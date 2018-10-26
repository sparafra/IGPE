package gameManager;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import org.json.JSONException;
import org.json.JSONObject;

import Network.ActionMessage;
import Network.Client;
import Network.Message;
import Network.Server;
//import Network.UDP.Client;
//import Network.UDP.Server;
import Objects.Background;
import Objects.Block;
import Objects.GameObject;
import Objects.Media;
import Objects.ObjectRenderer;
import Objects.Player;
import Objects.PlayerRenderer;
import Objects.PlayerState;
import Objects.SoundClip;
import World.MyFrame;
import World.MyPanel;
import World.Painter;
import World.World;
import interfaces.Direction;



public class GameManager extends Thread implements Runnable{
	public static void main(String[] args) {
		
		
		GameManager gm= new GameManager();
		gm.start();
	}
	
	static final int panelWidth=1440;	
	static final int panelHeight=900;

	Toolkit tk;

	Image BackgroundMenu;
	Menu menu;
	
	
	LinkedList<GameObject> objects;
	LinkedList<JAction> actions;
	
	LinkedList<ObjectRenderer>SavedRenderers;
	LinkedList<GameObject> SavedObjects;
	
	HashMap<String, SoundClip> SoundClips;
	
	Media m;
	EventHandler ev;
	World w;
	
	
	Painter painter;
	
	Server S = null;
	Client C = null;
	int myPlayer=0;
	
	private boolean running=false;
	boolean inMenu;
	boolean inGame=false;
	boolean MuteSound = true;
	boolean multiplayerGame = false;
	boolean waitingConnection = false;
	boolean waitingChoosePlayer = false;
	public GameManager() 
	{
		painter=new Painter();
		tk = Toolkit.getDefaultToolkit();
		m = new Media();
		w=new World(300,300);
		actions=new LinkedList<JAction>();
		menu = new Menu(this);
		
		initGui();
		initSound();
		
		inMenu=true;
		painter.setRenderers(menu.getRenderers());
			
	}
	public void initSound()
	{
		SoundClips = new HashMap<String, SoundClip>();
		SoundClip menuSound = new SoundClip("Menu.aif");
		SoundClips.put("Menu", menuSound);
	}
	private void loadPlayerSpecs(Player obj)
	{
		
		obj.setBaseAttack(m.getPlayerSpecs(obj.getName()).get("baseAttack"));
		obj.setStandHeight(m.getPlayerSpecs(obj.getName()).get("standHeight"));
		obj.setAtkSpeed(m.getPlayerSpecs(obj.getName()).get("atkSpeed"));
		obj.setAtkRange(m.getPlayerSpecs(obj.getName()).get("atkRange"));
		obj.setWeight(m.getPlayerSpecs(obj.getName()).get("weight"));
		
		
	}
	public void initGui() 
	{
		
		
		MyFrame f= new MyFrame(panelWidth,panelHeight);
		MyPanel pn=new MyPanel(this,panelWidth, panelHeight);
		
		painter.setPanel(pn);
		f.setContentPane(pn);
		f.setVisible(true);
	}
	public void loadLevel() {
		painter.clear();
		objects=new LinkedList<GameObject>();
		String p2=w.getPlayer2Name();
		String p1=w.getPlayerName();
		w=new World(300,500,objects);
		GameObject o=new Player(50,0);
		GameObject o2 =new Player(250,0);
		GameObject bg = new Background(w.getWidth(), w.getHeight());
		w.addObject(o);
		w.addObject(o2);
		w.setPlayer((Player)o,1);
		w.setPlayer((Player)o2,2);
		w.getPlayer(1).setName(p1);
		w.getPlayer(2).setName(p2);
		loadPlayerSpecs(w.getPlayer(1));
		loadPlayerSpecs(w.getPlayer(2));
		
		//cam=new Camera(w,o);
		//cam.setViewH(500);
		//cam.setViewW(300);
		
		painter.addRenderer(new ObjectRenderer(bg, this));
		painter.addRenderer(new PlayerRenderer((Player)o,this));	
		painter.addRenderer(new PlayerRenderer((Player)o2,this));	
		
		for (int i=50;i<w.getWidth()-50;i+=6) {
			o=new Block(i, w.getHeight()/2-18);
			w.addObject(o);
			painter.addRenderer(new ObjectRenderer(o,this));
		}
		for (int i=w.getWidth()/2+20;i<w.getWidth();i+=6) {
			o=new Block(i, w.getHeight()/2-40);
			w.addObject(o);
			painter.addRenderer(new ObjectRenderer(o,this));
		}
		for (int i=50;i<w.getWidth()/2-30;i+=6) {
			o=new Block(i, w.getHeight()/2-60);
			w.addObject(o);
			painter.addRenderer(new ObjectRenderer(o,this));
		}
		for (int i=w.getWidth()/2-50;i<w.getWidth()/2+50;i+=6) {
			o=new Block(i, w.getHeight()/2-100);
			w.addObject(o);
			painter.addRenderer(new ObjectRenderer(o,this));
		}
		
	}
	public void start() {
		if(isRunning() )return;
		ev=new EventHandler(this);
		running=true;
		super.start();
	}
	public void run() {
		double lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		double draw=0;
		while(isRunning()){
			double now = System.nanoTime();
			delta = (now - lastTime) / ns;
			tick(delta );
			draw+=delta;
			lastTime = now;
			if(draw>1) {
				draw=0;
				painter.render();
				
			}
			try {
				sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	public void tick(double delta) {	
		w.Update(delta);
		menu.tick(delta);
		checkInput(delta);
		resolveActions();	
	}
	
	public void checkInput(double delta) {
		
		if(C!=null&&C.getStateClient()=="Connected") {
			checkServerInputs();
		}
		if(inMenu &&!menu.isBusy()) 
		{
			checkMenuInputs();
		}
		else if(inGame&&!multiplayerGame){	
			checkLocalGameInputs();		
		}	
		else if(inGame&&multiplayerGame){
			checkMultiplayerInputs();	
		}
		
	}
	private void checkServerInputs(){
		String s = C.getMessage();
		if(s!=null) {
		try {
			
			Message m=new Message(s);
			s=m.getString("type");
			
			if (s.compareTo("message")==0) {
				System.out.println(m.get("content"));
			}
			else if(s.compareTo("action")==0) {
				JAction a=new JAction(m.toString());
				performAction(a);
			}
			else if(s.compareTo("sync")==0) {
				w.sync(m);
			}
		} catch (JSONException e) {
			//e.printStackTrace();
		}
		 
		}
	}
	
	private void checkMenuInputs(){
		if(ev.keys[Action.MUTE.key]){
			SoundClips.get("Menu").Stop();
			MuteSound = true;
		}
		if(!menu.isBusy()) {
			 if(menu.MenuState.equals("ChooseLocalPlayer"))
			{
				if(ev.keys[Action.SELECT_MENU.key])
				{
					if(menu.getPlayerSelectionTurn() == 1 || menu.getPlayerSelectionTurn() == 2)
						menu.nextPlayerSelectionTurn();
					else
					{
						performAction(Action.START_GAME);
					}
					
					menu.hold();
				}
				
				if(ev.keys[KeyEvent.VK_RIGHT])
					menu.nextPlayer(); 
					
				if(ev.keys[KeyEvent.VK_LEFT]) 
					menu.prevPlayer();
					
				
			}
			 else if(menu.MenuState.equals("ChooseMultiplayerPlayer")){
				 if(!menu.isLocked()) {
					 if(ev.keys[Action.SELECT_MENU.key]){
							if(menu.getPlayerSelectionTurn() == 1)
								menu.nextPlayerSelectionTurn();
							else {
								JAction a=new JAction(Action.PLAYER_CHOOSED_MULTIPLAYER);
								a.put("playerName",menu.Player1Preview.getSelectedPlayer());
								menu.lock();
								performAction(a);
								}
						}
						if(ev.keys[KeyEvent.VK_RIGHT])
							menu.nextPlayer();; 
						if(ev.keys[KeyEvent.VK_LEFT]) 
							menu.prevPlayer();
				}
			}
			else{
				 if(!menu.isLocked()) {
				if(ev.keys[Action.SELECT_MENU.key])
					performAction(menu.selectedAction()); 
				
				if(ev.keys[KeyEvent.VK_DOWN]) 
					menu.selectNext(); 
				if(ev.keys[KeyEvent.VK_UP]) 
					menu.selectPrev();		
				}
			}
		}
	}
	private void checkMultiplayerInputs() {
		if( C.getStateClient() == "Connected") {
			if(ev.keys[Action.PLAYER_ATTACK.key]&& !w.getPlayer(myPlayer).isAttacking()) 
				C.sendMessage(new JAction(Action.PLAYER_ATTACK).toString());
			 if(ev.keys[Action.PLAYER_MOVE_LEFT.key]&&!w.getPlayer(myPlayer).isMovingLeft()) {
				C.sendMessage(new JAction(Action.PLAYER_MOVE_LEFT).toString());
			}
			 if(ev.keys[Action.PLAYER_MOVE_RIGHT.key]&&!w.getPlayer(myPlayer).isMovingRight()) {
				C.sendMessage(new JAction(Action.PLAYER_MOVE_RIGHT).toString());
			}
			 if(ev.keys[Action.PLAYER_JUMP.key] && !w.getPlayer(myPlayer).isJumping()) {
				C.sendMessage(new JAction(Action.PLAYER_JUMP).toString());
			}
			 if(ev.keys[Action.PLAYER_CROUCH.key]&& !w.getPlayer(myPlayer).isCrouching()) {
				C.sendMessage(new JAction(Action.PLAYER_CROUCH).toString());
			}
			 if(!ev.keys[Action.PLAYER_CROUCH.key]&& w.getPlayer(myPlayer).isCrouching()) {
				C.sendMessage(new JAction(Action.PLAYER_STAND).toString());
			}
			 if(!ev.keys[Action.PLAYER_JUMP.key]&&!ev.keys[Action.PLAYER_MOVE_RIGHT.key]&&!ev.keys[Action.PLAYER_MOVE_LEFT.key]&&!w.getPlayer(myPlayer).isResting()) {
				C.sendMessage(new JAction(Action.PLAYER_MOVE_REST).toString());				
			}
		}	
	}
	private void checkLocalGameInputs() {
		if(ev.keys[Action.PAUSE.key])				
			performAction(Action.PAUSE);
		
		if(ev.keys[Action.PLAYER_ATTACK.key]&& !w.getPlayer(1).isAttacking()) {
			performAction(Action.PLAYER_ATTACK,1);
		}
		if(!w.getPlayer(1).isMovingLeft()&&ev.keys[Action.PLAYER_MOVE_LEFT.key])
			performAction(Action.PLAYER_MOVE_LEFT,1);
		if(!w.getPlayer(1).isMovingRight()&&ev.keys[Action.PLAYER_MOVE_RIGHT.key])
			performAction(Action.PLAYER_MOVE_RIGHT,1);
		if(ev.keys[Action.PLAYER_JUMP.key] && !w.getPlayer(1).isJumping())
			performAction(Action.PLAYER_JUMP,1);
		if(ev.keys[Action.PLAYER_CROUCH.key]&& !w.getPlayer(1).isCrouching())
			performAction(Action.PLAYER_CROUCH,1);
		if(!ev.keys[Action.PLAYER_CROUCH.key]&& w.getPlayer(1).isCrouching()) 
			performAction(Action.PLAYER_STAND,1);
		if(!w.getPlayer(1).isResting()&&!ev.keys[Action.PLAYER_JUMP.key]&&!ev.keys[Action.PLAYER_MOVE_RIGHT.key]&&!ev.keys[Action.PLAYER_MOVE_LEFT.key])
			performAction(Action.PLAYER_MOVE_REST,1);	
		if(ev.keys[Action.PLAYER2_ATTACK.key]&& !w.getPlayer(2).isAttacking())
			performAction(Action.PLAYER_ATTACK,2);
		if(!w.getPlayer(2).isMovingLeft()&&ev.keys[Action.PLAYER2_MOVE_LEFT.key])
			performAction(Action.PLAYER_MOVE_LEFT,2);
		if(!w.getPlayer(2).isMovingRight()&&ev.keys[Action.PLAYER2_MOVE_RIGHT.key])
			performAction(Action.PLAYER_MOVE_RIGHT,2);
		if(ev.keys[Action.PLAYER2_JUMP.key] && !w.getPlayer(2).isJumping())
			performAction(Action.PLAYER_JUMP,2);
		if(ev.keys[Action.PLAYER2_CROUCH.key]&&!w.getPlayer(2).isCrouching())
			performAction(Action.PLAYER_CROUCH,2);
		if(!ev.keys[Action.PLAYER2_CROUCH.key]&&w.getPlayer(2).isCrouching())
			performAction(Action.PLAYER_STAND,2);
		if(!w.getPlayer(2).isResting()&&!ev.keys[Action.PLAYER2_JUMP.key]&&!ev.keys[Action.PLAYER2_MOVE_RIGHT.key]&&!ev.keys[Action.PLAYER2_MOVE_LEFT.key])
			performAction(Action.PLAYER_MOVE_REST,2);
	}
	private void performAction(JAction a) {
		actions.push(a);		
	}
	private void performAction(Action a) {
		JAction ja;
		try {
			ja = new JAction(a);
			actions.push(ja);	
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	private void performAction(Action a,int client) {
		JAction ja;
		try {
			ja = new JAction(a);
			ja.put("client",client);
			actions.push(ja);	
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	public void resolveActions() {
		
			JAction a=actions.poll();
			if(a!=null)
			try {
				switch (a.getAction()) {
				case PLAYER_JUMP:
					w.PlayerJump(a.getInt("client"));
					break;
				case PLAYER_ATTACK:
					w.getPlayer(a.getInt("client")).toggleAttack(true);
				break;
				case PLAYER_MOVE_LEFT: 
					w.getPlayer(a.getInt("client")).ChangeDirection(Direction.LEFT);
					break;	
				case PLAYER_MOVE_RIGHT:
					w.getPlayer(a.getInt("client")).ChangeDirection(Direction.RIGHT);
					break;
				case PLAYER_MOVE_REST:
					w.getPlayer(a.getInt("client")).ChangeDirection(Direction.REST);
					break;
				case PLAYER_CROUCH:
					w.getPlayer(a.getInt("client")).toggleCrouch(true);
					break;
				case PLAYER_STAND:
					w.getPlayer(a.getInt("client")).toggleCrouch(false);
					break;
				case CLOSE_GAME: 
					System.exit(0);
					break;
				case OPEN_SETTING:
					break;
				case SELECT_MENU:
					break;
				case START_GAME:
						menu.ChangeStatus("Pause");
						inMenu=false;
						inGame=true;
						loadLevel();
					break;
				case START_MULTIPLAYER_GAME:
						myPlayer=a.getInt("target");
						menu.ChangeStatus("Pause");
						w.setPlayerName(a.getString("playerName"));
						w.setPlayer2Name(a.getString("player2Name"));
						inMenu=false;
						multiplayerGame=true;
						inGame=true;
						loadLevel();
					break;
				case MENU_START_LOCAL_GAME:
					menu.ChangeStatus("ChooseLocalPlayer");
					painter.setRenderers(menu.getRenderers());	
					break;
				case MENU_START_MULTIPLAYER_GAME:
					menu.ChangeStatus("Multiplayer");
					painter.setRenderers(menu.getRenderers());	
					break;
				case PAUSE:
					inMenu = true;
					SavedRenderers=painter.getRenderers();
					menu.ChangeStatus("Pause");
					painter.setRenderers(menu.getRenderers());	
					break;
				case RESUME:
					inMenu=false;
					SoundClips.get("Menu").Stop();
					painter.setRenderers(SavedRenderers);
					break;
				case CREAPARTITA:
					try 
					{	
						S = new Server();
						performAction(Action.PARTECIPA);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					
					break;
				case PARTECIPA:
					try 
					{
						waitingConnection = true;
						menu.ChangeStatus("waitingConnection");
						painter.setRenderers(menu.getRenderers());	
						C = new Client("localhost");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case BACKTOMENU:
					menu.ChangeStatus("StartMenu");
					painter.setRenderers(menu.getRenderers());
					break;
				case CHOOSE_PLAYER_MULTIPLAYER:
					menu.ChangeStatus("ChooseMultiplayerPlayer");
					painter.setRenderers(menu.getRenderers());
					break;
				case MENU_CLOSE_GAME:
					break;
				
				case PLAYER_CHOOSED_MULTIPLAYER:
					if(!menu.getPlayer1Choosed())
					C.sendMessage(a.toString());
					
					break;
				default:
					break;
				
				
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	public int ConvertX(float wx) {
		return (int) ((wx*painter.getPanel().getWidth())/w.getWidth()) ;	
	}
	public int ConvertY(float wy) {
		return (int) ((wy*painter.getPanel().getHeight())/w.getHeight()) ;	
	}
	public int ConvertPosX(float wx) {
		return (int) ((wx*painter.getPanel().getWidth())/w.getWidth()) ;
	}
	public int ConvertPosY(float wy) {
		return (int) ((wy*painter.getPanel().getHeight())/w.getHeight()) ;
	}
	
	public int ConvertPanelX(float px) {
		return (int) ((px*w.getWidth())/painter.getPanel().getWidth()) ;
	}
	public int ConvertPanelY(float py) {
		return (int) ((py*w.getHeight())/painter.getPanel().getHeight()) ;
	}
	
	public World getWorld() {return w;}
	public Media getMedia() {return m;}
	public void setMenu(boolean m) {this.inMenu = m;}
	public boolean isRunning() {
		return running;
	}
	
}
