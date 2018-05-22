package gameManager;
import gameManager.Action;
import interfaces.Clickable;
import interfaces.Drawable;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import object.Background;
import object.Button;
import object.Control;
//import object.Button;
import object.GameObject;
import object.Media;
import object.ObjectId;
import object.ObjectRenderer;
import object.Media.State;

public class Menu {
	float posx,posy;

	LinkedList<Control> controls;
	LinkedList<ObjectRenderer> renderers; 
	GameObject background;
	
	
	int selectedIndex=0;
	float distanceBetweenButton = 3;
	
	GameManager gm;
	
	boolean Rendered = false;
	
	public Menu(GameManager gm)  {
		
		this.gm = gm;
		
		
		
		controls=new LinkedList<Control>();
		renderers =  new LinkedList<ObjectRenderer>();
		
		background = new Background(gm.w.getWidth(), gm.w.getHeight());
		ObjectRenderer RBG = new ObjectRenderer(background, gm);

		Control Local = new Button(80,50,Action.START_GAME);
		ObjectRenderer RLocal = new ObjectRenderer(Local, gm);
		Control Multiplayer = new Button(80,50,Action.START_MULTIPLAYER_GAME);
		ObjectRenderer RMultiplayer = new ObjectRenderer(Multiplayer, gm);
		Control Training = new Button(80,50,Action.START_TRAINING);
		ObjectRenderer RTraining = new ObjectRenderer(Training, gm);
		Control Setting = new Button(80,50,Action.OPEN_SETTING);
		ObjectRenderer RSetting = new ObjectRenderer(Setting, gm);
		Control Exit = new Button(80,50,Action.CLOSE_GAME);
		ObjectRenderer RExit = new ObjectRenderer(Exit, gm);

		
		
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

		posx = (gm.w.getWidth()/2) - (controls.get(1).getWidth()/2);
		posy = (gm.w.getWidth()/2) - (((controls.size()-1)/2)*controls.get(1).getHeight());
		
		float pad = 10;
		
		for(int k=1; k<controls.size(); k++)
		{
			controls.get(k).setPosX(posx);
			controls.get(k).setPosY(posy +(pad*(k-1))+ (controls.get(k).getHeight()+(controls.get(k).getHeight()*(k-1))));

		}
		
		
	}
	
	public LinkedList<ObjectRenderer> getRenderers(){return renderers;}
	
	
	public void selectNext() { 
		controls.get(selectedIndex).setSelected(false);
		selectedIndex+=1;
		if(selectedIndex>controls.size()-1)
			selectedIndex=0;
		controls.get(selectedIndex).setSelected(true);
	}
	public void selectPrev() { 
		controls.get(selectedIndex).setSelected(false);
		selectedIndex-=1;
		if(selectedIndex<0)
			selectedIndex=controls.size()-1;
		controls.get(selectedIndex).setSelected(true);
	}
	
	public Action selectedAction() {
		Button b=(Button)controls.get(selectedIndex);
		return b.getAction();
				
	} 
		
}
