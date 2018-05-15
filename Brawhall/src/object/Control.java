package object;

import java.util.LinkedList;

public abstract class Control extends StaticGameObject {

	public Control(float x,float y,ObjectId id) {
		super(x,y,id);
		
	}
	public Control(float x,float y,float w,float h,ObjectId id) {
		super(x,y,w,h,id);
		
	}
	@Override
	public void tick(LinkedList<GameObject> objects, double delta) {
		// TODO Auto-generated method stub
		
	}

}
