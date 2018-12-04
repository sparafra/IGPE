package gameManager;


import java.util.LinkedList;

import Objects.Background;
import Objects.Block;
import Objects.Border;
import Objects.GameObject;

public class Level {
	int width;
	int height;
	LinkedList<GameObject> objects;

	
	
	public Level() {
		
		height=260;
		width=440;
		
		objects= new LinkedList<GameObject>();
		

		
		GameObject bg = new Background(getWidth(), getHeight());
		objects.add(bg);
		
		//borders
		addObject(new Border(-width,-height,getWidth()/2,getHeight()*3));
		addObject(new Border(-width,-height,width*3,height/2));
		addObject(new Border(-width,height+height/2,width*3,height/2));
		addObject(new Border (width+width/2,-height,width/2,height*3));
		
		addObject(new Block(20,getHeight()/2+40,300,10));
		addObject(new Block(getWidth()/2+20, getHeight()/2+20,100,10));
		addObject(new Block(60, getHeight()/2,100,10));
		addObject(new Block(getWidth()/2-50, getHeight()/2-40,200,10));
		
		
		
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
