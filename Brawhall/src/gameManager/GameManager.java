package gameManager;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import gameManager.EventHandler;
import interfaces.Direction;

import java.util.HashMap;
import java.util.LinkedList;

import object.Background;
import object.Block;
import object.GameObject;
import object.Media;
import object.ObjectRenderer;
import object.Player;
import object.PlayerRenderer;
import object.SoundClip;
import object.State;
import windows.MyFrame;
import windows.MyPanel;
import world.Camera;
import world.World;



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
	
	LinkedList<ObjectRenderer>renderers;
	LinkedList<GameObject> objects;
	
	LinkedList<ObjectRenderer>SavedRenderers;
	LinkedList<GameObject> SavedObjects;
	
	HashMap<String, SoundClip> SoundClips;
	
	Media m;
	EventHandler ev;
	World w;
	MyPanel p;
	Camera cam;
	
	boolean running=false;
	boolean menu;
	
	public GameManager() 
	{
		tk = Toolkit.getDefaultToolkit();
		m = new Media();
		initGui();
		initSound();
		menu=true;
		w=new World(300,300,null);
		cam=new Camera(w,null);
		DefaultMenu = new Menu(this);
		p.setRenderers(DefaultMenu.getRenderers());		
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
		
		p=pn;
		f.setContentPane(p);
		f.setVisible(true);
	}
	public void loadLevel() {
		renderers=new LinkedList<ObjectRenderer>();
		objects=new LinkedList<GameObject>();
		w=new World(300,500,objects);
		GameObject o=new Player(50,0);
		GameObject o2 =new Player(250,0);
		GameObject bg = new Background(w.getWidth(), w.getHeight());
		w.addObject(o);
		w.addObject(o2);
		w.setPlayer((Player)o);
		w.setPlayer2((Player)o2);
		w.getPlayer().setName(DefaultMenu.Player1Preview.getSelectedPlayer());
		w.getPlayer2().setName(DefaultMenu.Player2Preview.getSelectedPlayer());
		loadPlayerSpecs(w.getPlayer());
		loadPlayerSpecs(w.getPlayer2());
		
		cam=new Camera(w,o);
		cam.setViewH(500);
		cam.setViewW(300);
		
		renderers.add(new ObjectRenderer(bg, this));
		renderers.add(new PlayerRenderer((Player)o,this));	
		renderers.add(new PlayerRenderer((Player)o2,this));	
		
		for (int i=50;i<w.getWidth()-50;i+=6) {
			o=new Block(i, w.getHeight()/2-18);
			w.addObject(o);
			renderers.add(new ObjectRenderer(o,this));
		}
		for (int i=w.getWidth()/2+20;i<w.getWidth();i+=6) {
			o=new Block(i, w.getHeight()/2-40);
			w.addObject(o);
			renderers.add(new ObjectRenderer(o,this));
		}
		for (int i=50;i<w.getWidth()/2-30;i+=6) {
			o=new Block(i, w.getHeight()/2-60);
			w.addObject(o);
			renderers.add(new ObjectRenderer(o,this));
		}
		for (int i=w.getWidth()/2-50;i<w.getWidth()/2+50;i+=6) {
			o=new Block(i, w.getHeight()/2-100);
			w.addObject(o);
			renderers.add(new ObjectRenderer(o,this));
		}
		p.setRenderers(renderers);
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
		while(running){
			double now = System.nanoTime();
			delta = (now - lastTime) / ns;
			tick(delta );
			lastTime = now;
		}	
	}
	public void tick(double delta) {	
		checkInput();
		if(!menu)
		{
			w.Update(delta);
			cam.tick();
			p.render();
		}
		else
		{
			SoundClips.get("Menu").Play();
			p.render();
		}
	}
	public void checkInput() 
	{
		if(menu) 
		{
			if(DefaultMenu.MenuState.equals("StartMenu"))
			{
				if(ev.keys[Action.SELECT_MENU.key])
					performAction(DefaultMenu.selectedAction()); 
				
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
						performAction(Action.START_GAME);
					}
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
					performAction(DefaultMenu.selectedAction()); 
				
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
					performAction(DefaultMenu.selectedAction()); 
				
				if(ev.keys[KeyEvent.VK_DOWN])
					DefaultMenu.selectNext(); 
				if(ev.keys[KeyEvent.VK_UP]) 
					DefaultMenu.selectPrev();
				if(!ev.keys[KeyEvent.VK_DOWN] && !ev.keys[KeyEvent.VK_UP] && !ev.keys[Action.SELECT_MENU.key])
					DefaultMenu.ready = true;
			}
		}
		else 
		{
			if(ev.keys[Action.PAUSE.key])
				performAction(Action.PAUSE);
			if(ev.keys[Action.PLAYER_ATTACK.key])
				performAction(Action.PLAYER_ATTACK);
			if(ev.keys[Action.PLAYER_MOVE_LEFT.key])
				performAction(Action.PLAYER_MOVE_LEFT);
			if(ev.keys[Action.PLAYER_MOVE_RIGHT.key])
				performAction(Action.PLAYER_MOVE_RIGHT);
			if(ev.keys[Action.PLAYER_JUMP.key])
				performAction(Action.PLAYER_JUMP);
			if(ev.keys[Action.PLAYER_CROUCH.key])
				performAction(Action.PLAYER_CROUCH);
			if(!ev.keys[Action.PLAYER_CROUCH.key])
				performAction(Action.PLAYER_STAND);
			if(!ev.keys[Action.PLAYER_JUMP.key]&&!ev.keys[Action.PLAYER_MOVE_RIGHT.key]&&!ev.keys[Action.PLAYER_MOVE_LEFT.key])
				performAction(Action.PLAYER_MOVE_REST);
			
			if(ev.keys[Action.PLAYER2_ATTACK.key])
				performAction(Action.PLAYER2_ATTACK);
			if(ev.keys[Action.PLAYER2_MOVE_LEFT.key])
				performAction(Action.PLAYER2_MOVE_LEFT);
			if(ev.keys[Action.PLAYER2_MOVE_RIGHT.key])
				performAction(Action.PLAYER2_MOVE_RIGHT);
			if(ev.keys[Action.PLAYER2_JUMP.key])
				performAction(Action.PLAYER2_JUMP);
			if(ev.keys[Action.PLAYER2_CROUCH.key])
				performAction(Action.PLAYER2_CROUCH);
			if(!ev.keys[Action.PLAYER2_CROUCH.key])
				performAction(Action.PLAYER2_STAND);
			if(!ev.keys[Action.PLAYER2_JUMP.key]&&!ev.keys[Action.PLAYER2_MOVE_RIGHT.key]&&!ev.keys[Action.PLAYER2_MOVE_LEFT.key])
				performAction(Action.PLAYER2_MOVE_REST);
		}	
	}
	public void performAction(Action a) {
		switch (a) {
		case PLAYER_JUMP:
			w.PlayerJump();
			break;
		case PLAYER_ATTACK:
			w.getPlayer().toggleAttack(true);
		break;
		case PLAYER_MOVE_LEFT: 
			w.getPlayer().ChangeDirection(Direction.LEFT);
			break;	
		case PLAYER_MOVE_RIGHT:
			w.getPlayer().ChangeDirection(Direction.RIGHT);
			break;
		case PLAYER_MOVE_REST:
			w.getPlayer().ChangeDirection(Direction.REST);
			break;
		case PLAYER_CROUCH:
			w.getPlayer().toggleCrouch(true);
			break;
		case PLAYER_STAND:
			w.getPlayer().toggleCrouch(false);
			break;
		case PLAYER2_JUMP:
			w.Player2Jump();
			break;
		case PLAYER2_ATTACK:
			w.getPlayer2().toggleAttack(true);
			break;
		case PLAYER2_MOVE_LEFT: 
			w.getPlayer2().ChangeDirection(Direction.LEFT);
			break;	
		case PLAYER2_MOVE_RIGHT:
			w.getPlayer2().ChangeDirection(Direction.RIGHT);
			break;
		case PLAYER2_MOVE_REST:
			w.getPlayer2().ChangeDirection(Direction.REST);
			break;
		case PLAYER2_CROUCH:
			w.getPlayer2().toggleCrouch(true);
			break;
		case PLAYER2_STAND:
			w.getPlayer2().toggleCrouch(false);
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
				p.setRenderers(DefaultMenu.getRenderers());	
			}
			else
			{
				SoundClips.get("Menu").Stop();
				menu=false;
				loadLevel();
			}
			break;
		case START_MULTIPLAYER_GAME:
			DefaultMenu.ChangeStatus("Multiplayer");
			p.setRenderers(DefaultMenu.getRenderers());	
			break;
		case START_TRAINING:
			break;
		case PAUSE:
			menu = true;
			SavedRenderers=p.getRenderers();
			DefaultMenu.ChangeStatus("Pause");
			p.setRenderers(DefaultMenu.getRenderers());	
			break;
		case RESUME:
			menu=false;
			SoundClips.get("Menu").Stop();
			p.setRenderers(SavedRenderers);
			break;
		default:
			break;
		
		}
	}
	
	public int ConvertX(float wx) {
		return (int) ((wx*p.getWidth())/cam.getWidth()) ;	
	}
	public int ConvertY(float wy) {
		return (int) ((wy*p.getHeight())/cam.getHeight()) ;	
	}
	public int ConvertPosX(float wx) {
		return (int) (((wx-cam.getPosX())*p.getWidth())/cam.getWidth()) ;
	}
	public int ConvertPosY(float wy) {
		return (int) (((wy-cam.getPosY())*p.getHeight())/cam.getHeight()) ;
	}
	
	public int ConvertPanelX(float px) {
		return (int) ((px*w.getWidth())/p.getWidth()) ;
	}
	public int ConvertPanelY(float py) {
		return (int) ((py*w.getHeight())/p.getHeight()) ;
	}
	
	public World getWorld() {return w;}
	public Media getMedia() {return m;}
	public void setMenu(boolean menu) {this.menu = menu;}
}
