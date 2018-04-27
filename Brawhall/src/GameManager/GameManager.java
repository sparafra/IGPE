package GameManager;

import java.awt.Image;
import java.awt.Menu;
import java.awt.Toolkit;

import Graphics.MenuPanel;
import Windows.MenuGame;

public class GameManager {

	Toolkit tk = Toolkit.getDefaultToolkit();
	Image BackgroundMenu;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GameManager GM = new GameManager();
		
	}
	public GameManager()
	{
		Toolkit tk = Toolkit.getDefaultToolkit();
		BackgroundMenu = tk.getImage("C:\\Users\\spara\\eclipse-workspace\\Brawhall\\src\\Images\\Background\\BackgroundMenu.jpg");
		MenuGame M = new MenuGame(BackgroundMenu);
		
		M.render();
		
		
	}
}
