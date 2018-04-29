package object;

import object.ObjectId;
import interfaces.Collides;
import interfaces.Drawable;

public class Block extends GameObject implements Collides, Drawable{
	
	public Block(float x,float y) {
		super(x,y,ObjectId.BLOCK);
		
	}

}
