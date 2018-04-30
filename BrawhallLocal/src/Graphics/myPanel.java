package Graphics;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JPanel;

import GameManager.GameManager;
import World.World;
import object.Block;
import object.Control;
import object.GameObject;
import object.Character;

public class myPanel extends JPanel{

	LinkedList<GameObject> objectList;
	ObjectRenderer objRender;
	World w;
	HashMap<String, HashMap<String, LinkedList<Image>>> Media;
	
	public myPanel (World w, HashMap<String, HashMap<String, LinkedList<Image>>> Media)
	{
		super();
		this.Media = Media;
		this.w = w;
		objectList = new LinkedList<GameObject>();
		objRender = new ObjectRenderer(w, this);
		initGUI();
		
	}
	public void paint(Graphics g)
	{
		super.paint(g);
		//Graphics2D g2d = (Graphics2D) g.create();		

		g.drawImage(Media.get("Background").get("Sky").get(0), 0, 0, this);
		
		for(int k=0; k<objectList.size(); k++)
		{
			if(objectList.get(k) instanceof Control)
				objRender.Render(g, objectList.get(k), Media.get("Control").get("Start").get(0));
			if(objectList.get(k) instanceof Block)
				objRender.Render(g, objectList.get(k), Media.get("Block").get("Standard").get(0));
			if(objectList.get(k) instanceof Character)
				objRender.Render(g, objectList.get(k), Media.get("Character").get("IronMan").get(((Character)objectList.get(k)).getState()));
			
		}
		
	}
	public void initGUI()
	{
		Toolkit tk = Toolkit.getDefaultToolkit();
		/*
		Start = tk.getImage("C:\\Users\\spara\\eclipse-workspace\\BrawhallLocal\\src\\Images\\Buttons\\Start.png");
		Setting = tk.getImage("C:\\Users\\spara\\eclipse-workspace\\BrawhallLocal\\src\\Images\\Buttons\\Setting.png");
		Exit = tk.getImage("C:\\Users\\spara\\eclipse-workspace\\BrawhallLocal\\src\\Images\\Buttons\\Exit.png");
		*/
		Dimension d = tk.getScreenSize();
		//this.setSize(1440, 900);
		this.setSize(d);
	}
	
	public void rendergame(LinkedList<GameObject> objectList) 
	{
		this.objectList = objectList;
		super.repaint();
	}
	public void rendermenu(LinkedList<GameObject> objectList) 
	{
		this.objectList = objectList;
		super.repaint();
	}
}
