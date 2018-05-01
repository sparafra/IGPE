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
	
	int State; //0=SteadyBack, 1=SteadyForward, 2=Forward, 3=Back 
	
	public Character() {}
	public Character(int x, int y, int Height, int Width) 
	{
		super();
		this.x = x;
		this.y = y;
		this.Height = Height;
		this.Width = Width;
		State=0;
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
	public int getState() {return State;}
	
	public void Move(String Direction)
	{
		if(Direction == "Forward")
		{
			this.x += 0.2;
			State = 2;
		}
		else if (Direction == "SteadyForward")
		{
			State = 0;
		}
		else if (Direction == "Back")
		{
			this.x -= 0.2;
			State = 3;
		}
		else if (Direction == "SteadyBack")
		{
			State = 1;
		}
	}
}
