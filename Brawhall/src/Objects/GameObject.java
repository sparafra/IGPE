package Objects;

import java.util.LinkedList;

import org.json.JSONObject;

import Objects.BoundingBox.Side;
import interfaces.Direction;

public abstract class GameObject {

	float posX,posY;
	float height=1,width=1;
	ObjectId id;
	
	
	
	
	public GameObject(float x,float y,ObjectId id) {
		posX=x;
		posY=y;
		this.id=id;
		
	}
	public GameObject(float x,float y,float width,float height,ObjectId id) {
		posX=x;
		posY=y;
		this.width=width;
		this.height=height;
		this.id=id;
		
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	public float getWidth() {
		return width;
	}
	public void setWidth(float width) {
		this.width = width;
	}
	public float getPosX() {
		return posX;
	}
	public void setPosX(float posX) {
		this.posX = posX;
	}
	public float getPosY() {
		return posY;
	}
	public void setPosY(float posY) {
		this.posY = posY;
	}
	public BoundingBox getBounds(Side s) {
		BoundingBox b=null;
		
		b=new BoundingBox(this,posX,posY,width,height);	
		return b;
	}
	public JSONObject getJSON() {
		JSONObject j=new JSONObject();
		j.put("posX", posX);
		j.put("posY", posY);
		j.put("height", height);
		j.put("width", width);
		j.put("id", id.name());
		return j;
	}
	public abstract void tick(LinkedList<GameObject> objects, double delta) ;
	public void sync(JSONObject o) {
		posX=o.getFloat("posX");
		posY=o.getFloat("posY");
		height=o.getFloat("height");
		width=o.getFloat("width");
		width=o.getFloat("width");
		id=ObjectId.valueOf(o.getString("id"));
	}
}
