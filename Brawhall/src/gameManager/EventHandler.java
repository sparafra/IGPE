package gameManager;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import interfaces.Direction;
public class EventHandler {
	
	GameManager gm;
	
	public EventHandler(GameManager g) 
	{
		gm=g;
		
		initEH();
	}
	
	private void initEH()
	{
		gm.p.setFocusable(true);
		gm.p.requestFocusInWindow();
		gm.p.addMouseListener(new MouseAdapter() {			
			public void mousePressed(MouseEvent e) {			
			     		gm.w.MovePlayer(Direction.RIGHT);    			     
			    } 
			public void mouseReleased(MouseEvent e) {			
					gm.w.MovePlayer(Direction.REST);  
			       
			    } 
		});
				
		gm.p.addKeyListener( new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if(key==KeyEvent.VK_D) {
					gm.w.MovePlayer(Direction.RIGHT);  
				}
				if(key==KeyEvent.VK_A) {
					gm.w.MovePlayer(Direction.LEFT);  
				}
				if(key==KeyEvent.VK_SPACE) {
					gm.w.PlayerJump();  
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				 gm.w.MovePlayer(Direction.REST); 
			}
			
		});
		
	}
}
