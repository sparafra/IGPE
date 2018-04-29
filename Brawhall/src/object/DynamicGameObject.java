package object;

import object.GameObject;
import object.ObjectId;


	public abstract class DynamicGameObject extends GameObject  {
		float velX=0,velY=0;
		
		public DynamicGameObject(float x,float y,ObjectId id) {
			super(x,y,id);
		}

		public DynamicGameObject(float x,float y,float vx,float vy,ObjectId id) {
			super(x,y,id);
			velX=vx;
			velY=vy;
		}
	
}
