package object;

import interfaces.CanFight;
import interfaces.CanJump;
import interfaces.CanMove;
import interfaces.Collides;
import interfaces.Drawable;
import interfaces.GravityDependent;

public class Player extends DynamicGameObject implements Collides, CanFight, CanJump, Drawable, GravityDependent, CanMove{

	float damage;
	float baseAttack;
	float moveSpeed;
	float atkSpeed;
	float atkRange;
	float weight;
	
	
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
	
	
}
