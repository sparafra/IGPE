package gameManager;

import java.util.LinkedList;

//import object.Button;
import object.GameObject;
import object.ObjectRenderer;

public class Menu {
	int posx,posy;
	

	LinkedList<GameObject> controls;
	LinkedList<ObjectRenderer> renderers;
	
	int selected=0;
	
	public Menu(int x,int y,String arg) {
		posx=x;
		posy=y;
		controls=new LinkedList<GameObject>();
		
		if (arg=="MAIN") {
			//GameObject t=new Button(posx,posy);
		}
	}

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
