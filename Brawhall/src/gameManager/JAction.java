package gameManager;

import org.json.JSONException;
import org.json.JSONObject;



public class JAction extends JSONObject {

	public JAction(Action a) throws JSONException {
		put("type","action");
		put("content",a.name());
		put("key",a.getBinded());
		if(a==Action.PLAYER_CHOOSED_MULTIPLAYER)
			put("playerName","Zombie1");
	}
	public JAction(String s) throws JSONException {
		super(s);
	}
	public Action getAction() throws JSONException{
		return Action.valueOf(getString("content"));
	
	}
	
}
