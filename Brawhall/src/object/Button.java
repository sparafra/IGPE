package object;

import java.util.LinkedList;

import gameManager.Action;
import interfaces.Clickable;
import interfaces.Drawable;

public class Button extends Control implements Clickable, Drawable {
	
	Action act; 
	boolean selected = false;
	
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public Button(int width, int height,Action a) 
	{
		super(width, height,a);
		this.id= ObjectId.BUTTON;
		this.width = width;
		this.height = height; 
		this.act =a;
	}
	public Button(int x, int y, int Width, int Height , Action a) 
	{
		super(x, y, Width, Height, a);
		this.id= ObjectId.BUTTON;
		this.posX = x;
		this.posY = y;
		this.height = Height;
		this.width = Width; 
		this.act = a;
	}
	
	public void tick(LinkedList<GameObject> objects, double delta) {
		
	} 
	public Action getAction() {return this.act;} 
	 
	public void setAction(Action a){this.act=a;}
	
}   