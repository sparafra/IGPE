package Objects;

import java.util.LinkedList;

import org.json.JSONObject;

import interfaces.CanMove;
import interfaces.Direction;
	


public abstract class DynamicGameObject extends GameObject implements CanMove  {
		
	float velX=0.0f,velY=0.0f;
	float moveSpeed=0.2f;
	float maxMoveSpeed=2.0f;
	Direction dir=Direction.REST;
	
	public DynamicGameObject(float x,float y,ObjectId id) {
		super(x,y,id);
	}

	public DynamicGameObject(float x,float y,float vx,float vy,ObjectId id) {
		super(x,y,id);
		
	}
	public abstract void tick(LinkedList<GameObject> objs,double delta); 	
	public void move() {
		posX+=velX;
		posY+=velY;	
	}
	@Override
	public void ChangeDirection(Direction d) {
		dir=d;
		
	}
	@Override
	public JSONObject getJSON() {
		JSONObject j=super.getJSON();
		j.put("velX", velX);
		j.put("velY", velY);
		j.put("moveSpeed", moveSpeed);
		j.put("maxMoveSpeed", maxMoveSpeed);
		j.put("dir", dir.name());
		return j;
	}

	public void sync(JSONObject o) {
		super.sync(o);
		velX=o.getFloat("velX");
		velY=o.getFloat("velY");
		moveSpeed=o.getFloat("moveSpeed");
		maxMoveSpeed=o.getFloat("maxMoveSpeed");
		dir=Direction.valueOf(o.getString("dir"));
				
		
	}
}
