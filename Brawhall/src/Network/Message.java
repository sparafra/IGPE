package Network;

import org.json.JSONException;
import org.json.JSONObject;

public class Message extends JSONObject {

	public Message() throws JSONException {
		this.put("type", "message");
	}

	public Message(String s) throws JSONException {
		super(s);
	}

	public void write(String s) throws JSONException {
		this.put("content", s);
	}
}
