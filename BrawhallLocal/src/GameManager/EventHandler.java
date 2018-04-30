package GameManager;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import Graphics.myPanel;
import World.World;
import object.GameObject;
import object.Character;
public class EventHandler {
	
	myPanel panel;
	LinkedList<GameObject> objList;
	World w;
	
	public EventHandler(myPanel panel, World w, LinkedList<GameObject> objList) 
	{
		this.panel = panel;
		this.objList = objList;
		this.w = w;
		initEH();
	}
	
	private void initEH()
	{
		panel.addMouseListener(new MouseAdapter() {
			/*
			public void mouseClicked(MouseEvent e) {			
			       for(int k=0; k<objList.size(); k++)
			       {
			    	   if(objList.get(k).isOnTarget(ConvertX(e.getX()), ConvertY(e.getY())))
			    	   {
			    		   if(objList.get(k) instanceof Character)
			    		   {
			    			   ((Character) objList.get(k)).Move("Forward");
			    			   panel.rendergame(objList);
			    		   }
			    		   System.out.println("Clic su GameObject" + objList.get(k) );
			    	   }
			       }
			       
			    }*/ 
			public void mousePressed(MouseEvent e) {			
			       for(int k=0; k<objList.size(); k++)
			       {		    	  
			    		   if(objList.get(k) instanceof Character)
			    		   {
			    			   //Thread per gestire il movimento continuo			    				  			    			   
			    			   ((Character) objList.get(k)).Move("Forward");
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
			    			   ((Character) objList.get(k)).Move("Steady");
			    			   panel.rendergame(objList);
			    		   }
			    		   System.out.println("Clic su GameObject" + objList.get(k) );
			    	  
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
