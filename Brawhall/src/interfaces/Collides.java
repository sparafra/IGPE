package interfaces;

import java.util.LinkedList;

import Objects.BoundingBox;
import Objects.GameObject;

public interface Collides {

	public BoundingBox getBounds(BoundingBox.Side s);
	public void Collision(LinkedList<GameObject> g);
	
}
