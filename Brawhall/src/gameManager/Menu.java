package gameManager;
import gameManager.Action;
import java.util.LinkedList;
import object.Background;
import object.Button;
import object.Control;
import object.ControlRenderer;
import object.GameObject;
import object.ObjectRenderer;

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
	
	public Menu(GameManager gm)  {
		
		this.gm = gm;
		
		height=gm.cam.getHeight();
		width=gm.cam.getWidth();
		
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
		
		
	}
	
	public Menu(GameManager gm, String status)  
	{
		background = new Background(gm.w.getWidth(), gm.w.getHeight());
		ObjectRenderer RBG = new ObjectRenderer(background, gm);
		
		renderers.add(RBG);

		if(status == "LocalGame")
		{
			//LOCAL SELECTION MENU'
			
			
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
		
	}
	
	public LinkedList<ObjectRenderer> getRenderers(){return renderers;}
	
	
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
		return b.getAction();
				
	} 
		
}
