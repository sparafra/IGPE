package object;

import interfaces.CanMove;
import object.GameObject;
import object.ObjectId;
	


	public abstract class DynamicGameObject extends GameObject implements CanMove  {
		
		float velX=0,velY=0;
		public DynamicGameObject(float x,float y,ObjectId id) {
			super(x,y,id);
		}

		public DynamicGameObject(float x,float y,float vx,float vy,ObjectId id) {
			super(x,y,id);
			
		}
		public  void tick() {
			move();
		}
	public void move() {
		posX+=velX;
		posY+=velY;
	}
}
