package World;

import java.util.LinkedList;

import Objects.ObjectRenderer;

public class Painter extends Thread implements Runnable {
	
	LinkedList<ObjectRenderer>renderers;
	
	boolean running=false;
	boolean toRender=false;
	MyPanel p;
	public Painter() {
		// TODO Auto-generated constructor stub
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
			draw+=delta;
			lastTime = now;
			if(draw>1) {
				draw=0;
				if(toRender) {
					p.render();
					toRender=false;
				}
			}
		}	
		
	}
	public void start() {
		if(running )return;
		running =true;
		super.start();
		
	}
	public void addRenderer(ObjectRenderer r) {
		// TODO Auto-generated method stub
		renderers.add(r);
	}

	public void clear() {
		renderers.clear();
		
	}

	public void setPanel(MyPanel pn) {
		p=pn;
		
	}

	public void render() {
		
		toRender=true;
	}

	public void setRenderers(LinkedList<ObjectRenderer> r) {
		renderers=r;
		p.setRenderers(r);
	}

	public LinkedList<ObjectRenderer> getRenderers() {
		// TODO Auto-generated method stub
		return renderers;
	}

	public MyPanel getPanel() {
		return p;
	}

	
	
}
