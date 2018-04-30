package Thread;
import object.GameObject;

import java.util.LinkedList;

import Graphics.myPanel;
import object.Character;

public class ThreadMoves extends Thread{

	GameObject obj;
	myPanel panel;
	LinkedList<GameObject> list;
	boolean running=false;

	public ThreadMoves()
	{
		super();
		
	}
	
	public void start(GameObject obj, myPanel panel) 
	{
		this.obj = obj;
		this.panel = panel;
		list = new LinkedList<GameObject>();
		list.add(obj);
		
		if(running )
			return;
	
		running =true;
		super.start();
	}
	
	public void run() 
	{
		
		long lastTime = System.nanoTime();
		double amountOfTicks = 10.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while(running)
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1)
			{
				tick();
				updates++;
				delta--;
			}
			
			frames++;
					
			if(System.currentTimeMillis() - timer > 1000)
			{
				timer += 1000;
				System.out.println("FPS: " + frames + " TICKS: " + updates);
				frames = 0;
				updates = 0;
			}
		}	
	}

	
	public void tick() 
	{
		((Character) obj).Move("Forward");
		 panel.rendergame(list);

	}
	
	
}
