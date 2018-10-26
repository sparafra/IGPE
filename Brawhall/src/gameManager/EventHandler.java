package gameManager;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
public class EventHandler {
	
	GameManager gm;
	
	
	public boolean[] keys;
	KeyAdapter kl;
	public EventHandler(GameManager g) 
	{
		
		gm=g;
		initEH();
		keys=new boolean[KeyEvent.KEY_LAST];
	}
	
	private void initEH()
	{
		gm.painter.getPanel().setFocusable(true);
		gm.painter.getPanel().requestFocusInWindow();
		gm.painter.getPanel().addMouseListener(new MouseAdapter() {			
			
		});
				
		gm.painter.getPanel().addKeyListener( new KeyAdapter() {
			private long lastPressProcessed = 0;
			@Override
			public void keyPressed(KeyEvent e) {
				
				 if(System.currentTimeMillis() - lastPressProcessed > 50) {
			            //Do your work here...
						int key = e.getKeyCode();
						keys[key]=true;
			            lastPressProcessed = System.currentTimeMillis();
			        }     
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
	public boolean getKey(int k) {
		if(k>=KeyEvent.KEY_FIRST&&k<=KeyEvent.KEY_LAST) {
			boolean r=keys[k];
			keys[k]=false;
			return r;
		}
		return false;
	}
}
