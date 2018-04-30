package object;

import object.BoundingBox.Side;
import object.ObjectId;
import interfaces.Collides;
import interfaces.Drawable;

public class Block extends GameObject implements Collides, Drawable{
	
	public Block(float x,float y) {
		super(x,y,ObjectId.BLOCK);
		
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BoundingBox getBounds(Side s) {
		// TODO Auto-generated method stub
		return null;
	}

}
