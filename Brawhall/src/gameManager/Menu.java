package gameManager;
import gameManager.Action;
import java.util.LinkedList;
import object.Background;
import object.Button;
import object.Control;
import object.ControlRenderer;
import object.GameObject;
import object.ObjectRenderer;
import object.PlayerPreview;

public class Menu {
	float posx,posy;

	LinkedList<Control> controls;
	LinkedList<ObjectRenderer> renderers; 
	GameObject background;
	
	float height;
	float width;
	
	int selectedIndex=0;
	float distanceBetweenButton = 3;
	
	boolean ready = true;
	
	GameManager gm;
	
	boolean Rendered = false;
	
	String MenuState;
	
	int PlayerSelectionTurn;
	
	PlayerPreview Player1Preview;
	PlayerPreview Player2Preview;
	
	public Menu(GameManager gm)  {
		
		MenuState = "StartMenu";
		this.gm = gm;
		
		height=gm.cam.getHeight();
		width=gm.cam.getWidth();
		
		initGUI();
		/*
		controls=new LinkedList<Control>();
		renderers =  new LinkedList<ObjectRenderer>();
		
		background = new Background(gm.w.getWidth(), gm.w.getHeight());
		ObjectRenderer RBG = new ObjectRenderer(background, gm);

		Control Local = new Button(40,25,Action.START_GAME);
		ObjectRenderer RLocal = new ControlRenderer(Local, gm);
		Control Multiplayer = new Button(40,25,Action.START_MULTIPLAYER_GAME);
		ObjectRenderer RMultiplayer = new ControlRenderer(Multiplayer, gm);
		Control Training = new Button(40,25,Action.START_TRAINING);
		ObjectRenderer RTraining = new ControlRenderer(Training, gm);
		Control Setting = new Button(40,25,Action.OPEN_SETTING);
		ObjectRenderer RSetting = new ControlRenderer(Setting, gm);
		Control Exit = new Button(40,25,Action.CLOSE_GAME);
		ObjectRenderer RExit = new ControlRenderer(Exit, gm);

		
		
		controls.add(Local);
		controls.add(Multiplayer);
		controls.add(Training);
		controls.add(Setting);
		controls.add(Exit);

		controls.get(selectedIndex).setSelected(true);
		
		renderers.add(RBG);
		renderers.add(RLocal);
		renderers.add(RMultiplayer);
		renderers.add(RTraining);
		renderers.add(RSetting);
		renderers.add(RExit);

		posx = (width/2) - (controls.get(1).getWidth()/2);
		posy = (height/2) - (((controls.size()-1)/2)*controls.get(1).getHeight());
		
		float pad = 10;
		
		for(int k=0; k<controls.size(); k++)
		{
			controls.get(k).setPosX(posx);
			controls.get(k).setPosY(posy +(pad*(k-1))+ (controls.get(k).getHeight()+(controls.get(k).getHeight()*(k-1))));

		}
		*/
		
	}
	
	public Menu(GameManager gm, String status)  
	{
		MenuState = status;
		this.gm = gm;
		
		height=gm.cam.getHeight();
		width=gm.cam.getWidth();
		
		initGUI();
		/*
		controls=new LinkedList<Control>();
		renderers =  new LinkedList<ObjectRenderer>();
		
		background = new Background(gm.w.getWidth(), gm.w.getHeight());
		ObjectRenderer RBG = new ObjectRenderer(background, gm);
		
		renderers.add(RBG);
		
		if(status == "StartMenu")
		{
			Control Local = new Button(40,25,Action.START_GAME);
			ObjectRenderer RLocal = new ControlRenderer(Local, gm);
			Control Multiplayer = new Button(40,25,Action.START_MULTIPLAYER_GAME);
			ObjectRenderer RMultiplayer = new ControlRenderer(Multiplayer, gm);
			Control Training = new Button(40,25,Action.START_TRAINING);
			ObjectRenderer RTraining = new ControlRenderer(Training, gm);
			Control Setting = new Button(40,25,Action.OPEN_SETTING);
			ObjectRenderer RSetting = new ControlRenderer(Setting, gm);
			Control Exit = new Button(40,25,Action.CLOSE_GAME);
			ObjectRenderer RExit = new ControlRenderer(Exit, gm);

			
			
			controls.add(Local);
			controls.add(Multiplayer);
			controls.add(Training);
			controls.add(Setting);
			controls.add(Exit);

			controls.get(selectedIndex).setSelected(true);
			
			renderers.add(RLocal);
			renderers.add(RMultiplayer);
			renderers.add(RTraining);
			renderers.add(RSetting);
			renderers.add(RExit);

			posx = (width/2) - (controls.get(1).getWidth()/2);
			posy = (height/2) - (((controls.size()-1)/2)*controls.get(1).getHeight());
			
			float pad = 10;
			
			for(int k=0; k<controls.size(); k++)
			{
				controls.get(k).setPosX(posx);
				controls.get(k).setPosY(posy +(pad*(k-1))+ (controls.get(k).getHeight()+(controls.get(k).getHeight()*(k-1))));

			}
			
		}
		else if(status == "LocalGame")
		{
			//LOCAL SELECTION MENU'
			PlayerSelectionTurn = 1;
			Player1Preview = new PlayerPreview(30,150, gm.getMedia().getCharactersName());
			Player2Preview = new PlayerPreview(30,150, gm.getMedia().getCharactersName());
			ObjectRenderer rPlayer1Preview = new ControlRenderer(Player1Preview, gm);
			ObjectRenderer rPlayer2Preview = new ControlRenderer(Player2Preview, gm);

			
			Player1Preview.setPosX(50);
			Player1Preview.setPosY(50);
			Player2Preview.setPosX(200);
			Player2Preview.setPosY(50);
			
			controls.add(Player1Preview);
			controls.add(Player2Preview);
			renderers.add(rPlayer1Preview);
			renderers.add(rPlayer2Preview);
		} 
		else if(status =="Multiplayer")
		{
			// MULTIPLAYER SELECTION MENU'
		} 
		else if(status == "Training")
		{
			// TRAINING SELECTION MENU'
		} 
		else if(status == "Setting")
		{
			// TRAINING SELECTION MENU'
		}
		*/
	}
	
	public LinkedList<ObjectRenderer> getRenderers(){return renderers;}
	
	public void initGUI()
	{
		controls=new LinkedList<Control>();
		renderers =  new LinkedList<ObjectRenderer>();
		
		background = new Background(gm.w.getWidth(), gm.w.getHeight());
		ObjectRenderer RBG = new ObjectRenderer(background, gm);
		
		renderers.add(RBG);
		
		if(MenuState == "StartMenu")
		{
			Control Local = new Button(40,25,Action.START_GAME);
			ObjectRenderer RLocal = new ControlRenderer(Local, gm);
			Control Multiplayer = new Button(40,25,Action.START_MULTIPLAYER_GAME);
			ObjectRenderer RMultiplayer = new ControlRenderer(Multiplayer, gm);
			Control Training = new Button(40,25,Action.START_TRAINING);
			ObjectRenderer RTraining = new ControlRenderer(Training, gm);
			Control Setting = new Button(40,25,Action.OPEN_SETTING);
			ObjectRenderer RSetting = new ControlRenderer(Setting, gm);
			Control Exit = new Button(40,25,Action.CLOSE_GAME);
			ObjectRenderer RExit = new ControlRenderer(Exit, gm);

			
			
			controls.add(Local);
			controls.add(Multiplayer);
			controls.add(Training);
			controls.add(Setting);
			controls.add(Exit);

			controls.get(selectedIndex).setSelected(true);
			
			renderers.add(RLocal);
			renderers.add(RMultiplayer);
			renderers.add(RTraining);
			renderers.add(RSetting);
			renderers.add(RExit);

			posx = (width/2) - (controls.get(1).getWidth()/2);
			posy = (height/2) - (((controls.size()-1)/2)*controls.get(1).getHeight());
			
			float pad = 10;
			
			for(int k=0; k<controls.size(); k++)
			{
				controls.get(k).setPosX(posx);
				controls.get(k).setPosY(posy +(pad*(k-1))+ (controls.get(k).getHeight()+(controls.get(k).getHeight()*(k-1))));

			}
			
		}
		else if(MenuState == "LocalGame")
		{
			//LOCAL SELECTION MENU'
			PlayerSelectionTurn = 1;
			Player1Preview = new PlayerPreview(30,150, gm.getMedia().getCharactersName());
			Player2Preview = new PlayerPreview(30,150, gm.getMedia().getCharactersName());
			ObjectRenderer rPlayer1Preview = new ControlRenderer(Player1Preview, gm);
			ObjectRenderer rPlayer2Preview = new ControlRenderer(Player2Preview, gm);

			
			Player1Preview.setPosX(50);
			Player1Preview.setPosY(50);
			Player2Preview.setPosX(200);
			Player2Preview.setPosY(50);
			
			controls.add(Player1Preview);
			controls.add(Player2Preview);
			renderers.add(rPlayer1Preview);
			renderers.add(rPlayer2Preview);
		} 
		else if(MenuState =="Multiplayer")
		{
			// MULTIPLAYER SELECTION MENU'
		} 
		else if(MenuState == "Training")
		{
			// TRAINING SELECTION MENU'
		} 
		else if(MenuState == "Setting")
		{
			// TRAINING SELECTION MENU'
		}
	}
	public void selectNext() {  
		
		if(ready)
		{
			controls.get(selectedIndex).setSelected(false);
			selectedIndex+=1;
			if(selectedIndex>controls.size()-1)
				selectedIndex=0;
			controls.get(selectedIndex).setSelected(true); 
			ready =false;
		}
	}
	public void selectPrev() { 
		
		if(ready)
		{
			controls.get(selectedIndex).setSelected(false);
			selectedIndex-=1;
			if(selectedIndex<0)
				selectedIndex=controls.size()-1;
			controls.get(selectedIndex).setSelected(true); 
			ready = false;
		}
	}
	
	public Action selectedAction() {
		Button b=(Button)controls.get(selectedIndex);
		ready = false;
		return b.getAction();
		
	} 
	
	public void nextPlayer() 
	{
		if(ready)
		{
			if(PlayerSelectionTurn == 1)
			{
				Player1Preview.Next();
				ready = false;
			}
			else if (PlayerSelectionTurn == 2)
			{
				Player2Preview.Next();
				ready = false;
			}
		}
	}
	public void prevPlayer() 
	{
		if(ready)
		{
			if(PlayerSelectionTurn == 1)
			{
				Player1Preview.Prev();
			}
			else if(PlayerSelectionTurn == 2)
			{
				Player2Preview.Prev();
			}
			ready = false;

		}		
	}
	
	public void nextPlayerSelectionTurn() 
	{
		if(ready)
		{
			if(PlayerSelectionTurn == 1)
				PlayerSelectionTurn++;
			else if (PlayerSelectionTurn == 2)
				PlayerSelectionTurn = -1;
			ready = false;
		}
	}
	public int getPlayerSelectionTurn() {return PlayerSelectionTurn;} 
	
	public void ChangeStatus(String Status) 
	{
		MenuState = Status; 
		initGUI();
	}
	public String getStatus() {return MenuState;}
}
