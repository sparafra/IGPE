package World;

import java.util.ArrayList;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONObject;

import Objects.GameObject;
import Objects.Player;

public class World {
	
	int width,height;
	
	LinkedList<GameObject> objects;
	
	ArrayList<Player> players;
	String playerName="Zombie";
	String player2Name="Zombie";
	
	public JSONObject getJSON() {
		JSONObject j=new JSONObject();
		j.put("width", width);
		j.put("height", height);
		j.put("playerName", playerName);
		j.put("player2Name", player2Name);
	
		JSONArray pl=new JSONArray();
		
		pl.put(this.players.get(0).getJSON());
		pl.put(this.players.get(1).getJSON());
		j.put("players",pl);
		return j;
	}
	public void sync(JSONObject j) {
		width=j.getInt("width");
		height=j.getInt("height");
		playerName=j.getString("playerName");
		player2Name =j.getString("player2Name");
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
	
	
	

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getPlayer2Name() {
		return player2Name;
	}

	public void setPlayer2Name(String player2Name) {
		this.player2Name = player2Name;
	}

	public World(int w, int h,LinkedList<GameObject> l) {
		objects=l;
		width=w;
		players=new ArrayList<Player>();
		players.add(new Player(0,0));
		players.add(new Player(0,0));
		height=h;
	}
	public World(int w, int h) {
		objects=new LinkedList<GameObject>();
		players=new ArrayList<Player>();
		players.add(new Player(0,0));
		players.add(new Player(0,0));
		width=w;
		height=h;
	}
	
	
	
	public int getWidth() {	return width;}

	public void setWidth(int width) {this.width = width;}

	public int getHeight() {return height;}

	public void setHeight(int height) {this.height = height;}



	/*public void Update() {
		
		for (int i=0;i<objects.size();i++) {
			objects.get(i).tick(objects);
		}
	}*/
	
public void Update(double delta) {
		
		for (int i=0;i<objects.size();i++) {
			objects.get(i).tick(objects,delta);
		}
	}

	public void addObject(GameObject o) {
		objects.add(o);
	}

	
	
	
}
