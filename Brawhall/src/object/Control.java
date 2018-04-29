package object;

import java.awt.Image;

import interfaces.Clickable;
import interfaces.Drawable;

public abstract class Control extends StaticGameObject implements Clickable, Drawable {
	
	Image x;
	
	public Control(float x,float y ,ObjectId id) 
	{
		super(x, y, id);
		
	}
	
	public Control(float x,float y ,int Height, int Width,ObjectId id) 
	{
		super(x, y,Width,Height, id);
		
	}
	
}
