package GameManager;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.Timer;

import Graphics.myPanel;
import Thread.MovementTask;
import World.World;
import object.GameObject;
import object.Character;
public class EventHandler {
	
	myPanel panel;
	LinkedList<GameObject> objList;
	World w;
	Timer tmrMovement;
	MovementTask mTask;
	
	boolean Pressed = false;
	public EventHandler(myPanel panel, World w, LinkedList<GameObject> objList) 
	{
		this.panel = panel;
		this.objList = objList;
		this.w = w;
		initEH();
	}
	
	private void initEH()
	{
		panel.setFocusable(true);
		panel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {	
			       for(int k=0; k<objList.size(); k++)
			       {		    	  
			    		   if(objList.get(k) instanceof Character)
			    		   {
			    			   //Thread per gestire il movimento continuo	
			    			   tmrMovement = new Timer();
			    			   mTask = new MovementTask(objList.get(k), panel, "Forward");
			    			   tmrMovement.schedule(mTask, 0, 60);
			    			   
			    			   //((Character) objList.get(k)).Move("Forward");
			    			   panel.rendergame(objList);
			    			   
			    		   }
			    		   System.out.println("Clic su GameObject" + objList.get(k) );
			       }			    			     
			    } 
			public void mouseReleased(MouseEvent e) {			
			       for(int k=0; k<objList.size(); k++)
			       {
			    	   
			    		   if(objList.get(k) instanceof Character)
			    		   {
			    			   tmrMovement.cancel();
			    			   ((Character) objList.get(k)).Move("SteadyForward");
			    			   panel.rendergame(objList);
			    		   }
			    		   System.out.println("Clic su GameObject" + objList.get(k) );
			    	  
			       }
			       
			    } 
		});
		
		panel.addKeyListener(new KeyAdapter() {
			
			public void keyPressed(KeyEvent e)
			{
				int key = e.getKeyCode();
	    		System.out.println("Pressed KeyCode: "+ e.getKeyCode());
	    		if(!Pressed)
	    		{
	    			Pressed = true;
	    			if(key == KeyEvent.VK_RIGHT)
					{
						for(int k=0; k<objList.size(); k++)
					       {		    	  
					    		   if(objList.get(k) instanceof Character)
					    		   {
					    			   //Thread per gestire il movimento continuo	
					    			   tmrMovement = new Timer();
					    			   mTask = new MovementTask(objList.get(k), panel, "Forward");
					    			   tmrMovement.schedule(mTask, 0, 60);
					    			   
					    			   //((Character) objList.get(k)).Move("Forward");
					    			   panel.rendergame(objList);
					    			   
					    		   }
					    		   //System.out.println("Clic su GameObject" + objList.get(k) );
					       }
					}
					else if (key == KeyEvent.VK_LEFT)
					{
						for(int k=0; k<objList.size(); k++)
					       {		    	  
					    		   if(objList.get(k) instanceof Character)
					    		   {
					    			   //Thread per gestire il movimento continuo	
					    			   tmrMovement = new Timer();
					    			   mTask = new MovementTask(objList.get(k), panel, "Back");
					    			   tmrMovement.schedule(mTask, 0, 60);
					    			   
					    			   //((Character) objList.get(k)).Move("Forward");
					    			   panel.rendergame(objList);
					    			   
					    		   }
					    		   //System.out.println("Clic su GameObject" + objList.get(k) );
					       }
					}
	    		}
				
			}
			
			public void keyReleased(KeyEvent e)
			{
				int key = e.getKeyCode();
				System.out.println("Released KeyCode: "+ e.getKeyCode());
				
				if(Pressed)
				{
					Pressed = false;
					if(key == KeyEvent.VK_RIGHT)
					{
						for(int k=0; k<objList.size(); k++)
					       {
					    	   
					    		   if(objList.get(k) instanceof Character)
					    		   {
					    			   tmrMovement.cancel();
					    			   ((Character) objList.get(k)).Move("SteadyForward");
					    			   panel.rendergame(objList);
					    		   }
					    		   //System.out.println("Clic su GameObject" + objList.get(k) );
					    	  
					       }
					}
					else if (key == KeyEvent.VK_LEFT)
					{
						for(int k=0; k<objList.size(); k++)
					       {		    	  
					    		   if(objList.get(k) instanceof Character)
					    		   {
					    			   tmrMovement.cancel();
					    			   ((Character) objList.get(k)).Move("SteadyBack");
					    			   panel.rendergame(objList);
					    			   
					    		   }
					    		   //System.out.println("Clic su GameObject" + objList.get(k) );
					       }
					}
				}
				
			}
			
		});
		
	}
	
	public int ConvertX(float wx) {
		return (int) ((wx*w.getWidth())/panel.getWidth()) ;
		
	}
	public int ConvertY(float wy) {
		return (int) ((wy*w.getHeight())/panel.getHeight()) ;
		
	}
}
