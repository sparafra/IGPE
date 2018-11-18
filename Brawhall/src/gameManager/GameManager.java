package gameManager;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import org.json.JSONException;
import org.json.JSONObject;

import Graphics.Camera;
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
		Media.LoadMedia();
		gm.start();
	}
	
	static final int panelWidth=1440;	
	static final int panelHeight=900;

	Toolkit tk;

	Image BackgroundMenu;
	Menu menu;
	
	
	
	
	
	LinkedList<ObjectRenderer>SavedRenderers;
	LinkedList<GameObject> SavedObjects;
	
	HashMap<String, SoundClip> SoundClips;
	
	
	GMEventHandler ev;
	World w;
	
	
	Painter painter;
	Camera cam;
	
	Server S = null;
	Client C = null;
	int myPlayer=1;
	
	private boolean running=false;
	boolean inMenu;
	boolean inGame=false;
	boolean MuteSound = true;
	boolean multiplayerGame = false;
	boolean waitingConnection = false;
	boolean waitingChoosePlayer = false;
	public GameManager() 
	{
		this.setName("Game Manager");
		painter=new Painter();
		tk = Toolkit.getDefaultToolkit();
		initGui();
		initSound();
		openStartMenu();
		ev=new GMEventHandler(this);
		w=new World(0,0,ev);
	
		
		
			
	}
	public void initSound()
	{
		SoundClips = new HashMap<String, SoundClip>();
		SoundClip menuSound = new SoundClip("Menu.aif");
		SoundClips.put("Menu", menuSound);
	}
	private void loadPlayerSpecs(Player obj)
	{
		
		obj.setBaseAttack(Media.getPlayerSpecs(obj.getName()).get("baseAttack"));
		obj.setStandHeight(Media.getPlayerSpecs(obj.getName()).get("standHeight"));
		obj.setAtkSpeed(Media.getPlayerSpecs(obj.getName()).get("atkSpeed"));
		obj.setAtkRange(Media.getPlayerSpecs(obj.getName()).get("atkRange"));
		obj.setWeight(Media.getPlayerSpecs(obj.getName()).get("weight"));
		
		
	}
	public void initGui() 
	{
		
		
		MyFrame f= new MyFrame(panelWidth,panelHeight);
		MyPanel pn=new MyPanel(this,panelWidth, panelHeight);
		
		painter.setPanel(pn);
		f.setContentPane(pn);
		f.setVisible(true);
	}
	public void loadLevel(Level l) {
		painter.clear();
		
		String s2=w.getPlayerName(1);
		String s1=w.getPlayerName(2);
		w=new World(l.getWidth(),l.getHeight(),ev);
		
		Player p=new Player(50,0);
		Player p2 =new Player(200,0);
		
		w.addObject(p);
		w.addObject(p2);
		
		
		w.setPlayer(p,1);
		w.setPlayer(p2,2);
		w.getPlayer(1).setName(s1);
		w.getPlayer(2).setName(s2);
		
		loadPlayerSpecs(w.getPlayer(1));
		loadPlayerSpecs(w.getPlayer(2));
		
		cam=new Camera(w);
		
		cam.addAnchor(p);
		cam.addAnchor(p2);
		for(int i=0;i<l.objects.size();i++) {
			GameObject o= l.objects.get(i);
			w.addObject(o);
			painter.addRenderer(new ObjectRenderer(l.objects.get(i),this));
		}
		
		painter.addRenderer(new PlayerRenderer(p,this));	
		painter.addRenderer(new PlayerRenderer(p2,this));
		
		
		
	}
	public void start() {
		if(isRunning() )return;
		running=true;
		super.start();
	}
	public void run() {
	/*	double lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;*/
		double draw=0;
		while(isRunning()){
			/*double now = System.nanoTime();
			delta = (now - lastTime) / ns;
			tick(delta );
			
			lastTime = now;*/
			
			Time.update();
			double delta=Time.deltaTime();
			draw+=delta;
			tick(delta );
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
		if(!multiplayerGame&&inGame)
		w.Update(delta);
		menu.tick(delta);
		cam.tick();
		checkInput();
		ev.resolveActions();	
	}
	
	public void checkInput() {
		
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
			try {
				checkMultiplayerInputs();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
	}
	private void checkServerInputs(){
		
		
		try {
			String s = C.getMessage();
			if(s!=null) {
				
				Message m=new Message(s);
				s=m.getString("type");
				
				if (s.compareTo("message")==0) {
					System.out.println(m.get("content"));
				}
				else if(s.compareTo("action")==0) {
					JAction a=new JAction(m.toString());
					ev.performAction(a);
				}
				else if(s.compareTo("sync")==0) {
					w.sync(m);
				}
			}
		} catch (JSONException e) {
			//e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
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
						w.setPlayerName(1,menu.Player1Preview.getSelectedPlayer());
						w.setPlayerName(2,menu.Player2Preview.getSelectedPlayer());
						
						ev.performAction(Action.START_GAME);
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
								
								ev.performAction(a);
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
					ev.performAction(menu.selectedAction()); 
				
				if(ev.keys[KeyEvent.VK_DOWN]) 
					menu.selectNext(); 
				if(ev.keys[KeyEvent.VK_UP]) 
					menu.selectPrev();		
				}
			}
		}
	}
	private void checkMultiplayerInputs() throws JSONException, InterruptedException {
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
			 if(!w.getPlayer(myPlayer).isResting()&&!ev.keys[Action.PLAYER_JUMP.key]&&!ev.keys[Action.PLAYER_MOVE_RIGHT.key]&&!ev.keys[Action.PLAYER_MOVE_LEFT.key]) {
				C.sendMessage(new JAction(Action.PLAYER_MOVE_REST).toString());				
			}
		}	
	}
	private void checkLocalGameInputs() {
		if(ev.keys[Action.PAUSE.key])				
			ev.performAction(Action.PAUSE);
		
		 if(ev.keys[Action.PLAYER_ATTACK.key]&& !w.getPlayer(1).isAttacking()) 
			 ev.performAction(Action.PLAYER_ATTACK,1);
		else if (!w.getPlayer(1).isMovingLeft()&&ev.keys[Action.PLAYER_MOVE_LEFT.key])
			ev.performAction(Action.PLAYER_MOVE_LEFT,1);
		else if(!w.getPlayer(1).isMovingRight()&&ev.keys[Action.PLAYER_MOVE_RIGHT.key])
			ev.performAction(Action.PLAYER_MOVE_RIGHT,1);
		else if(ev.keys[Action.PLAYER_JUMP.key] && !w.getPlayer(1).isJumping())
			ev.performAction(Action.PLAYER_JUMP,1);
		else if(ev.keys[Action.PLAYER_CROUCH.key]&& !w.getPlayer(1).isCrouching())
			ev.performAction(Action.PLAYER_CROUCH,1);
		else if(!ev.keys[Action.PLAYER_CROUCH.key]&& w.getPlayer(1).isCrouching()) 
			ev.performAction(Action.PLAYER_STAND,1);
		else if(!w.getPlayer(1).isResting()&&!ev.keys[Action.PLAYER_JUMP.key]&&!ev.keys[Action.PLAYER_MOVE_RIGHT.key]&&!ev.keys[Action.PLAYER_MOVE_LEFT.key])
			ev.performAction(Action.PLAYER_MOVE_REST,1);	
		else if(ev.keys[Action.PLAYER2_ATTACK.key]&& !w.getPlayer(2).isAttacking())
			ev.performAction(Action.PLAYER_ATTACK,2);
		else if(!w.getPlayer(2).isMovingLeft()&&ev.keys[Action.PLAYER2_MOVE_LEFT.key])
			ev.performAction(Action.PLAYER_MOVE_LEFT,2);
		else if(!w.getPlayer(2).isMovingRight()&&ev.keys[Action.PLAYER2_MOVE_RIGHT.key])
			ev.performAction(Action.PLAYER_MOVE_RIGHT,2);
		else if(ev.keys[Action.PLAYER2_JUMP.key] && !w.getPlayer(2).isJumping())
			ev.performAction(Action.PLAYER_JUMP,2);
		else if(ev.keys[Action.PLAYER2_CROUCH.key]&&!w.getPlayer(2).isCrouching())
			ev.performAction(Action.PLAYER_CROUCH,2);
		else if(!ev.keys[Action.PLAYER2_CROUCH.key]&&w.getPlayer(2).isCrouching())
			ev.performAction(Action.PLAYER_STAND,2);
		else if(!w.getPlayer(2).isResting()&&!ev.keys[Action.PLAYER2_JUMP.key]&&!ev.keys[Action.PLAYER2_MOVE_RIGHT.key]&&!ev.keys[Action.PLAYER2_MOVE_LEFT.key])
			ev.performAction(Action.PLAYER_MOVE_REST,2);
	}
	
	
	
	
	public float ConvertX(float wx) {
		return  ((wx*painter.getPanel().getWidth())/cam.getWidth()) ;
		
 	}
 	public float ConvertY(float wy) {
 		return  ((wy*painter.getPanel().getHeight())/cam.getHeight()) ;
 		
 	}
 	public float ConvertPosX(float wx) { 
 		return (int) (((wx-cam.getPosX())*painter.getPanel().getWidth())/cam.getWidth()) ;
 		
 	}
 	public float ConvertPosY(float wy) {
 		return  (((wy-cam.getPosY())*painter.getPanel().getHeight())/cam.getHeight()) ;
 		
 	}
 	public float ConvertPanelX(float px) {
 		return  ((px*cam.getWidth())/painter.getPanel().getWidth()) ;
 		
 	}
 	public float ConvertPanelY(float py) {
 		return  ((py*cam.getHeight())/painter.getPanel().getHeight()) ;
 	}
	public World getWorld() {return w;}
	
	public void setMenu(boolean m) {this.inMenu = m;}
	public boolean isRunning() {
		return running;
	}
	public void openStartMenu() {
		menu = new Menu(300,300,this);
		cam=new Camera(menu);
		cam.setFree(true);
		cam.center();
		inMenu=true;
		painter.setRenderers(menu.getRenderers());
	}
	
}
