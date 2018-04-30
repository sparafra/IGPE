package object;

import java.awt.Image;

import interfaces.Clickable;
import interfaces.Drawable;

public class Control extends GameObject implements Clickable, Drawable {
	
	Image Background;
	
	public Control() { }
	public Control(int Height, int Width, Image Background) 
	{
		super();
		this.Height = Height;
		this.Width = Width;
		this.Background = Background;
	}
	public Control(int x, int y, int Width, int Height) 
	{
		super();
		this.x = x;
		this.y = y;
		this.Height = Height;
		this.Width = Width;
	}
	public Image getImage() {return Background;}
	
}
