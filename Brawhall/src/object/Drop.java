package object;

import interfaces.Collides;
import interfaces.Drawable;
import interfaces.GravityDependent;
import interfaces.Pickable;

public class Drop extends GameObject implements Collides, Drawable, Pickable, GravityDependent{

	float lifeTime;
	
	public Drop() {}
	public Drop(int Height, int Width)
	{
		super();
		this.Height = Height;
		this.Width = Width;
	}
	
}
