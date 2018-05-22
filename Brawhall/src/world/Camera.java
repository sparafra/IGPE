package world;

import object.GameObject;

public class Camera  {

	

	World world;
	GameObject anchor;
	
	float posX,posY,viewH=300,viewW=300;
	


	
	
	
	public float getPosX() {
		return posX-getWidth()/2;
	}
	public void setPosX(float posX) {
		this.posX = posX;
	}
	public float getPosY() {
		return posY-getHeight()/2;
	}
	public void setPosY(float posY) {
		this.posY = posY;
	}
	public float getHeight() {
		return viewH;
	}
	public void setViewH(float viewH) {
		this.viewH = viewH;
	}
	public float getWidth() {
		return viewW;
	}
	public void setViewW(float viewW) {
		this.viewW = viewW;
	}
	public Camera(World w,GameObject a) {		
		world=w;
		anchor=a;

	}
	public void tick() {
		posX=anchor.getPosX()+anchor.getWidth();
		posY=anchor.getPosY()+anchor.getHeight();
		if (this.posX-viewW/2<0) 
			posX=viewW/2;
		
		if(this.posX+viewW/2>world.width )
		 posX=world.width-viewW/2;
		
		if (this.posY-viewH/2<0) 
			posY=viewH/2;
		
		if(this.posY+viewH/2>world.height )
		 posY=world.height-viewH/2;
		
	}
	

}
