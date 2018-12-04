package gameManager;
import gameManager.Action;
import java.util.LinkedList;

import Objects.Background;
import Objects.Button;
import Objects.Control;
import Objects.ControlRenderer;
import Objects.GameObject;
import Objects.Media;
import Objects.ObjectId;
import Objects.ObjectRenderer;
import Objects.PlayerPreview;
import Objects.SoundClip;

public class Menu extends GameObject {
	
	LinkedList<Control> controls;
	LinkedList<ObjectRenderer> renderers; 
	GameObject background;
	
	
	
	int selectedIndex=0;
	float distanceBetweenButton = 3;
	
	
	
	GameManager gm;
	

	
	String MenuState;
	
	int PlayerSelectionTurn;
	
	PlayerPreview Player1Preview;
	PlayerPreview Player2Preview;
	
	boolean Player1Choosed = false;
	boolean Player2Choosed = false;

	private double delay=0;
	private double inputDelay=30;
	private boolean ready;
	private boolean locked;
	
	public Menu(float w,float h,GameManager gm)  {
		super(0,0,w,h,ObjectId.MENU);
		MenuState = "StartMenu";
		this.gm = gm;
		height=h;
		width=w;
		initGUI();
	}
	
	public Menu(float w,float h,GameManager gm, String status)  {
		this(w,h,gm);
		MenuState = status;
		
	}
	
	public LinkedList<ObjectRenderer> getRenderers(){return renderers;}
	public void initGUI()
	{
		controls=new LinkedList<Control>();
		renderers =  new LinkedList<ObjectRenderer>();
		
		background = new Background(width,height);
		ObjectRenderer RBG = new ObjectRenderer(background, gm);
		
		renderers.add(RBG);
		
		if(MenuState == "StartMenu"){
			selectedIndex = 0;
			Control Local = new Button(50,25,Action.MENU_START_LOCAL_GAME);
			ObjectRenderer RLocal = new ControlRenderer(Local, gm);
			Control Multiplayer = new Button(50,25,Action.MENU_START_MULTIPLAYER_GAME);
			ObjectRenderer RMultiplayer = new ControlRenderer(Multiplayer, gm);
			
			Control Exit = new Button(50,25,Action.MENU_CLOSE_GAME);
			ObjectRenderer RExit = new ControlRenderer(Exit, gm);
			
			controls.add(Local);
			controls.add(Multiplayer);
			controls.add(Exit);
			controls.get(selectedIndex).setSelected(true);
			
			renderers.add(RLocal);
			renderers.add(RMultiplayer);
			renderers.add(RExit);

			float posx = (width/2) - (controls.get(1).getWidth()/2);
			float posy = (height/2) - (((controls.size()-1)/2)*controls.get(1).getHeight());
			
			float pad = 10;
			
			for(int k=0; k<controls.size(); k++)
			{
				controls.get(k).setPosX(posx);
				controls.get(k).setPosY(posy +(pad*(k-1))+ (controls.get(k).getHeight()+(controls.get(k).getHeight()*(k-1))));
			}
			
			
		}
		else if(MenuState == "ChooseLocalPlayer")
		{
			//LOCAL SELECTION MENU'
			PlayerSelectionTurn = 1;
			Player1Preview = new PlayerPreview(30,200, Media.getCharactersName(), true);
			Player2Preview = new PlayerPreview(30,200, Media.getCharactersName(), false);
			ObjectRenderer rPlayer1Preview = new ControlRenderer(Player1Preview, gm);
			ObjectRenderer rPlayer2Preview = new ControlRenderer(Player2Preview, gm);

			
			Player1Preview.setPosX(50);
			Player1Preview.setPosY(80);
			Player2Preview.setPosX(200);
			Player2Preview.setPosY(80);
			
			controls.add(Player1Preview);
			controls.add(Player2Preview);
			renderers.add(rPlayer1Preview);
			renderers.add(rPlayer2Preview);
		} 
		else if(MenuState =="Multiplayer")
		{
			// MULTIPLAYER SELECTION MENU'
			selectedIndex = 0;
			Control CreaPartita = new Button(50,25,Action.CREAPARTITA);
			ObjectRenderer RCreaPartita = new ControlRenderer(CreaPartita, gm);
			Control Partecipa = new Button(50,25,Action.PARTECIPA);
			ObjectRenderer RPartecipa = new ControlRenderer(Partecipa, gm);
			Control Back = new Button(50,25,Action.BACKTOMENU);
			ObjectRenderer RBack = new ControlRenderer(Back, gm);
			
			controls.add(CreaPartita);
			controls.add(Partecipa);
			controls.add(Back);
			
			controls.get(selectedIndex).setSelected(true);
			
			renderers.add(RCreaPartita);
			renderers.add(RPartecipa);
			renderers.add(RBack);
			
			float posx = (width/2) - (controls.get(1).getWidth()/2);
			float posy = (height/2) - (((controls.size()-1)/2)*controls.get(1).getHeight());
			
			float pad = 10;
			
			for(int k=0; k<controls.size(); k++)
			{
				controls.get(k).setPosX(posx);
				controls.get(k).setPosY(posy +(pad*(k-1))+ (controls.get(k).getHeight()+(controls.get(k).getHeight()*(k-1))));
			}
			
		} 
		
		else if(MenuState == "Pause")
		{
			// PAUSE SELECTION MENU'
			Control Continue = new Button(50,25,Action.RESUME);
			ObjectRenderer RContinue = new ControlRenderer(Continue, gm);
			Control Exit = new Button(50,25,Action.CLOSE_GAME);
			ObjectRenderer RExit = new ControlRenderer(Exit, gm);
			
			controls.add(Continue);
			controls.add(Exit);
			
			controls.get(selectedIndex).setSelected(true);
			
			renderers.add(RContinue);
			renderers.add(RExit);
			
			float posx = (width/2) - (controls.get(1).getWidth()/2);
			float posy = (height/2) - (((controls.size()-1)/2)*controls.get(1).getHeight());
			
			float pad = 10;
			
			for(int k=0; k<controls.size(); k++)
			{
				controls.get(k).setPosX(posx);
				controls.get(k).setPosY(posy +(pad*(k-1))+ (controls.get(k).getHeight()+(controls.get(k).getHeight()*(k-1))));
			}
		}
		else if(MenuState =="waitingConnection")
		{
			((Background) background).setState("Waiting");
		}
		else if(MenuState =="ChooseMultiplayerPlayer")
		{
			//LOCAL SELECTION MENU'
			PlayerSelectionTurn = 1;
			Player1Preview = new PlayerPreview(30,200, Media.getCharactersName(), true);
			
			ObjectRenderer rPlayer1Preview = new ControlRenderer(Player1Preview, gm);
			

			Player1Preview.setPosX(50);
			Player1Preview.setPosY(80);
			
			
			controls.add(Player1Preview);
			
			renderers.add(rPlayer1Preview);
			
		}
		else if(MenuState == "GameOver")
		{
			selectedIndex = 0;
			Player1Preview = new PlayerPreview(30,200, Media.getCharactersName(), true);
			ObjectRenderer rPlayer1Preview = new ControlRenderer(Player1Preview, gm);

			
			Player1Preview.setPosX(60);
			Player1Preview.setPosY(50);
		
			
			controls.add(Player1Preview);
			renderers.add(rPlayer1Preview);
			
			// PAUSE SELECTION MENU'
			Control Restart = new Button(50,25,Action.START_MULTIPLAYER_GAME);
			ObjectRenderer RRestart = new ControlRenderer(Restart, gm);
			Control Exit = new Button(50,25,Action.CLOSE_GAME);
			ObjectRenderer RExit = new ControlRenderer(Exit, gm);
			
			controls.add(Restart);
			controls.add(Exit);
			
			controls.get(selectedIndex).setSelected(true);
			
			renderers.add(RRestart);
			renderers.add(RExit);
			
			float posx = (width/2) - (controls.get(1).getWidth()/2);
			float posy = (height/2) - (((controls.size()-1)/2)*controls.get(1).getHeight());
			
			float pad = 10;
			
			for(int k=1; k<controls.size(); k++)
			{
				controls.get(k).setPosX(posx);
				controls.get(k).setPosY(posy +(pad*(k-1))+ (controls.get(k).getHeight()+(controls.get(k).getHeight()*(k-1))));
			}
		}
	}
	public void selectNext() {  
		
			controls.get(selectedIndex).setSelected(false);
			selectedIndex+=1;
			if(selectedIndex>controls.size()-1)
				selectedIndex=0;
			controls.get(selectedIndex).setSelected(true); 
			setReady(false);
		
	}
	public void selectPrev() { 
		
		
			controls.get(selectedIndex).setSelected(false);
			selectedIndex-=1;
			if(selectedIndex<0)
				selectedIndex=controls.size()-1;
			controls.get(selectedIndex).setSelected(true); 
			setReady(false);
		
	}
	
	public Action selectedAction() {
		
		Button b=(Button)controls.get(selectedIndex);
		setReady(false);
		return b.getAction();
		
		
	} 
	
	public void nextPlayer() 
	{
		if(PlayerSelectionTurn == 1)
		{
			Player1Preview.Next();					
		}
		else if (PlayerSelectionTurn == 2)
		{
			Player2Preview.Next();
		}	
		setReady(false);
	}
	public void nextPlayer(int PlayerId) 
	{
		
			if(PlayerId == 1)
			{
				Player1Preview.Next();
			}
			else if (PlayerId == 2)
			{
				Player2Preview.Next();
				
			}
			setReady(false);
		
	}
	public void prevPlayer() 
	{
		if(!isLocked()) {
			if(PlayerSelectionTurn == 1)
			{
				Player1Preview.Prev();
			}
			else if(PlayerSelectionTurn == 2)
			{
				Player2Preview.Prev();
			}
			setReady(false);
		}
	}
	public void prevPlayer(int PlayerId) 
	{
		if(!isLocked()) {
			if(PlayerId == 1)
			{
				Player1Preview.Prev();
			}
			else if(PlayerId == 2)
			{
				Player2Preview.Prev();
			}
			setReady(false);	
		}
	}
	public void nextPlayerSelectionTurn() 
	{
		if(!isLocked()) {
				if(PlayerSelectionTurn == 1)
				{
					Player1Preview.setActive(false);
					if(Player2Preview!=null)
					Player2Preview.setActive(true);
					PlayerSelectionTurn++;
				}
				else if (PlayerSelectionTurn == 2)
				{
					PlayerSelectionTurn = -1;
					Player2Preview.setActive(false);
				}
		}
	}
	public int getPlayerSelectionTurn() {return PlayerSelectionTurn;} 
	public void setPlayer1Choosed(boolean C) 
	{
			Player1Choosed = C;
			Player1Preview.setActive(false);
		
	}
	public void setPlayer2Choosed(boolean C) 
	{
		
			Player2Choosed = C;
			Player2Preview.setActive(false);
		
	}
	public boolean getPlayer1Choosed(){return Player1Choosed;} 
	public boolean getPlayer2Choosed(){return Player2Choosed;} 
	public void ChangeStatus(String Status) 
	{
		MenuState = Status;
		selectedIndex=0;
		initGUI();
		
		setReady(false);
	}
	public String getStatus() {return MenuState;}

	public void tick(double delta) {
		delay+=delta;
		if (delay>inputDelay) {
			setReady(true);
			delay=0;
		}	
	}
	public boolean isBusy() {return !isReady();}

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public void hold() {
		ready=false;
		
	}

	public boolean isLocked() {
		return locked;
	}

	@Override
	public void tick(LinkedList<GameObject> objects, double delta) {
		// TODO Auto-generated method stub
		
	}

}
