package object;

import interfaces.Collides;
import interfaces.Drawable;

public class Block extends GameObject implements Collides, Drawable{
	
	public Block(int Height, int Width) 
	{
		super();
		this.Height = Height;
		this.Width = Width;
	}
	public Block() { }
	
}
