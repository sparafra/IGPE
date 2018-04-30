package world;

import java.util.LinkedList;

import interfaces.Direction;
import object.GameObject;
import object.Player;

public class World {
	
	int width,height;
	
	LinkedList<GameObject> list;
	Player player;
	
	public void setPlayer(Player p) {
		player=p;
	}
	
	public void MovePlayer(Direction d) {
		
			player.ChangeDirection(d);
	}

	public World(int w, int h,LinkedList<GameObject> l) {
		list=l;
		width=w;
		height=h;
	}
	
	
	
	public int getWidth() {
		return width;
	}



	public void setWidth(int width) {
		this.width = width;
	}



	public int getHeight() {
		return height;
	}



	public void setHeight(int height) {
		this.height = height;
	}



	public void Update() {
		
		for (int i=0;i<list.size();i++) {
			list.get(i).tick();
		}
	}

	public void addObject(GameObject o) {
		list.add(o);
	}
}
