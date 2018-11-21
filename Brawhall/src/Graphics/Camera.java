package Graphics;

import java.util.LinkedList;

import Objects.Anchor;
import Objects.GameObject;
import Objects.ObjectId;
import Objects.Player;
import World.World;
import gameManager.Time;

public class Camera  {

	

	GameObject holder;
	LinkedList<GameObject> anchors;
	
	float posX,posY,viewH=300,viewW=300,minH=300,maxH=400,minW=300,maxW=400;
	
	boolean free=false;

	
	
	
	public boolean isFree() {
		return free;
	}
	public void setFree(boolean free) {
		this.free = free;
	}
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
		minH=viewH-10;
		maxH=viewH+20;
		
	}
	public float getWidth() {
		return viewW;
	}
	public void setViewW(float viewW) {
		this.viewW = viewW;
		minW=viewW-10;
		maxW=viewW+50;
	}
	public Camera(GameObject w) {		
		holder=w;
		anchors=new LinkedList<GameObject>();
		maxW=holder.getWidth();
		minW=maxW-holder.getWidth()/3;

	}
	public void tick() {
		if(!free) {
			repositon();
			rescale();
			if (this.posX-viewW/2<0) 
				posX=viewW/2;
			
			if(this.posX+viewW/2>holder.getWidth() )
			 posX=holder.getWidth()-viewW/2;
			
			if (this.posY-viewH/2<0) 
				posY=viewH/2;
			
			if(this.posY+viewH/2>holder.getHeight() )
			 posY=holder.getHeight()-viewH/2;
		}
	}
	public void center() {
		posX=holder.getWidth()/2;
		posY=holder.getHeight()/2;
		
	}
	
 private void repositon() {
	 float sumX=0,sumY=0;
		for (int i=0;i<anchors.size();i++) {
			sumX+=anchors.get(i).getPosX();
			sumY+=anchors.get(i).getPosY();
		}
		posX=sumX/anchors.size();
		posY=sumY/anchors.size();
 }
 private void rescale() {
	 float Xmax=0,Ymax=0;
	 float Xmin=holder.getWidth(),Ymin=holder.getHeight();
		for (int i=0;i<anchors.size();i++) {
			GameObject o=anchors.get(i);
			if(o.getPosX()<Xmin)
				Xmin=o.getPosX();
			if(o.getPosX()+o.getWidth()>Xmax)
				Xmax=o.getPosX()+o.getWidth();
			if(o.getPosY()<Ymin)
				Ymin=o.getPosY();
			if(o.getPosY()+o.getHeight()>Ymax)
				Ymax=o.getPosY()+o.getHeight();
		}
		float nWidth = minW+Xmax-Xmin;
		viewW= (nWidth<maxW)? nWidth: maxW;
		viewH=viewW/16*9;


		
 }
public void addAnchor(GameObject p) {
	anchors.add(p);
}

}