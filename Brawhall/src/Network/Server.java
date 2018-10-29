package Network;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

import World.World;
import gameManager.Action;
import Network.ActionMessage;
import Objects.Background;
import Objects.Block;
import Objects.GameObject;
import Objects.ObjectRenderer;
import Objects.Player;
import Objects.PlayerRenderer;
import interfaces.Direction;

public class Server extends Thread
{
	
	private ServerSocket Server;
	
	ArrayList<Connect> clients;
	LinkedBlockingQueue<Message>messages;
	ServerState state;
	World w;
	boolean playerSelected[]=new boolean[3];
	
	public Server() throws Exception
	{
		this.setName("Server");
		clients = new ArrayList<Connect>();
		Server = new ServerSocket(1234);
		messages=new LinkedBlockingQueue<Message>();
		System.out.println("Il Server è in attesa sulla porta 4000.");
		state=ServerState.WAITING_CONNECTIONS;
		w=new World(300,300);
		this.start();
		
	}
	public void run(){
		double lastTime = System.nanoTime();
			double amountOfTicks = 60.0;
			double ns = 1000000000 / amountOfTicks;
			double delta = 0;
			double draw=0;
		while(true) {
			while(state==ServerState.WAITING_CONNECTIONS)
			{
				try 
				{
					System.out.println("In attesa di Connessione.");
					Socket client = Server.accept();
					System.out.println("Connessione accettata da: "+ client.getInetAddress());
					Connect c = new Connect(client,messages);
					Message o=new Message();
					o.write("Hello m8");
					c.sendMessage(o.toString());
					c.setId(clients.size()+1);
					clients.add(c);
					if(clients.size()==2) {
						ActionMessage a= new ActionMessage(Action.CHOOSE_PLAYER_MULTIPLAYER);
						sendBroadcast(a);
						state=ServerState.CHOOSING_PLAYERS;
					}
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
			
			
			
			while(state==ServerState.CHOOSING_PLAYERS||state==ServerState.IN_GAME) {
				
				double now = System.nanoTime();
				delta = (now - lastTime) / ns;
				tick(delta );
				draw+=delta;
				lastTime = now;
				if(draw>1) {
					draw=0;
					
					if(state==ServerState.IN_GAME) {
					
						sendBroadcast(new SyncMessage(w.getJSON().toString()));
					}
				}
						
			}
		}
	}
	private void tick(double delta) {
		
		resolveActions();
		if(state==ServerState.IN_GAME) {
			w.Update(delta);
		
		}
	}
	
	private void resolveActions() {
		Message m=messages.poll();
		if(m!=null){
			int sender=m.getInt("client");
			if(m.getString("type").compareTo("message")==0) {
				System.out.println(m.get("content"));
			}else if(m.getString("type").compareTo("action")==0) {
				ActionMessage a=new ActionMessage(m.toString());
			switch(a.getAction()) {
				
				case CLOSE_GAME:
					break;
				case PLAYER_JUMP:
					w.getPlayer(sender).jump();
					break;
				case PLAYER_ATTACK:
					w.getPlayer(sender).toggleAttack(true);
					break;
				case PLAYER_MOVE_LEFT: 
					w.getPlayer(sender).ChangeDirection(Direction.LEFT);
					break;	
				case PLAYER_MOVE_RIGHT:
					w.getPlayer(sender).ChangeDirection(Direction.RIGHT);
					break;
				case PLAYER_MOVE_REST:
					w.getPlayer(sender).ChangeDirection(Direction.REST);
					break;
				case PLAYER_CROUCH:
					w.getPlayer(sender).toggleCrouch(true);
					break;
				case PLAYER_STAND:
					w.getPlayer(sender).toggleCrouch(false);
					break;
				case START_MULTIPLAYER_GAME:
					loadLevel();
					state=ServerState.IN_GAME;
					break;
				case PLAYER_CHOOSED_MULTIPLAYER:
					if(sender==1)
						w.setPlayerName(a.getString("playerName"));
					else if (sender==2)
						w.setPlayer2Name(a.getString("playerName"));
					playerSelected[sender]=true;
					if(playerSelected[1]&&playerSelected[2]) {
						ActionMessage am=new ActionMessage(Action.START_MULTIPLAYER_GAME);
						am.put("playerName", w.getPlayerName());
						am.put("player2Name", w.getPlayer2Name());
						putMessage(am);
					}
					break;
				
				default:
					break;
				
				
				}
			
				if(needBroadcast(a))sendBroadcast(a);
			}
			
		}
	}
	private void putMessage(ActionMessage a) {
		a.put("client", 0);
		messages.add(a);
	}
	private boolean needBroadcast(ActionMessage a) {
		switch(a.getAction()) {
		case START_MULTIPLAYER_GAME:
			return true;
		default:
			return false;
			
		}	
		
	}
	private void loadLevel() {
		
		
		String p2=w.getPlayer2Name();
		String p1=w.getPlayerName();
		w=new World(300,500);
		GameObject o=new Player(50,0);
		GameObject o2 =new Player(250,0);
		
		w.addObject(o);
		w.addObject(o2);
		w.setPlayer((Player)o,1);
		w.setPlayer((Player)o2,2);
		w.getPlayer(1).setName(p1);
		w.getPlayer(2).setName(p2);
		
		
		//cam=new Camera(w,o);
		//cam.setViewH(500);
		//cam.setViewW(300);
		
	
		
		for (int i=50;i<w.getWidth()-50;i+=6) {
			o=new Block(i, w.getHeight()/2-18);
			w.addObject(o);
		
		}
		for (int i=w.getWidth()/2+20;i<w.getWidth();i+=6) {
			o=new Block(i, w.getHeight()/2-40);
			w.addObject(o);
		
		}
		for (int i=50;i<w.getWidth()/2-30;i+=6) {
			o=new Block(i, w.getHeight()/2-60);
			w.addObject(o);
			
		}
		for (int i=w.getWidth()/2-50;i<w.getWidth()/2+50;i+=6) {
			o=new Block(i, w.getHeight()/2-100);
			w.addObject(o);
		
		}
		
	}
		
	
	public ServerState getStateServer() {return state;};
	
	
	public void sendMessage(int idConnection, Message message) {
		
		clients.get(idConnection).sendMessage(message.toString());
		
	}
	public void sendBroadcast( Message message) {
		for(int id = 0;id<clients.size();id++) {
			message.put("target", id+1);
			clients.get(id).sendMessage(message.toString());
		
		}
	}

	
}
