package object;

import interfaces.CanFight;
import interfaces.CanJump;
import interfaces.CanMove;
import interfaces.Collides;
import interfaces.Drawable;
import interfaces.GravityDependent;

public class Character extends GameObject implements Collides, CanFight, CanJump, Drawable, GravityDependent, CanMove{

	float damage;
	float baseAttack;
	float moveSpeed;
	float atkSpeed;
	float atkRange;
	float weight;
	
	public Character() {}
	public Character(int Height, int Width) 
	{
		super();
		this.Height = Height;
		this.Width = Width;
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
