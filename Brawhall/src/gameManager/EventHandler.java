package gameManager;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
public class EventHandler {
	
	GameManager gm;
	
	
	public boolean[] keys;
	public EventHandler(GameManager g) 
	{
		
		gm=g;
		initEH();
		keys=new boolean[KeyEvent.KEY_LAST];
	}
	
	private void initEH()
	{
		gm.p.setFocusable(true);
		gm.p.requestFocusInWindow();
		gm.p.addMouseListener(new MouseAdapter() {			
			
		});
				
		gm.p.addKeyListener( new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				keys[key]=true;
				
			}
			@Override
			public void keyReleased(KeyEvent e) {
				 {
						int key = e.getKeyCode();		
						keys[key]=false;	 
				}
			}
			
		});	
	}
	
}
