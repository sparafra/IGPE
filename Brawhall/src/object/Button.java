package object;

public class Button extends Control {

	public Button(float x,float y ) 
	{
		super(x, y, ObjectId.BUTTON);
		
	}
	
	public Button(float x,float y ,int Height, int Width) 
	{
		super(x, y,Width,Height, ObjectId.BUTTON);
		
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

}
