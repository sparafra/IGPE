package object;

import interfaces.Collides;
import interfaces.Drawable;
import interfaces.GravityDependent;
import interfaces.Pickable;

public class Drop extends StaticGameObject implements Collides, Drawable, Pickable, GravityDependent{

	float lifeTime;
	
	
	public Drop(float x,float y,int Height, int Width)
	{
		super(x, y, ObjectId.DROP);
		this.height = Height;
		this.width = Width;
	}
	
}
