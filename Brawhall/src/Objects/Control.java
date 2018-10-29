package Objects;

import java.util.LinkedList;

import gameManager.Action;
import interfaces.Clickable;
import interfaces.Drawable;

public class Control extends GameObject implements Clickable, Drawable {
	
	Action act; 
	Boolean selected = false;
	
	public Boolean isSelected() {
		return selected;
	}
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}
	
	public Control(int width, int height,Action a) 
	{
		super(width, height, ObjectId.BUTTON);
		this.width = width;
		this.height = height; 
		this.act =a;
	}
	public Control(int x, int y, int Width, int Height , Action a) 
	{
		super(x, y, Width, Height, ObjectId.BUTTON);
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