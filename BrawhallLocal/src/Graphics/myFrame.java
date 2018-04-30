package Graphics;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import GameManager.GameManager;

public class myFrame extends JFrame{

	public static void main(String[] args)
	{
		JFrame frame = new myFrame();
		frame.setVisible(true);
		
	}
	
	public myFrame()
	{
		super();
		initGUI();
		GameManager gm = new GameManager();
		this.setContentPane(gm.panel);
		gm.start();
	}
	
	public void initGUI()
	{
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		this.setSize(d);
		//this.setSize(1440, 900);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocation(0, 0);
		this.setResizable(false);
		this.setUndecorated(true);
		
	}
}
