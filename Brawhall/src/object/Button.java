package object;

import java.util.LinkedList;

public class Button extends Control {

	public Button(float x,float y ) 
	{
		super(x, y, ObjectId.BUTTON);
		
	}
	
	public Button(float x,float y ,int Height, int Width) 
	{
		super(x, y,Width,Height, ObjectId.BUTTON);
		
	}

//	@Override
//	public void tick(LinkedList<GameObject> objects) {
//		// TODO Auto-generated method stub
//		
//	}

	@Override
	public void tick(LinkedList<GameObject> objects, double delta) {
		// TODO Auto-generated method stub
		
	}

	

}
