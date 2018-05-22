package object;

import java.util.LinkedList;

import interfaces.Clickable;
import interfaces.Drawable;

public class Button extends GameObject implements Clickable, Drawable {
	
	public Button(int width, int height) 
	{
		super(width, height, ObjectId.BUTTON);
		this.width = width;
		this.height = height;
	}
	public Button(int x, int y, int Width, int Height) 
	{
		super(x, y, Width, Height, ObjectId.BUTTON);
		this.posX = x;
		this.posY = y;
		this.height = Height;
		this.width = Width;
	}
	
	public void tick(LinkedList<GameObject> objects, double delta) {
		
	}
	
}