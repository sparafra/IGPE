package interfaces;

import object.BoundingBox;

public interface Collides {

	public BoundingBox getBounds(BoundingBox.Side s);
	
}
