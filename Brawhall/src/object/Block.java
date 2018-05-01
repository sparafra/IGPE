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

	@Override
	public void tick(LinkedList<GameObject>objs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BoundingBox getBounds(Side s) {
		BoundingBox b=null;
		/*if (s==Side.Bottom) {
			b=new BoundingBox((int)(posX + (width/2)-(width/4)) ,(int)(posY+height/2),(int) width/2, (int)height/2);
		}
		else if(s==Side.Top) {
			b=new BoundingBox((int)(posX + (width/2)-(width/4)) ,(int)posY,(int) width/2, (int)height/2);
		}
		else if(s==Side.Left) {
			b=new BoundingBox((int)(posX+width-(width/10) ) ,(int)(posY+(height/10)/2),(int) width/10, (int)(height-height/10));
		}
		else if(s==Side.Right) {
			b=new BoundingBox((int)(posX ) ,(int)(posY+(height/10)/2),(int) width/10, (int)(height-(height/10)));
		}*/
		b=new BoundingBox((int)posX,(int)posY,(int)width,(int)height);	
		return b;
	}

	@Override
	public void Collision(LinkedList<GameObject> g) {
		// TODO Auto-generated method stub
		
	}

}
