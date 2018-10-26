package Network;

import org.json.JSONException;

import gameManager.Action;

public class ActionMessage extends Message {

	
	public ActionMessage(Action a) throws JSONException {
		put("type","action");
		put("content",a.name());
	}
	public ActionMessage(String string) {
		super(string);
	}
	public Action getAction() throws JSONException {
		return Action.valueOf(this.getString("content"));	
	}

}
