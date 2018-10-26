package Network;

import org.json.JSONException;



public class SyncMessage extends Message {

	public SyncMessage() throws JSONException {
		// TODO Auto-generated constructor stub
	}

	public SyncMessage(String s) throws JSONException {
		super(s);
		put("type","sync");
	}
	
}
