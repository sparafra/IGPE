package gameManager;

import java.awt.Image;
import java.awt.Toolkit;
import java.beans.EventHandler;
import java.util.LinkedList;

import object.Block;
import object.GameObject;
import object.ObjectRenderer;
import windows.MyPanel;
import world.World;

public class GameManager extends Thread implements Runnable{

	Toolkit tk = Toolkit.getDefaultToolkit();
	Image BackgroundMenu;
	

	LinkedList<object.ObjectRenderer> m;
	LinkedList<ObjectRenderer>l;
	EventHandler ev;
	World w;
	MyPanel p;
	boolean running=false;
	boolean menu=false;
	
	public void start() {
		if(running )return;
		
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
		
		if (menu) { 	
			p.render(m);
		}
		else {
			p.render(l);
		}
	}
	
	public GameManager(MyPanel pn) {
		
		l=new LinkedList<ObjectRenderer>();
		m=new LinkedList<ObjectRenderer>();
		
		p=pn;
		w=new World(50,50);
		GameObject j= new Block(25,25);
		ObjectRenderer r =new ObjectRenderer(j,this);
		m.add(r);
		Block b= new Block(10,10);
		ObjectRenderer rb =new ObjectRenderer(b,this);
		l.add(rb);
	}

	public int ConvertX(float wx) {
		return (int) ((wx*p.getWidth())/w.getWidth()) ;
		
	}
	public int ConvertY(float wy) {
		return (int) ((wy*p.getHeight())/w.getHeight()) ;
		
	}
}
