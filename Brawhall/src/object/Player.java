package object;

import interfaces.CanFight;
import interfaces.CanJump;
import interfaces.CanMove;
import interfaces.Collides;
import interfaces.Drawable;
import interfaces.GravityDependent;
import object.BoundingBox.Side;

public class Player extends DynamicGameObject implements Collides, CanFight, CanJump, Drawable, GravityDependent, CanMove{

	float damage;
	float baseAttack;
	float moveSpeed;
	float atkSpeed;
	float atkRange;
	float weight=0.0f;
	
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
	public void move() {
		super.move();
	}
	@Override
	public void fall() {
		if(falling) {
			velY+=gravity;
		}
		if(velY>MAX_FALL_SPEED) {
		velY=MAX_FALL_SPEED;
		}
	}
	@Override
	public void tick() {
		super.tick();
		fall();
	}


	@Override
	public BoundingBox getBounds(Side s) {
		BoundingBox b=null;
		if (s==Side.Bottom) {
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
		}
			
		return b;
	}
	
	
}
