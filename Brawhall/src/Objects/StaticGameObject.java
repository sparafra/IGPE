package Objects;

public abstract class StaticGameObject extends GameObject {

	public StaticGameObject(float x,float y,ObjectId id) {
		super(x,y,id);
		
	}
	public StaticGameObject(float x,float y,float w,float h,ObjectId id) {
		super(x,y,w,h,id);
		
	}
}


