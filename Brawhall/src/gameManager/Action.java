package gameManager;

import java.awt.event.KeyEvent;

public enum Action {
	
	PLAYER_JUMP(KeyEvent.VK_SPACE),
	PLAYER_MOVE_RIGHT(KeyEvent.VK_D),
	PLAYER_MOVE_LEFT(KeyEvent.VK_A),
	PLAYER_CROUCH(KeyEvent.VK_S),
	SELECT_MENU(KeyEvent.VK_ENTER),
	PLAYER_MOVE_REST(-1),
	PLAYER_STAND(-1),
	START_GAME(-1);
	
	int key;
	
	Action(int k){
		key=k;
	}
	public int getBinded() {
		return key;
		
	}

}
