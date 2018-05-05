package gameManager;

import java.awt.Image;
import java.awt.Toolkit;
import gameManager.EventHandler;
import java.util.LinkedList;

import object.Block;
import object.GameObject;
import object.ObjectRenderer;
import object.Player;
import windows.MyFrame;
import windows.MyPanel;
import world.Camera;
import world.World;

public class GameManager extends Thread implements Runnable{
	
	static final int panelWidth=1440;
	public Camera getCam() {
		return cam;
	}

	static final int panelHeight=900;

	
	
	Toolkit tk = Toolkit.getDefaultToolkit();
	Image BackgroundMenu;
	
	LinkedList<ObjectRenderer> m;
	
	LinkedList<ObjectRenderer>renderers;
	LinkedList<GameObject> objects;
	
	EventHandler ev;
	World w;
	MyPanel p;
	Camera cam;
	
	boolean running=false;
	boolean menu;
	public GameManager() {
		renderers=new LinkedList<ObjectRenderer>();
		objects=new LinkedList<GameObject>();
		initGui();
		menu=true;
		w=new World(500,700,objects);
		
		loadLevel();
			
	}
	public void initGui() {
		MyFrame f= new MyFrame(panelWidth,panelHeight);
		MyPanel pn=new MyPanel(this,panelWidth, panelHeight);
		p=pn;
		f.setContentPane(p);
		f.setVisible(true);
	}
	public void loadLevel() {
		GameObject o=new Player(50,0);
		w.addObject(o);
		w.setPlayer((Player)o);
		
		cam=new Camera(w,o);
		
		
		renderers.add(new ObjectRenderer(o,this));	
		
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
	}
	public void start() {
		if(running )return;
		ev=new EventHandler(this);
		running =true;
		super.start();
	}
	public void run() {
		//fancy stuff
		double lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while(running){
			double now = System.nanoTime();
			delta += (now - lastTime) / ns;
			tick((now - lastTime) / ns );
			lastTime = now;
			while(delta >= 1){
				//tick();
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
//	public void tick() {
//			
//				
//			w.Update();
//			cam.tick();
//			p.render(renderers);
//	}
	public void tick(double delta) {
		
		
		w.Update(delta);
		cam.tick();
		p.render(renderers);
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
	
	public static void main(String[] args) {
		GameManager gm= new GameManager();
		gm.start();
	}
}
