package Objects;

import java.util.LinkedList;

public class Background extends GameObject{
	String State;
	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}

	public Background(float w, float h) {
		super(0, 0, w, h, ObjectId.BACKGROUND);
		State = "Null";
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick(LinkedList<GameObject> objects, double delta) {
		// TODO Auto-generated method stub
		
	}
	

}
