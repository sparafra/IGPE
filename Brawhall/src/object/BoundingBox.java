package object;

import java.awt.Rectangle;

public class BoundingBox extends Rectangle {

	public enum Side{
		Top,Bottom,Left,Right;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BoundingBox(int x, int y, int height, int width) {
		super(x, y, height, width);
		// TODO Auto-generated constructor stub
	}

}
