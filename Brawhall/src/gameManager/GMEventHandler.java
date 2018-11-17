package gameManager;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.util.LinkedList;

import org.json.JSONException;

import Network.Client;
import Network.Server;
import interfaces.Direction;
public class GMEventHandler implements EventHandler {
	
	GameManager gm;
	
	
	public boolean[] keys;
	KeyAdapter kl;
	LinkedList<JAction> actions;
	public GMEventHandler(GameManager g) 
	{
		
		gm=g;
		initEH();
		keys=new boolean[KeyEvent.KEY_LAST];
	}
	
	private void initEH()
	{
		actions=new LinkedList<JAction>();
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
		try {
			return keys[k];
		}
		finally {
			keys[k]=false;
		}
	}
	
	public void performAction(JAction a) {
		actions.push(a);		
	}
	public void performAction(Action a) {
		JAction ja;
		try {
			ja = new JAction(a);
			actions.push(ja);	
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	public void performAction(Action a,int client) {
		JAction ja;
		try {
			ja = new JAction(a);
			ja.put("client",client);
			actions.push(ja);	
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	public void resolveActions() {
		
		JAction a=actions.poll();
		if(a!=null)
		try {
			switch (a.getAction()) {
			case PLAYER_JUMP:
				gm.w.getPlayer(a.getInt("client")).jump();
				break;
			case PLAYER_ATTACK:
				gm.w.getPlayer(a.getInt("client")).toggleAttack(true);
			break;
			case PLAYER_MOVE_LEFT: 
				gm.w.getPlayer(a.getInt("client")).ChangeDirection(Direction.LEFT);
				break;	
			case PLAYER_MOVE_RIGHT:
				gm.w.getPlayer(a.getInt("client")).ChangeDirection(Direction.RIGHT);
				break;
			case PLAYER_MOVE_REST:
				gm.w.getPlayer(a.getInt("client")).ChangeDirection(Direction.REST);
				break;
			case PLAYER_CROUCH:
				gm.w.getPlayer(a.getInt("client")).toggleCrouch(true);
				break;
			case PLAYER_STAND:
				gm.w.getPlayer(a.getInt("client")).toggleCrouch(false);
				break;
			case CLOSE_GAME: 
				System.exit(0);
				break;
			case OPEN_SETTING:
				break;
			case SELECT_MENU:
				break;
			case START_GAME:
					gm.menu.ChangeStatus("Pause");
					gm.inMenu=false;
					gm.inGame=true;
					gm.loadLevel(new Level());
				break;
			case START_MULTIPLAYER_GAME:
					gm.menu.ChangeStatus("Pause");
					gm.w.setPlayerName(1,a.getString("playerName"));
					gm.w.setPlayerName(2,a.getString("player2Name"));
					gm.inMenu=false;
					gm.multiplayerGame=true;
					gm.inGame=true;
					gm.loadLevel(new Level());
				break;
			case MENU_START_LOCAL_GAME:
				gm.menu.ChangeStatus("ChooseLocalPlayer");
				gm.painter.setRenderers(gm.menu.getRenderers());	
				break;
			case MENU_START_MULTIPLAYER_GAME:
				gm.menu.ChangeStatus("Multiplayer");
				gm.painter.setRenderers(gm.menu.getRenderers());	
				break;
			case PAUSE:
				gm.inMenu = true;
				gm.SavedRenderers=gm.painter.getRenderers();
				gm.menu.ChangeStatus("Pause");
				gm.painter.setRenderers(gm.menu.getRenderers());	
				break;
			case RESUME:
				gm.inMenu=false;
				gm.SoundClips.get("Menu").Stop();
				gm.painter.setRenderers(gm.SavedRenderers);
				break;
			case CREAPARTITA:
				try 
				{	
					gm.S = new Server();
					performAction(Action.PARTECIPA);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
				break;
			case PARTECIPA:
				try 
				{
					gm.waitingConnection = true;
					gm.menu.ChangeStatus("waitingConnection");
					gm.painter.setRenderers(gm.menu.getRenderers());	
					gm.C = new Client();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case BACKTOMENU:
				gm.menu.ChangeStatus("StartMenu");
				gm.painter.setRenderers(gm.menu.getRenderers());
				break;
			case CHOOSE_PLAYER_MULTIPLAYER:
				gm.menu.ChangeStatus("ChooseMultiplayerPlayer");
				gm.myPlayer=a.getInt("target");
				gm.painter.setRenderers(gm.menu.getRenderers());
				break;
			case MENU_CLOSE_GAME:
				break;
			
			case PLAYER_CHOOSED_MULTIPLAYER:
				if(!gm.menu.getPlayer1Choosed())
					try {
						gm.C.sendMessage(a.toString());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				break;
			case GAMEOVER:
				if (gm.multiplayerGame) {
					gm.inGame=false;
					gm.multiplayerGame=false;
					gm.menu.ChangeStatus("StartMenu");
					gm.C.close();
					if (gm.S!=null) {
						gm.S.close();
					}
				}
				else {
					
				}
				break;
			default:
				break;
			
			
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
}
}
