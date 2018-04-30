package gameManager;

import java.awt.Image;
import java.awt.Toolkit;
import gameManager.EventHandler;
import java.util.LinkedList;

import object.GameObject;
import object.ObjectRenderer;
import object.Player;
import windows.MyFrame;
import windows.MyPanel;
import world.World;

public class GameManager extends Thread implements Runnable{
	
	static final int panelWidth=1440;
	static final int panelHeight=900;

	
	
	Toolkit tk = Toolkit.getDefaultToolkit();
	Image BackgroundMenu;
	
	LinkedList<ObjectRenderer> m;
	
	LinkedList<ObjectRenderer>renderers;
	LinkedList<GameObject> objects;
	
	EventHandler ev;
	World w;
	MyPanel p;
	
	boolean running=false;
	boolean menu;
	
	public void initGui() {
		MyFrame f= new MyFrame(panelWidth,panelHeight);
		MyPanel pn=new MyPanel(panelWidth, panelHeight);
		p=pn;
		f.setContentPane(p);
	}
	
	public void start() {
		if(running )return;
		ev=new EventHandler(this);
		running =true;
		super.start();
	}
	
	public void run() {
		
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				tick();
				updates++;
				delta--;
			}
			
			frames++;
					
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println("FPS: " + frames + " TICKS: " + updates);
				frames = 0;
				updates = 0;
			}
		}
		
	}
	
	public void tick() {
		
		w.Update();
			p.render(renderers);
		
			
	}
	
	public GameManager() {
		
		
		renderers=new LinkedList<ObjectRenderer>();
		objects=new LinkedList<GameObject>();
		initGui();
		menu=true;
		
		w=new World(200,200,objects);
		
		GameObject p=new Player(10,10);
		w.addObject(p);
		w.setPlayer((Player)p);
		ObjectRenderer r=new ObjectRenderer(p,this);
		renderers.add(r);
		
		
	}


	public int ConvertX(float wx) {
		return (int) ((wx*p.getWidth())/w.getWidth()) ;
		
	}
	public int ConvertY(float wy) {
		return (int) ((wy*p.getHeight())/w.getHeight()) ;
		
	}
	public int ConvertPanelX(float px) {
		return (int) ((px*w.getWidth())/p.getWidth()) ;
		
	}
	public int ConvertPanelY(float py) {
		return (int) ((py*w.getHeight())/p.getHeight()) ;
		
	}
	
	public static void main(String[] args) {
		
		GameManager gm= new GameManager();
		gm.start();
	}
}
