package interfaces;

import java.util.LinkedList;

import Objects.GameObject;

public interface CanFight {

	public void attack(LinkedList<GameObject> list,double delta);
	public void toggleAttack(boolean b);
	public void getDamage(float dmg);
}
