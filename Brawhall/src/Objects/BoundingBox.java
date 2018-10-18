package Objects;

import java.awt.geom.Rectangle2D;;
public class BoundingBox extends Rectangle2D.Float{

	public enum Side{
		Top,Bottom,Left,Right;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	GameObject holder;

	public BoundingBox(GameObject o,float x, float y, float height, float width) {
		super(x, y, height, width);
		holder=o;
		// TODO Auto-generated constructor stub
	}

	public GameObject getHolder() {
		return holder;
	}

}
