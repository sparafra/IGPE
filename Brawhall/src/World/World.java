package World;

import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONObject;

import Objects.GameObject;
import Objects.Player;

public class World {
	
	int width,height;
	
	LinkedList<GameObject> objects;
	Player player;
	Player player2;
	String playerName="Zombie";
	String player2Name="Zombie";
	
	public JSONObject getJSON() {
		JSONObject j=new JSONObject();
		j.put("width", width);
		j.put("height", height);
		j.put("playerName", playerName);
		j.put("player2Name", player2Name);
	
		JSONArray players=new JSONArray();
		players.put(player.getJSON());
		players.put(player2.getJSON());
		
		j.put("players",players);
		return j;
	}
	public void sync(JSONObject j) {
		width=j.getInt("width");
		height=j.getInt("height");
		playerName=j.getString("playerName");
		player2Name =j.getString("player2Name");
		JSONArray ja= j.getJSONArray("players");
		player.sync( ja.getJSONObject(0));
		player2.sync(ja.getJSONObject(1));
		
	}
	
 	public Player getPlayer(int n) {
		if (n==1)
			return player;
		else
			return player2;
	}

	public void setPlayer(Player p,int n) {
		if (n==1)
		player=p;
		else
			player2=p;
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
		player=new Player(0,0);
		player2=new Player(0,0);
		height=h;
	}
	public World(int w, int h) {
		objects=new LinkedList<GameObject>();
		player=new Player(0,0);
		player2=new Player(0,0);
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

	public void PlayerJump(int i) {
		if (i==1) {
			player.jump();
		}
		else if(i==2) {
			player2.jump();
		}
	}
	
	
}
