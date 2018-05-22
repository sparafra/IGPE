package object;

import java.awt.Image;
import java.util.LinkedList;

import interfaces.Clickable;
import interfaces.Drawable;

public  class Control extends StaticGameObject implements Clickable, Drawable {
	
	Image x;
	
	public Control(float x,float y ,ObjectId id) 
	{
		super(x, y, id);
		
	}
	
	public Control(float x,float y ,int Height, int Width,ObjectId id) 
	{
		super(x, y,Width,Height, id);
		
	}

	@Override
	public void tick(LinkedList<GameObject> objects, double delta) {
		// TODO Auto-generated method stub
		
	}
	
}
