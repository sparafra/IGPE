package object;

import interfaces.Collides;
import interfaces.Drawable;
import interfaces.GravityDependent;
import interfaces.Pickable;
import object.BoundingBox.Side;

public abstract class Drop extends StaticGameObject implements Collides, Drawable, Pickable, GravityDependent{

	float lifeTime;
	
	
	public Drop(float x,float y,int Height, int Width)
	{
		super(x, y, ObjectId.DROP);
		this.height = Height;
		this.width = Width;
	}


	@Override
	public void fall() {
		// TODO Auto-generated method stub
		
	}


	


	@Override
	public BoundingBox getBounds(Side s) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
