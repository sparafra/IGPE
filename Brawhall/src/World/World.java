package World;

import java.util.ArrayList;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONObject;

import Objects.GameObject;
import Objects.Player;
import gameManager.Action;
import gameManager.EventHandler;

public class World {
	
	int width,height;
	
	LinkedList<GameObject> objects;
	EventHandler ev;
	
	ArrayList<Player> players;
	
	
	public JSONObject getJSON() {
		JSONObject j=new JSONObject();
		j.put("width", width);
		j.put("height", height);
		
	
		JSONArray pl=new JSONArray();
		
		pl.put(this.players.get(0).getJSON());
		pl.put(this.players.get(1).getJSON());
		j.put("players",pl);
		return j;
	}
	public void sync(JSONObject j) {
		width=j.getInt("width");
		height=j.getInt("height");
		
		JSONArray ja= j.getJSONArray("players");
		players.get(0).sync( ja.getJSONObject(0));
		players.get(1).sync(ja.getJSONObject(1));
		
	}
	
 	public Player getPlayer(int n) {
		return players.get(n-1);
	}

	public void setPlayer(Player p,int n) {
		players.set(n-1, p);
	}
	
	
	

	public String getPlayerName(int i) {
		return players.get(i-1).getName();
	}

	public void setPlayerName(int i,String n) {
		this.players.get(i-1).setName(n);
	}



	
	public World(int w, int h, EventHandler e) {
		objects=new LinkedList<GameObject>();
		players=new ArrayList<Player>();
		players.add(new Player(0,0));
		players.add(new Player(0,0));
		width=w;
		height=h;
		ev=e;
	}
	
	
	
	public int getWidth() {	return width;}

	public void setWidth(int width) {this.width = width;}

	public int getHeight() {return height;}

	public void setHeight(int height) {this.height = height;}




public void Update(double delta) {
		for (int i=0;i<players.size();i++){
			Player p=players.get(i);
			if(p.isDead()&&p.getlives()==0) {
				ev.performAction(Action.GAMEOVER);
			}
		}
		for (int i=0;i<objects.size();i++) {
			objects.get(i).tick(objects,delta);
		}
	}

	public void addObject(GameObject o) {
		objects.add(o);
	}

	
	
	
}
