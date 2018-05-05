package object;

import java.util.LinkedList;

import interfaces.CanMove;
import interfaces.Direction;
import object.GameObject;
import object.ObjectId;
	


	public abstract class DynamicGameObject extends GameObject implements CanMove  {
		
		float velX=0.0f,velY=0.0f;
		float moveSpeed=0.2f;
		float maxMoveSpeed=2.0f;
		Direction dir=Direction.REST;
		
		public DynamicGameObject(float x,float y,ObjectId id) {
			super(x,y,id);
		}

		public DynamicGameObject(float x,float y,float vx,float vy,ObjectId id) {
			super(x,y,id);
			
		}
		public abstract void tick(LinkedList<GameObject> objs,double delta); 
			
		
	public void move() {
		
		posX+=velX;
		posY+=velY;
		
		
		
	}
	@Override
	public void ChangeDirection(Direction d) {
		dir=d;
		
	}
}
