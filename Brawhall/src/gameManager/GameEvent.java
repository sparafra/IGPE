package gameManager;

public class GameEvent {
	public enum EventType{
		MOUSE_RIGHT_CLICK_DOWN,
		MOUSE_RIGHT_CLICK_UP,
		MOUSE_LEFT_CLICK_DOWN,
		MOUSE_LEFT_CLICK_UP,
	}

	EventType type;
	public GameEvent(EventType e) {
		type=e;
	}

}
