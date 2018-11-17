package gameManager;


import java.util.LinkedList;

import Objects.Background;
import Objects.Block;
import Objects.GameObject;

public class Level {
	int width;
	int height;
	LinkedList<GameObject> objects;

	
	
	public Level() {
		GameObject o;
		height=500;
		width=300;
				objects= new LinkedList<GameObject>();
		
		GameObject bg = new Background(getWidth(), getHeight());
		objects.add(bg);
		
		o=new Block(20,getHeight()/2+40,100,10);
		for (int i=20;i<getWidth()-50;i+=6) {
			o=new Block(i, getHeight()/2+40);
			addObject(o);
			
		}
		for (int i=getWidth()/2+20;i<getWidth();i+=6) {
			o=new Block(i, getHeight()/2+20);
			addObject(o);
			
		}
		for (int i=15;i<getWidth()/2-30;i+=6) {
			o=new Block(i, getHeight()/2);
			addObject(o);
			
		}
		for (int i=getWidth()/2-50;i<getWidth()/2+50;i+=6) {
			o=new Block(i, getHeight()/2-40);
			addObject(o);
			
		}
		
	}
	
	private void addObject(GameObject o) {
		objects.add(o);
		
	}



	public int getWidth() {
		// TODO Auto-generated method stub
		return width;
	}

	public int getHeight() {
		// TODO Auto-generated method stub
		return height;
	}

	public LinkedList<GameObject> getObjects() {
		// TODO Auto-generated method stub
		return objects;
	}

	
}
