package gameManager;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import gameManager.EventHandler;
import interfaces.Direction;

import java.util.LinkedList;

import object.Background;
import object.Block;
import object.GameObject;
import object.Media;
import object.ObjectRenderer;
import object.Player;
import object.PlayerRenderer;
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
		menu=true;
		w=new World(300,300,null);
		cam=new Camera(w,null);
		DefaultMenu = new Menu(this);
		p.setRenderers(DefaultMenu.getRenderers());		
	}
	public void initGui() 
	{
		MyFrame f= new MyFrame(panelWidth,panelHeight);
		MyPanel pn=new MyPanel(this,panelWidth, panelHeight);
		p=pn;
		f.setContentPane(p);
		f.setVisible(true);
	}
	public void loadLevel() {
		renderers=new LinkedList<ObjectRenderer>();
		objects=new LinkedList<GameObject>();
		w=new World(500,700,objects);
		GameObject o=new Player(50,0);
		GameObject bg = new Background(w.getWidth(), w.getHeight());
		w.addObject(o);
		w.setPlayer((Player)o);
		cam=new Camera(w,o);
		renderers.add(new ObjectRenderer(bg, this));
		renderers.add(new PlayerRenderer((Player)o,this));	
		
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
			p.render();
		}
	}
	public void checkInput() {
		if(menu) 
		{
			if(ev.keys[Action.SELECT_MENU.key])
				performAction(DefaultMenu.selectedAction()); 
			
			if(ev.keys[KeyEvent.VK_DOWN])
				DefaultMenu.selectNext(); 
			if(ev.keys[KeyEvent.VK_UP]) 
				DefaultMenu.selectPrev();
			if(!ev.keys[KeyEvent.VK_DOWN] && !ev.keys[KeyEvent.VK_UP])
				DefaultMenu.ready = true;
		}
		else 
		{
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
		}	
	}
	public void performAction(Action a) {
		switch (a) {
		case PLAYER_JUMP:
			w.PlayerJump();
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
		case CLOSE_GAME: 
			System.exit(0);
			break;
		case OPEN_SETTING:
			break;
		case SELECT_MENU:
			break;
		case START_GAME:
			menu=false;
			loadLevel();
			break;
		case START_MULTIPLAYER_GAME:
			break;
		case START_TRAINING:
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
