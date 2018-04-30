package World;

import java.util.LinkedList;

import object.GameObject;

public class World {

int width,height;
	
	LinkedList<GameObject> list;

	public World(int w, int h) {
		width=w;
		height=h;
	}
	
	
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}


	public void Update() 
	{
		
		
	}
	
	public LinkedList<GameObject> getObjList(){return list;}
	public void setObjList(LinkedList<GameObject> objList) {this.list = objList;}
}
