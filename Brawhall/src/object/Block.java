package object;

import object.BoundingBox.Side;
import object.ObjectId;

import java.util.LinkedList;

import interfaces.Collides;
import interfaces.Drawable;

public class Block extends GameObject implements Collides, Drawable{
	
	public Block(float x,float y) {
		super(x,y,ObjectId.BLOCK);
		width=12;
		height=12;
	}

//	@Override
//	public void tick(LinkedList<GameObject>objs) {
//		// TODO Auto-generated method stub
//		
//	}

	@Override
	public BoundingBox getBounds(Side s) {
		BoundingBox b;
		return b=new BoundingBox(this,posX,posY,width,height);
	}

	@Override
	public void Collision(LinkedList<GameObject> g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tick(LinkedList<GameObject> objects, double delta) {
		// TODO Auto-generated method stub
		
	}

}
