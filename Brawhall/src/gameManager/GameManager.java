package gameManager;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import Network.Client;
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
	Menu DefaultMenu;
	
	
	LinkedList<GameObject> objects;
	LinkedList<Action> actions;
	
	LinkedList<ObjectRenderer>SavedRenderers;
	LinkedList<GameObject> SavedObjects;
	
	HashMap<String, SoundClip> SoundClips;
	
	Media m;
	EventHandler ev;
	World w;
	
	
	Painter painter;
	
	Server S = null;
	Client C = null;
	
	boolean running=false;
	boolean menu;
	boolean MuteSound = true;
	boolean MultiplayerGame = false;
	boolean WaitingConnection = false;
	boolean WaitingChoosePlayer = false;
	public GameManager() 
	{
		painter=new Painter();
		tk = Toolkit.getDefaultToolkit();
		m = new Media();
		w=new World(300,300,null);
		actions=new LinkedList<Action>();
		DefaultMenu = new Menu(this);
		
		initGui();
		initSound();
		
		menu=true;
		painter.setRenderers(DefaultMenu.getRenderers());
		painter.start();	
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
		
		System.out.println(m.getPlayerSpecs(obj.getName()).get("atkRange"));
	}
	public void initGui() 
	{
		//FullScreen
		/*
		Dimension FullScreen = tk.getScreenSize();
		MyFrame f= new MyFrame(FullScreen.width,FullScreen.height);
		MyPanel pn=new MyPanel(this,FullScreen.width, FullScreen.height);
		*/
		
		MyFrame f= new MyFrame(panelWidth,panelHeight);
		MyPanel pn=new MyPanel(this,panelWidth, panelHeight);
		
		painter.setPanel(pn);
		f.setContentPane(pn);
		f.setVisible(true);
	}
	public void loadLevel() {
		painter.clear();
		objects=new LinkedList<GameObject>();
		w=new World(300,500,objects);
		GameObject o=new Player(50,0);
		GameObject o2 =new Player(250,0);
		GameObject bg = new Background(w.getWidth(), w.getHeight());
		w.addObject(o);
		w.addObject(o2);
		w.setPlayer((Player)o,1);
		w.setPlayer((Player)o2,2);
		w.getPlayer(1).setName(DefaultMenu.Player1Preview.getSelectedPlayer());
		w.getPlayer(2).setName(DefaultMenu.Player2Preview.getSelectedPlayer());
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
		//painter.setRenderers(renderers);
	}
	public void start() {
		if(running )return;
		ev=new EventHandler(this);
		running =true;
		super.start();
	}
	public void run() {
		double lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		double draw=0;
		while(running){
			double now = System.nanoTime();
			delta = (now - lastTime) / ns;
			tick(delta );
			draw+=delta;
			lastTime = now;
			if(draw>1) {
				draw=0;
				painter.render();
			}
		}	
	}
	public void tick(double delta) {	
		
		checkInput();
		
		resolveActions();
		if(!menu)
		{
			if(MultiplayerGame)
			{
				if(C == null)
				{
					if(S.getStateServer() == "Connected")
					{
						if(S.getMessage(0).equals("PLAYER_ATTACK") && !S.getMessageReaded(0))
						{
							performAction(Action.PLAYER2_ATTACK);
							S.setMessageReaded(0, true);
						}
						else if (S.getMessage(0).equals("PLAYER_MOVE_LEFT") && !S.getMessageReaded(0))
						{
							performAction(Action.PLAYER2_MOVE_LEFT);
							S.setMessageReaded(0, true);
						}
						else if (S.getMessage(0).equals("PLAYER_MOVE_RIGHT") && !S.getMessageReaded(0))
						{
							performAction(Action.PLAYER2_MOVE_RIGHT);
							S.setMessageReaded(0, true);
						}
						else if (S.getMessage(0).equals("PLAYER_JUMP") && !S.getMessageReaded(0))
						{
							performAction(Action.PLAYER2_JUMP);
							S.setMessageReaded(0, true);
						}
						else if (S.getMessage(0).equals("PLAYER_CROUCH") && !S.getMessageReaded(0))
						{
							performAction(Action.PLAYER2_CROUCH);
							S.setMessageReaded(0, true);
						}
						else if (S.getMessage(0).equals("PLAYER_STAND") && !S.getMessageReaded(0))
						{
							performAction(Action.PLAYER2_STAND);
							S.setMessageReaded(0, true);
						}
						else if (S.getMessage(0).equals("PLAYER_MOVE_REST") && !S.getMessageReaded(0))
						{
							performAction(Action.PLAYER2_MOVE_REST);
							S.setMessageReaded(0, true);
						}
					}
				}
				else if(S == null)
				{
					if(C.getStateClient() == "Connected")
					{
						if(C.getMessage().equals("PLAYER_ATTACK") && !C.getMessageReaded())
						{
							performAction(Action.PLAYER_ATTACK);
							C.setMessageReaded(true);
						}
						else if (C.getMessage().equals("PLAYER_MOVE_LEFT") && !C.getMessageReaded())
						{
							performAction(Action.PLAYER_MOVE_LEFT);
							C.setMessageReaded(true);
						}
						else if (C.getMessage().equals("PLAYER_MOVE_RIGHT") && !C.getMessageReaded())
						{
							performAction(Action.PLAYER_MOVE_RIGHT);
							C.setMessageReaded(true);
						}
						else if (C.getMessage().equals("PLAYER_JUMP") && !C.getMessageReaded())
						{
							performAction(Action.PLAYER_JUMP);
							C.setMessageReaded(true);
						}
						else if (C.getMessage().equals("PLAYER_CROUCH") && !C.getMessageReaded())
						{
							performAction(Action.PLAYER_CROUCH);
							C.setMessageReaded(true);
						}
						else if (C.getMessage().equals("PLAYER_STAND") && !C.getMessageReaded())
						{
							if( w.getPlayer(1).isCrouching())
							performAction(Action.PLAYER_STAND);
							C.setMessageReaded(true);
						}
						else if (C.getMessage().equals("PLAYER_MOVE_REST") && !C.getMessageReaded())
						{
							performAction(Action.PLAYER_MOVE_REST);
							C.setMessageReaded(true);
						}
					}
				}
			
			}
			w.Update(delta);
			//cam.tick();
			//p.render();
		}
		else
		{
			if(!MuteSound)
				SoundClips.get("Menu").Play();
			painter.render();
			if(WaitingConnection)
			{
				if(C == null)
				{
					if(S.getStateServer() == "Connected")
					{
						performAction(Action.CHOOSE_PLAYER_MULTIPLAYER);
						WaitingConnection = false;
					}
				}
				else if(S == null)
				{
					if(C.getStateClient() == "Connected")
					{
						performAction(Action.CHOOSE_PLAYER_MULTIPLAYER);
						WaitingConnection = false;
					}
				}
			}
			else if(DefaultMenu.getStatus() == "ChooseMultiplayerPlayer" )
			{
				if(DefaultMenu.getPlayer1Choosed() && DefaultMenu.getPlayer2Choosed())
				{
					System.out.println("START");
					MultiplayerGame = true;
					performAction(Action.START_GAME);
				}
				else
				{
					if(C == null)
					{
						if(S.getStateServer() == "Connected")
						{
							if(S.getMessage(0).equals("PlayerChoosed") && !S.getMessageReaded(0))
							{
								DefaultMenu.setPlayer2Choosed(true);
								S.setMessageReaded(0, true);
							}
							else if (S.getMessage(0).equals("NextPlayer") && !S.getMessageReaded(0))
							{
								DefaultMenu.nextPlayer(2);
								S.setMessageReaded(0, true);
							}
							else if (S.getMessage(0).equals("PrevPlayer") && !S.getMessageReaded(0))
							{
								DefaultMenu.prevPlayer(2);
								S.setMessageReaded(0, true);
							}
						}
					}
					else if(S == null)
					{
						if(C.getStateClient() == "Connected")
						{
							if(C.getMessage().equals("PlayerChoosed") && !C.getMessageReaded())
							{
								DefaultMenu.setPlayer1Choosed(true);
								C.setMessageReaded(true);
							}
							else if (C.getMessage().equals("NextPlayer") && !C.getMessageReaded())
							{
								DefaultMenu.nextPlayer(1);
								C.setMessageReaded(true);
							}
							else if (C.getMessage().equals("PrevPlayer") && !C.getMessageReaded())
							{
								DefaultMenu.prevPlayer(1);
								C.setMessageReaded(true);
							}
							
						}
					}
				}
			}
		}
	}
	
	public void checkInput() {
		if(menu) 
		{
			if(ev.keys[Action.MUTE.key]){
				SoundClips.get("Menu").Stop();
				MuteSound = true;
			}
			if(DefaultMenu.MenuState.equals("StartMenu")){
				if(ev.keys[Action.SELECT_MENU.key]){
					performAction(DefaultMenu.selectedAction()); 
					ev.keys[Action.SELECT_MENU.key] = false;
				}
				if(ev.keys[KeyEvent.VK_DOWN])
					DefaultMenu.selectNext(); 
				if(ev.keys[KeyEvent.VK_UP]) 
					DefaultMenu.selectPrev();
				if(!ev.keys[KeyEvent.VK_DOWN] && !ev.keys[KeyEvent.VK_UP] && !ev.keys[Action.SELECT_MENU.key])
					DefaultMenu.ready = true;		
			}
			else if(DefaultMenu.MenuState.equals("LocalGame"))
			{
				if(ev.keys[Action.SELECT_MENU.key])
				{
					if(DefaultMenu.getPlayerSelectionTurn() == 1 || DefaultMenu.getPlayerSelectionTurn() == 2)
						DefaultMenu.nextPlayerSelectionTurn();
					else
					{
						System.out.println("GAMEE");
						performAction(Action.START_GAME);
					}
					ev.keys[Action.SELECT_MENU.key] = false;
				}
				
				if(ev.keys[KeyEvent.VK_RIGHT])
					DefaultMenu.nextPlayer();; 
				if(ev.keys[KeyEvent.VK_LEFT]) 
					DefaultMenu.prevPlayer();
				if(!ev.keys[KeyEvent.VK_RIGHT] && !ev.keys[KeyEvent.VK_LEFT] && !ev.keys[Action.SELECT_MENU.key])
					DefaultMenu.ready = true;
			}
			else if(DefaultMenu.MenuState.equals("Pause"))
			{
				if(ev.keys[Action.SELECT_MENU.key])
				{
					performAction(DefaultMenu.selectedAction()); 
					ev.keys[Action.SELECT_MENU.key] = false;
				}
				
				if(ev.keys[KeyEvent.VK_DOWN])
					DefaultMenu.selectNext(); 
				if(ev.keys[KeyEvent.VK_UP]) 
					DefaultMenu.selectPrev();
				if(!ev.keys[KeyEvent.VK_DOWN] && !ev.keys[KeyEvent.VK_UP] && !ev.keys[Action.SELECT_MENU.key])
					DefaultMenu.ready = true;
			}
			else if(DefaultMenu.MenuState.equals("Multiplayer"))
			{
				if(ev.keys[Action.SELECT_MENU.key])
				{
					performAction(DefaultMenu.selectedAction()); 
					ev.keys[Action.SELECT_MENU.key] = false;
				}
				if(ev.keys[KeyEvent.VK_DOWN])
					DefaultMenu.selectNext(); 
				if(ev.keys[KeyEvent.VK_UP]) 
					DefaultMenu.selectPrev();
				if(!ev.keys[KeyEvent.VK_DOWN] && !ev.keys[KeyEvent.VK_UP] && !ev.keys[Action.SELECT_MENU.key])
					DefaultMenu.ready = true;
			}
			else if(DefaultMenu.MenuState.equals("ChooseMultiplayerPlayer"))
			{
				if(ev.keys[Action.SELECT_MENU.key])
				{
					if(DefaultMenu.getPlayerSelectionTurn() == 1 )
					{
						
						if(C == null)
						{
							if(S.getStateServer() == "Connected")
							{
								DefaultMenu.setPlayer1Choosed(true);
								S.sendMessage(0, "PlayerChoosed");
							}
						}
						else if(S == null)
						{
							if(C.getStateClient() == "Connected")
							{
								DefaultMenu.setPlayer2Choosed(true);
								C.sendMessage("PlayerChoosed");
							}	
						}
					}
					else 
					{
						performAction(Action.START_GAME);
					}
					ev.keys[Action.SELECT_MENU.key] = false;
				}
				if(ev.keys[KeyEvent.VK_RIGHT] )
				{
					
					if(C == null && !DefaultMenu.getPlayer1Choosed())
					{
						if(S.getStateServer() == "Connected")
						{
							DefaultMenu.nextPlayer(1);
							S.sendMessage(0, "NextPlayer");
						}
					}
					else if(S == null && !DefaultMenu.getPlayer1Choosed())
					{
						if(C.getStateClient() == "Connected")
						{
							DefaultMenu.nextPlayer(2);
							C.sendMessage("NextPlayer");
						}
					}
					ev.keys[KeyEvent.VK_RIGHT] = false;
				}
				if(ev.keys[KeyEvent.VK_LEFT]  ) 
				{
					
					if(C == null && !DefaultMenu.getPlayer1Choosed())
					{
						if(S.getStateServer() == "Connected")
						{
							DefaultMenu.prevPlayer(1);
							S.sendMessage(0, "PrevPlayer");
						}
					}
					else if(S == null && !DefaultMenu.getPlayer2Choosed())
					{
						if(C.getStateClient() == "Connected")
						{
							DefaultMenu.prevPlayer(2);
							C.sendMessage("PrevPlayer");
						}
					}
					ev.keys[KeyEvent.VK_LEFT] = false;
				}
				if(!ev.keys[KeyEvent.VK_RIGHT] && !ev.keys[KeyEvent.VK_LEFT] && !ev.keys[Action.SELECT_MENU.key])
					DefaultMenu.ready = true;
			}
		}
		else if(!MultiplayerGame)
		{	
				if(ev.keys[Action.PAUSE.key])
					
					performAction(Action.PAUSE);
				if(ev.keys[Action.PLAYER_ATTACK.key]&& !w.getPlayer(1).isAttacking())
					performAction(Action.PLAYER_ATTACK);
				if(!w.getPlayer(1).isMovingLeft()&&ev.keys[Action.PLAYER_MOVE_LEFT.key])
					performAction(Action.PLAYER_MOVE_LEFT);
				if(!w.getPlayer(1).isMovingRight()&&ev.keys[Action.PLAYER_MOVE_RIGHT.key])
					performAction(Action.PLAYER_MOVE_RIGHT);
				if(ev.keys[Action.PLAYER_JUMP.key])
					performAction(Action.PLAYER_JUMP);
				if(ev.keys[Action.PLAYER_CROUCH.key]&& !w.getPlayer(1).isCrouching())
					performAction(Action.PLAYER_CROUCH);
				if(!ev.keys[Action.PLAYER_CROUCH.key]&& w.getPlayer(1).isCrouching()) 
					performAction(Action.PLAYER_STAND);
				if(!w.getPlayer(1).isResting()&&!ev.keys[Action.PLAYER_JUMP.key]&&!ev.keys[Action.PLAYER_MOVE_RIGHT.key]&&!ev.keys[Action.PLAYER_MOVE_LEFT.key])
					performAction(Action.PLAYER_MOVE_REST);
				
				if(ev.keys[Action.PLAYER2_ATTACK.key]&& !w.getPlayer(2).isAttacking())
					performAction(Action.PLAYER2_ATTACK);
				if(!w.getPlayer(2).isMovingLeft()&&ev.keys[Action.PLAYER2_MOVE_LEFT.key])
					performAction(Action.PLAYER2_MOVE_LEFT);
				if(!w.getPlayer(2).isMovingRight()&&ev.keys[Action.PLAYER2_MOVE_RIGHT.key])
					performAction(Action.PLAYER2_MOVE_RIGHT);
				if(ev.keys[Action.PLAYER2_JUMP.key])
					performAction(Action.PLAYER2_JUMP);
				if(ev.keys[Action.PLAYER2_CROUCH.key]&&!w.getPlayer(2).isCrouching())
					performAction(Action.PLAYER2_CROUCH);
				if(!ev.keys[Action.PLAYER2_CROUCH.key]&&w.getPlayer(2).isCrouching())
					performAction(Action.PLAYER2_STAND);
				if(!w.getPlayer(2).isResting()&&!ev.keys[Action.PLAYER2_JUMP.key]&&!ev.keys[Action.PLAYER2_MOVE_RIGHT.key]&&!ev.keys[Action.PLAYER2_MOVE_LEFT.key])
					performAction(Action.PLAYER2_MOVE_REST);
		}	
		else if(MultiplayerGame)
		{
			if(C == null && S.getStateServer() == "Connected") {
				if(ev.keys[Action.PLAYER_ATTACK.key]&& !w.getPlayer(1).isAttacking()) {
					performAction(Action.PLAYER_ATTACK);
					S.sendMessage(0, "PLAYER_ATTACK");
				}
				if(!w.getPlayer(1).isMovingLeft()&&ev.keys[Action.PLAYER_MOVE_LEFT.key]) {
					performAction(Action.PLAYER_MOVE_LEFT);
					S.sendMessage(0, "PLAYER_MOVE_LEFT");
				}
				if(!w.getPlayer(1).isMovingRight()&&ev.keys[Action.PLAYER_MOVE_RIGHT.key]) {
					performAction(Action.PLAYER_MOVE_RIGHT);
					S.sendMessage(0, "PLAYER_MOVE_RIGHT");
				}
				if(ev.keys[Action.PLAYER_JUMP.key]) {
					performAction(Action.PLAYER_JUMP);
					S.sendMessage(0, "PLAYER_JUMP");
				}
				if(ev.keys[Action.PLAYER_CROUCH.key]&& !w.getPlayer(1).isCrouching()) {
					performAction(Action.PLAYER_CROUCH);
					S.sendMessage(0, "PLAYER_CROUCH");
				}
				if(!ev.keys[Action.PLAYER_CROUCH.key]&& w.getPlayer(1).isCrouching()) {
					performAction(Action.PLAYER_STAND);
					S.sendMessage(0, "PLAYER_STAND");
				}
				if(!w.getPlayer(1).isResting()&&!ev.keys[Action.PLAYER_JUMP.key]&&!ev.keys[Action.PLAYER_MOVE_RIGHT.key]&&!ev.keys[Action.PLAYER_MOVE_LEFT.key]) {
					performAction(Action.PLAYER_MOVE_REST);
					S.sendMessage(0, "PLAYER_MOVE_REST");
				}
			}
			if(S == null && C.getStateClient() == "Connected") {
				if(ev.keys[Action.PLAYER_ATTACK.key]&& !w.getPlayer(2).isAttacking()) {
					performAction(Action.PLAYER2_ATTACK);
					C.sendMessage( "PLAYER_ATTACK");
				}
				if(!w.getPlayer(2).isMovingLeft()&&ev.keys[Action.PLAYER_MOVE_LEFT.key]) {
					performAction(Action.PLAYER2_MOVE_LEFT);
					C.sendMessage( "PLAYER_MOVE_LEFT");
				}
				if(!w.getPlayer(2).isMovingRight()&&ev.keys[Action.PLAYER_MOVE_RIGHT.key]) {
					performAction(Action.PLAYER_MOVE_RIGHT);
					C.sendMessage( "PLAYER_MOVE_RIGHT");
				}
				if(ev.keys[Action.PLAYER_JUMP.key]) {
					performAction(Action.PLAYER2_JUMP);
					C.sendMessage("PLAYER_JUMP");
				}
				if(ev.keys[Action.PLAYER_CROUCH.key]&& !w.getPlayer(2).isCrouching()) {
					performAction(Action.PLAYER2_CROUCH);
					C.sendMessage( "PLAYER_CROUCH");
				}
				if(!ev.keys[Action.PLAYER_CROUCH.key]&& w.getPlayer(2).isCrouching()) {
					performAction(Action.PLAYER2_STAND);
					C.sendMessage( "PLAYER_STAND");
				}
				if(!w.getPlayer(2).isResting()&&!ev.keys[Action.PLAYER_JUMP.key]&&!ev.keys[Action.PLAYER_MOVE_RIGHT.key]&&!ev.keys[Action.PLAYER_MOVE_LEFT.key]) {
					performAction(Action.PLAYER2_MOVE_REST);
					C.sendMessage( "PLAYER_MOVE_REST");
				}
			}	
		}
	}
	private void performAction(Action a) {
		actions.push(a);
		
	}
	public void resolveActions() {
		while(!actions.isEmpty()) {
			Action a=actions.pop();
			
			switch (a) {
			case PLAYER_JUMP:
				w.PlayerJump();
				break;
			case PLAYER_ATTACK:
				w.getPlayer(1).toggleAttack(true);
			break;
			case PLAYER_MOVE_LEFT: 
				w.getPlayer(1).ChangeDirection(Direction.LEFT);
				break;	
			case PLAYER_MOVE_RIGHT:
				w.getPlayer(1).ChangeDirection(Direction.RIGHT);
				break;
			case PLAYER_MOVE_REST:
				w.getPlayer(1).ChangeDirection(Direction.REST);
				break;
			case PLAYER_CROUCH:
				w.getPlayer(1).toggleCrouch(true);
				break;
			case PLAYER_STAND:
				w.getPlayer(1).toggleCrouch(false);
				break;
			case PLAYER2_JUMP:
				w.Player2Jump();
				break;
			case PLAYER2_ATTACK:
				w.getPlayer(2).toggleAttack(true);
				break;
			case PLAYER2_MOVE_LEFT: 
				w.getPlayer(2).ChangeDirection(Direction.LEFT);
				break;	
			case PLAYER2_MOVE_RIGHT:
				w.getPlayer(2).ChangeDirection(Direction.RIGHT);
				break;
			case PLAYER2_MOVE_REST:
				w.getPlayer(2).ChangeDirection(Direction.REST);
				break;
			case PLAYER2_CROUCH:
				w.getPlayer(2).toggleCrouch(true);
				break;
			case PLAYER2_STAND:
				w.getPlayer(2).toggleCrouch(false);
				break;
			case CLOSE_GAME: 
				System.exit(0);
				break;
			case OPEN_SETTING:
				break;
			case SELECT_MENU:
				break;
			case START_GAME:
				if(DefaultMenu.getStatus() == "StartMenu")
				{
					DefaultMenu.ChangeStatus("LocalGame");
					painter.setRenderers(DefaultMenu.getRenderers());	
				}
				else
				{
					SoundClips.get("Menu").Stop();
					menu=false;
					if(MultiplayerGame)
					{
						if(C == null)
						{
							if(S.getStateServer() == "Connected")
							{
								S.setInGame(0, true);
							}
						}
						else if(S == null)
						{
							if(C.getStateClient() == "Connected")
							{
								C.setInGame(true);
							}
						}
					}
					loadLevel();
				}
				break;
			case START_MULTIPLAYER_GAME:
				DefaultMenu.ChangeStatus("Multiplayer");
				painter.setRenderers(DefaultMenu.getRenderers());	
				break;
			case START_TRAINING:
				break;
			case PAUSE:
				menu = true;
				SavedRenderers=painter.getRenderers();
				DefaultMenu.ChangeStatus("Pause");
				painter.setRenderers(DefaultMenu.getRenderers());	
				break;
			case RESUME:
				menu=false;
				SoundClips.get("Menu").Stop();
				painter.setRenderers(SavedRenderers);
				break;
			case CREAPARTITA:
				try 
				{
					DefaultMenu.ChangeStatus("WaitingConnection");
					painter.setRenderers(DefaultMenu.getRenderers());	
					S = new Server();
					WaitingConnection = true;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case PARTECIPA:
				try 
				{
					WaitingConnection = true;
					DefaultMenu.ChangeStatus("WaitingConnection");
					painter.setRenderers(DefaultMenu.getRenderers());	
					C = new Client("localhost");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case BACKTOMENU:
				DefaultMenu.ChangeStatus("StartMenu");
				painter.setRenderers(DefaultMenu.getRenderers());
				break;
			case CHOOSE_PLAYER_MULTIPLAYER:
				DefaultMenu.ChangeStatus("ChooseMultiplayerPlayer");
				painter.setRenderers(DefaultMenu.getRenderers());
				break;
			default:
				break;
			
			}
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
	public void setMenu(boolean menu) {this.menu = menu;}
}
