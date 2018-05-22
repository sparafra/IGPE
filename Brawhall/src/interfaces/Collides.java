package interfaces;

import java.util.LinkedList;

import object.BoundingBox;
import object.GameObject;

public interface Collides {

	public BoundingBox getBounds(BoundingBox.Side s);
	public void Collision(LinkedList<GameObject> g);
	
}
