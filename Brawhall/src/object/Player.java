package object;

import java.util.LinkedList;

import interfaces.CanCrouch;
import interfaces.CanFight;
import interfaces.CanJump;
import interfaces.CanMove;
import interfaces.Collides;
import interfaces.Direction;
import interfaces.Drawable;
import interfaces.GravityDependent;
import object.BoundingBox.Side;

public class Player extends DynamicGameObject implements Collides, CanFight, CanJump, Drawable, GravityDependent, CanMove,CanCrouch{

	String Name;
	
	float damage=0.0f;
	float life=250000.0f;
	float baseAttack=1.0f;
	
	float standHeight;
	
	
	float atkSpeed=10.0f;
	float atkRange=5.0f;
	float weight=0.0f;
	
	boolean falling=true;
	boolean jumping=false;
	boolean crouching=false;
	boolean attacking=false;
	
	double attackTimer=0;
	HitBox h=null;
	Direction facing=Direction.RIGHT;
	
	public Player(float x,float y) 
	{
		super(x, y, ObjectId.PLAYER);
		this.height = 20;
		this.width = 10;
		standHeight=20;
		Name = "";
	}
	public Player(float x,float y, float Width,float Height) 
	{
		super(x, y, Width, Height, ObjectId.PLAYER);
		this.height = Height;
		this.width = Width;
		Name = "";
	}
	public void tick(LinkedList<GameObject> objects, double delta) {
		move(delta);
		crouch(delta);
		if (attacking) {
			attack(objects,delta);
		}
		
		Collision(objects);
		fall(delta);
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
			
			if(t.id==ObjectId.BLOCK ) {
				
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
			
			if(t.id==ObjectId.PLAYER ) {
				Player p=(Player)t;
				if(this != p)
				{
					if(this.getBounds(Side.Bottom).intersects( ((Player)t).getBounds(Side.Top)) ){
						posY=t.posY-this.height;
						velY=0;
						falling=false;
						jumping=false;
					}
					else if(this.getBounds(Side.Top).intersects( ((Player)t).getBounds(Side.Left)) ){
						velY=0;
						posY=t.posY+t.height;
					}
					else if(this.getBounds(Side.Right).intersects( ((Player)t).getBounds(Side.Left)) ){
						velX=0;
						posX=t.posX-width;	
					}
					else if(this.getBounds(Side.Left).intersects( ((Player)t).getBounds(Side.Right)) ){
						velX=0;
						posX=t.posX+t.width;
					}
					else {
						falling=true;
					}
				}
				
			}
		}
	}
	public boolean Intersect(BoundingBox b) {
		return (b.intersects(this.getBounds(Side.Bottom))||b.intersects(this.getBounds(Side.Top))||b.intersects(this.getBounds(Side.Right))||b.intersects(this.getBounds(Side.Left)));
	}
	@Override
	public BoundingBox getBounds(Side s) {
		BoundingBox b=null;
		if (s==Side.Bottom) {
			b=new BoundingBox(this,(posX + (width/2)-(width/4)) ,(posY+height/2), width/2, height/2);
		}
		else if(s==Side.Top) {
			b=new BoundingBox(this,(posX + (width/2)-(width/4)) ,posY, width/2, height/2);
		}
		else if(s==Side.Right) {
			b=new BoundingBox(this,(posX+width )-1 ,(posY+(height/10)/2), 1, (height-height/10));
		}
		else if(s==Side.Left) {
			b=new BoundingBox(this,(posX ) ,(posY+(height/10)/2), width/10, (height-(height/10)));
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
		switch (dir){
		case RIGHT:
			facing=Direction.RIGHT;
			velX+=moveSpeed*delta;
			if(velX>maxMoveSpeed) 
				velX=maxMoveSpeed;
			break;
		case LEFT: 
			facing=Direction.LEFT;
			velX-=moveSpeed*delta;
			if(velX<-maxMoveSpeed) 
				velX=-maxMoveSpeed;
			break;
		case REST: 
				if(velX>=moveSpeed) 
					velX-=moveSpeed*delta;
				else  if(velX<=-moveSpeed) 
					velX+=moveSpeed*delta;
				else 
					velX=0;
		default:
			break;				
			}	
	}
	@Override
	public void crouch(double delta) {
		if (!crouching){
			if(height<standHeight) {
				height+=crouchSpeed*delta;
				posY-=crouchSpeed*delta;
			}
			else {
				height=standHeight;
			}
		}
		else {
			if(height>standHeight/2) {
				height-=crouchSpeed*delta;
				posY+=crouchSpeed*delta;
			}
			else {
				height=standHeight/2;
			}
		}
	}
	
	@Override
	public void attack(LinkedList<GameObject> list,double delta) {
		attackTimer-=delta;
		if(attackTimer<0) 
		{
			for (int i=0;i<list.size();i++) 
			{
				GameObject t=list.get(i);
				switch (facing) {
				
					case LEFT:
						h=new HitBox(this, (posX-atkRange), (posY) ,  atkRange,  height/3);
						break;
					case REST:
						break;
					case RIGHT:
							h=new HitBox(this, (posX+width), (posY) ,  atkRange,  height/3);
						break;
					
					case UP:
						break;
					default:
						break;
				}
				if (t!=this && t.id==ObjectId.PLAYER && ((Player)t).Intersect(h)) 
				{
					Player p= (Player)t;
					p.getDamage(baseAttack);
					
				}
			}
			attacking=false;
		}
			
	}
	@Override
	public void toggleAttack(boolean b) {
		if(attacking==false&&b) {
			attacking=true;
			attackTimer=atkSpeed;
		}
			
	}
	public HitBox getHitBox() {
		return h;
	}
	@Override
	public void getDamage(float dmg) {
		// gestisci staggering
		//damage+=dmg;
		life-=dmg;
		System.out.println(life);
	}
	public State getState()
	{
		if(attacking)
		{
			//System.out.println("Attacking");
			if(facing == Direction.LEFT)
				return State.ATTACKINGBACK;
			else if(facing == Direction.RIGHT)
				return State.ATTACKINGFORWARD;
		}
		else if(isMovingRight())
			return State.FORWARD;
		else if (isMovingLeft())
			return State.BACK;
		else if (falling&& velY<0)
		{
			if(facing == Direction.LEFT)
				return State.FALLINGBACK;
			else if(facing == Direction.RIGHT)
				return State.FALLINGFORWARD;
		}
		else if (crouching)
		{
			if(facing == Direction.LEFT)
				return State.CROUCHINGBACK;
			else if(facing == Direction.RIGHT)
				return State.CROUCHINGFORWARD;
		}
		else if (jumping)
		{
			if(facing == Direction.LEFT)
				return State.JUMPINGBACK;
			else if(facing == Direction.RIGHT)
				return State.JUMPINGFORWARD;
		}
		else if(dir==Direction.REST)
		{
			if(facing == Direction.LEFT)
				return State.STEADYBACK;
			else if(facing == Direction.RIGHT)
				return State.STEADYFORWARD;
		}
		
		return State.NULL;
	}
	public boolean isMovingLeft() {
		if(dir==Direction.LEFT)
			return true;
		return false;
	}
	public boolean isMovingRight() {
		if(dir==Direction.RIGHT)
			return true;
		return false;	
	
	}
	
	@Override
	public void toggleCrouch(boolean b) {
		crouching=b;
		
	}
	public boolean isCrouching() {
		return crouching;
	}
	
	public void setName(String Name) {this.Name = Name;}
	public String getName() {return this.Name;}
	
	public float getBaseAttack() {
		return baseAttack;
	}
	public void setBaseAttack(float baseAttack) {
		this.baseAttack = baseAttack;
	}
	public float getStandHeight() {
		return standHeight;
	}
	public void setStandHeight(float standHeight) {
		this.standHeight = standHeight;
	}
	public float getAtkSpeed() {
		return atkSpeed;
	}
	public void setAtkSpeed(float atkSpeed) {
		this.atkSpeed = atkSpeed;
	}
	public float getAtkRange() {
		return atkRange;
	}
	public void setAtkRange(float atkRange) {
		this.atkRange = atkRange;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
}