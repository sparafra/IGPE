package world;

import java.util.LinkedList;

import interfaces.Direction;
import object.GameObject;
import object.Player;

public class World {
	
	int width,height;
	
	LinkedList<GameObject> objects;
	Player player;
	
	public void setPlayer(Player p) {
		player=p;
	}
	
	public void MovePlayer(Direction d) {
		
			player.ChangeDirection(d);
	}

	public World(int w, int h,LinkedList<GameObject> l) {
		objects=l;
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
		
		for (int i=0;i<objects.size();i++) {
			objects.get(i).tick(objects);
		}
	}

	public void addObject(GameObject o) {
		objects.add(o);
	}

	public void PlayerJump() {
		player.jump();
		
	}
}
