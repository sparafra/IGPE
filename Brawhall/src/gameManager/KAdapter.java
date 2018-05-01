package gameManager;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import interfaces.Direction;

public class KAdapter extends KeyAdapter {

	GameManager gm;
	
	public KAdapter(GameManager g)  {
		gm=g;
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key==KeyEvent.VK_D) {
			gm.w.MovePlayer(Direction.RIGHT);  
		}
		if(key==KeyEvent.VK_A) {
			gm.w.MovePlayer(Direction.LEFT);  
		}	
	}
	public void keyReleased(KeyEvent e) {
		
		switch (e.getKeyCode()){
			 default:
				 gm.w.MovePlayer(Direction.REST); 
			break;	
		}			
	}

}
