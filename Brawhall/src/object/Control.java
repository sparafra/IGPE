package object;

import java.awt.Image;

import interfaces.Clickable;
import interfaces.Drawable;

public class Control extends GameObject implements Clickable, Drawable {
	
	Image x;
	
	public Control() { }
	public Control(int Height, int Width) 
	{
		super();
		this.Height = Height;
		this.Width = Width;
	}
	
}
