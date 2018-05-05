package object;

import java.util.LinkedList;

import interfaces.CanFight;
import interfaces.CanJump;
import interfaces.CanMove;
import interfaces.Collides;
import interfaces.Direction;
import interfaces.Drawable;
import interfaces.GravityDependent;
import object.BoundingBox.Side;

public class Player extends DynamicGameObject implements Collides, CanFight, CanJump, Drawable, GravityDependent, CanMove{

	float damage;
	float baseAttack;
	
	float atkSpeed;
	float atkRange;
	float weight=0.0f;
	boolean falling=true;
	boolean jumping=false;
	
	public Player(float x,float y) 
	{
		
		super(x, y, ObjectId.PLAYER);
		this.height = 20;
		this.width = 10;
	}
	
	
	public Player(float x,float y, float Width,float Height) 
	{
		super(x, y, Width, Height, ObjectId.PLAYER);
		this.height = Height;
		this.width = Width;
	}
	public void getHit(float hitDmg) 
	{ 
		//Add hitDmg to damage
	}
	public float hit() 
	{ 
		return 0;
		//return BaseAttack
	}
	@Override
	public void ChangeDirection(Direction d) {
		dir=d;
		
	}
	@Override
	public void move() {
		super.move();
		if (dir==Direction.RIGHT) {
			velX+=moveSpeed;
			if(velX>maxMoveSpeed) {
			velX=maxMoveSpeed;
			}
		}
		if (dir==Direction.LEFT) {
			velX-=moveSpeed;
			if(velX<-maxMoveSpeed) {
			velX=-maxMoveSpeed;
			}
		}
			else if(dir==Direction.REST) {
				if(velX>=moveSpeed) {
					velX-=moveSpeed;
				}
				else  if(velX<=-moveSpeed) {
					velX+=moveSpeed;
				}
				else {
					velX=0;
				}
			}		
	}
//	@Override
//	public void tick(LinkedList<GameObject> objects) {
//		
//		move();
//		Collision(objects);
//		fall();
//	}
	@Override
	public void tick(LinkedList<GameObject> objects, double delta) {
		move(delta);
		Collision(objects);
		fall(delta);
	}	
    public void fall(double delta) {
    	if(falling) {
			velY+=gravity*delta;
		}
		if(velY>MAX_FALL_SPEED) {
		velY=MAX_FALL_SPEED;
		}
		
	}


	@Override
	public void Collision(LinkedList<GameObject> list) {
		for (int i=0;i<list.size();i++) {
			
			GameObject t=list.get(i);
			if(t.id==ObjectId.BLOCK) {
				if(this.getBounds(Side.Bottom).intersects( ((Block)t).getBounds(Side.Top)) ){
					posY=t.posY-this.height;
					velY=0;
					falling=false;
					jumping=false;
				}
				else if(this.getBounds(Side.Top).intersects( ((Block)t).getBounds(Side.Left)) ){
					velY=0;
					
					posY=t.posY+t.height;
					
				}
				
				else if(this.getBounds(Side.Right).intersects( ((Block)t).getBounds(Side.Left)) ){
					velX=0;
					
					posX=t.posX-width;
					
				}
				else if(this.getBounds(Side.Left).intersects( ((Block)t).getBounds(Side.Left)) ){
					velX=0;
					
					posX=t.posX+t.width;
					
				}
				
				else {
					falling=true;
				}
			}
		}
	}

	@Override
	public BoundingBox getBounds(Side s) {
		BoundingBox b=null;
		if (s==Side.Bottom) {
			b=new BoundingBox((posX + (width/2)-(width/4)) ,(posY+height/2), width/2, height/2);
		}
		else if(s==Side.Top) {
			b=new BoundingBox((posX + (width/2)-(width/4)) ,posY, width/2, height/2);
		}
		else if(s==Side.Right) {
			b=new BoundingBox((posX+width )-1 ,(posY+(height/10)/2), 1, (height-height/10));
		}
		else if(s==Side.Left) {
			b=new BoundingBox((posX ) ,(posY+(height/10)/2), width/10, (height-(height/10)));
		}
			
		return b;
	}

	@Override
	public void jump() {
		if(!jumping) {
		 velY-=jumpVel;
		 falling=true;
		 jumping=true;
		}
	}
	private void move(double delta) {
		posX+=velX*delta;
		posY+=velY*delta;
		if (dir==Direction.RIGHT) {
			velX+=moveSpeed*delta;
			if(velX>maxMoveSpeed) {
			velX=maxMoveSpeed;
			}
		}
		if (dir==Direction.LEFT) {
			velX-=moveSpeed*delta;
			if(velX<-maxMoveSpeed) {
			velX=-maxMoveSpeed;
			}
		}
			else if(dir==Direction.REST) {
				if(velX>=moveSpeed) {
					velX-=moveSpeed*delta;
				}
				else  if(velX<=-moveSpeed) {
					velX+=moveSpeed*delta;
				}
				else {
					velX=0;
				}
			}	
	}


	@Override
	public void fall() {
		// TODO Auto-generated method stub
		
	}


	


	
	
	
}
