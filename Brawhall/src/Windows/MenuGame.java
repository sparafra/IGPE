package Windows;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Graphics.MenuPanel;
import Graphics.myFrame;
import object.Control;

public class MenuGame {
	Toolkit tk = Toolkit.getDefaultToolkit();
	
	float Width, Height;
	Image Play;
	Image Setting;
	Image Exit;
	Image Background;
	ArrayList<Control> Controls = new ArrayList<Control>();
	
	public static void main(String[] args) {
		
	}

	public MenuGame(Image Background)
	{		
		this.Background = Background;
		Control Start = new Control();
		Control Setting = new Control();
		Control Exit = new Control();
		
	}
	
	public void render()
	{
		JFrame frame = new myFrame();
		frame.setVisible(true);
		
		JPanel panel = new MenuPanel(Background);
		
		frame.add(panel);
	}
	
	public float getWidth() {return Width;}
	public float getHeight() {return Height;}

}
