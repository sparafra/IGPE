package object;

import java.awt.geom.Rectangle2D;;
public class BoundingBox extends Rectangle2D.Float{

	public enum Side{
		Top,Bottom,Left,Right;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BoundingBox(float x, float y, float height, float width) {
		super(x, y, height, width);
		// TODO Auto-generated constructor stub
	}

}
