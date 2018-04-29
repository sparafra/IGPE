package object;

import object.ObjectId;

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

}
