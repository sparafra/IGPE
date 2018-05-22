package gameManager;

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
//import object.Button;
import object.GameObject;
import object.Media;
import object.ObjectId;
import object.ObjectRenderer;
import object.Media.State;

public class Menu {
	float posx,posy;

	LinkedList<GameObject> controls;
	LinkedList<ObjectRenderer> renderers;
	
	int selected=0;
	
	float distanceBetweenButton = 3;
	
	GameManager gm;
	
	boolean Rendered = false;
	
	public Menu(GameManager gm)  {
		
		this.gm = gm;
		
		
		
		controls=new LinkedList<GameObject>();
		renderers =  new LinkedList<ObjectRenderer>();
		
		GameObject BG = new Background(gm.w.getWidth(), gm.w.getHeight());
		ObjectRenderer RBG = new ObjectRenderer(BG, gm);

		GameObject Local = new Button(80,50);
		ObjectRenderer RLocal = new ObjectRenderer(Local, gm);
		GameObject Multiplayer = new Button(80,50);
		ObjectRenderer RMultiplayer = new ObjectRenderer(Multiplayer, gm);
		GameObject Training = new Button(80,50);
		ObjectRenderer RTraining = new ObjectRenderer(Training, gm);
		GameObject Setting = new Button(80,50);
		ObjectRenderer RSetting = new ObjectRenderer(Setting, gm);
		GameObject Exit = new Button(80,50);
		ObjectRenderer RExit = new ObjectRenderer(Exit, gm);

		
		controls.add(BG);
		controls.add(Local);
		controls.add(Multiplayer);
		controls.add(Training);
		controls.add(Setting);
		controls.add(Exit);

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
		selected+=1;
		if(selected>controls.size()-1)
			selected=0;
	}
	public void selectPrev() {
		selected-=1;
		if(selected<0)
			selected=controls.size()-1;
	}
}
