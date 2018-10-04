package gameManager;

import java.awt.event.KeyEvent;

public enum Action {
	
	PLAYER_JUMP(KeyEvent.VK_W),
	PLAYER_ATTACK(KeyEvent.VK_F),
	PLAYER_MOVE_RIGHT(KeyEvent.VK_D),
	PLAYER_MOVE_LEFT(KeyEvent.VK_A),
	PLAYER_CROUCH(KeyEvent.VK_S),
	PLAYER_MOVE_REST(-1),
	PLAYER_STAND(-1),
	
	PLAYER2_JUMP(KeyEvent.VK_UP),
	PLAYER2_ATTACK(KeyEvent.VK_M),
	PLAYER2_MOVE_RIGHT(KeyEvent.VK_RIGHT),
	PLAYER2_MOVE_LEFT(KeyEvent.VK_LEFT),
	PLAYER2_CROUCH(KeyEvent.VK_DOWN),
	PLAYER2_MOVE_REST(-1),
	PLAYER2_STAND(-1),
	
	SELECT_MENU(KeyEvent.VK_ENTER),
	START_MULTIPLAYER_GAME(-1), 
	OPEN_SETTING(-1), 
	START_TRAINING(-1),
	CLOSE_GAME(-1),
	START_GAME(-1),
	PAUSE(KeyEvent.VK_ESCAPE),
	RESUME(-1);
	
	int key;
	
	Action(int k){
		key=k;
	}
	public int getBinded() {
		return key;
		
	}

}
