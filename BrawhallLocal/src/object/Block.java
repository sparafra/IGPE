package object;

import interfaces.Collides;
import interfaces.Drawable;

public class Block extends GameObject implements Collides, Drawable{
	
	public Block(int x, int y, int Height, int Width) 
	{
		super();
		this.x = x;
		this.y = y;
		this.Height = Height;
		this.Width = Width;
	}
	public Block() { }
	
}
