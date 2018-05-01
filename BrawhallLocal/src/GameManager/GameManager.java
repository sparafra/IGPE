package GameManager;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JPanel;

//import Graphics.MenuPanel;
import Graphics.ObjectRenderer;
import Graphics.myPanel;
//import Windows.MenuGame;
import World.World;
import object.Block;
import object.Control;
import object.GameObject;
import object.Character;


public class GameManager extends Thread implements Runnable {

	EventHandler EH;
	public World w;
	public myPanel panel;
	Toolkit tk;
	public HashMap<String, HashMap<String, LinkedList<Image>>> Media;
	LinkedList<GameObject> MenuObj;
	LinkedList<GameObject> GameObj;
	boolean running=false;
	boolean menu = false;
	
	
	public void start() 
	{
		if(running )
			return;
	
		running =true;
		super.start();
	}
	
	public void run() 
	{
		
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while(running)
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1)
			{
				tick();
				updates++;
				delta--;
			}
			
			frames++;
					
			if(System.currentTimeMillis() - timer > 1000)
			{
				timer += 1000;
				System.out.println("FPS: " + frames + " TICKS: " + updates);
				frames = 0;
				updates = 0;
			}
		}	
	}

	
	public void tick() 
	{
		if (menu) 
		{ 	
			panel.rendermenu(MenuObj);
		}
		else 
		{
			panel.rendergame(GameObj);
		}
	}
	
	public GameManager()
	{
		tk = Toolkit.getDefaultToolkit();
		w = new World(50,50);
		
		
		Media = new HashMap<String, HashMap<String, LinkedList<Image>>>();
		LoadMedia();
		
		panel = new myPanel(w, Media);
		//MenuGame M = new MenuGame(BackgroundMenu);
		
		//M.render();
		MenuObj = new LinkedList<GameObject>();
		Control j= new Control(10, 10, 10,5);
		MenuObj.add(j);
		
		GameObj = new LinkedList<GameObject>();
		Block b = new Block(15, 25, 3,10);
		//Block c = new Block(0, 40, 3,10);
		Character player = new Character(20, 20, 5, 2);
		
		GameObj.add(b);
		//GameObj.add(c);
		GameObj.add(player);
		
		w.setObjList(GameObj);
		
		//EH = new EventHandler(panel, w, MenuObj);
		EH = new EventHandler(panel, w, w.getObjList());
	}
	
	public void LoadMedia()
	{
		LinkedList<Image> CharacterImagesZombie =new LinkedList<Image>();
		LinkedList<Image> CharacterImagesIronMan = new LinkedList<Image>();

		LinkedList<Image> CharacterImagesRed;
		
		LinkedList<Image> BackgroundImagesSky = new LinkedList<Image>();
		
		LinkedList<Image> ControlImagesStart = new LinkedList<Image>();
		LinkedList<Image> ControlImagesSetting = new LinkedList<Image>();
		
		LinkedList<Image> BlocksImageStandard = new LinkedList<Image>();
		
		HashMap<String, LinkedList<Image>> Character = new HashMap<String, LinkedList<Image>>();
		HashMap<String, LinkedList<Image>> Block = new HashMap<String, LinkedList<Image>>();
		HashMap<String, LinkedList<Image>> Control = new HashMap<String, LinkedList<Image>>();
		HashMap<String, LinkedList<Image>> Background = new HashMap<String, LinkedList<Image>>();

		
		ControlImagesStart.add(tk.getImage("C:\\Users\\spara\\eclipse-workspace\\BrawhallLocal\\src\\Images\\Buttons\\Start.png"));

		ControlImagesSetting.add(tk.getImage("C:\\Users\\spara\\eclipse-workspace\\BrawhallLocal\\src\\Images\\Buttons\\Setting.png"));
		
		BackgroundImagesSky.add(tk.getImage("C:\\Users\\spara\\eclipse-workspace\\BrawhallLocal\\src\\Images\\Background\\BackgroundMenu.jpg"));
		
		BlocksImageStandard.add(tk.getImage("C:\\Users\\spara\\eclipse-workspace\\BrawhallLocal\\src\\Images\\Blocks\\Standard.png"));

		CharacterImagesZombie.add(tk.getImage("C:\\Users\\spara\\eclipse-workspace\\BrawhallLocal\\src\\Images\\Characters\\Zombie.png"));
		
		CharacterImagesIronMan.add(tk.getImage("C:\\Users\\spara\\eclipse-workspace\\BrawhallLocal\\src\\Images\\Characters\\IronMan\\SteadyForward.png"));
		CharacterImagesIronMan.add(tk.getImage("C:\\Users\\spara\\eclipse-workspace\\BrawhallLocal\\src\\Images\\Characters\\IronMan\\SteadyBack.png"));
		CharacterImagesIronMan.add(tk.getImage("C:\\Users\\spara\\eclipse-workspace\\BrawhallLocal\\src\\Images\\Characters\\IronMan\\Forward.png"));
		CharacterImagesIronMan.add(tk.getImage("C:\\Users\\spara\\eclipse-workspace\\BrawhallLocal\\src\\Images\\Characters\\IronMan\\Back.png"));

		Control.put("Start", ControlImagesStart);
		Control.put("Setting", ControlImagesSetting);
		
		Background.put("Sky", BackgroundImagesSky);
		
		Block.put("Standard", BlocksImageStandard);
		
		Character.put("IronMan", CharacterImagesIronMan);
		
		Media.put("Character", Character);
		Media.put("Control", Control);
		Media.put("Background", Background);
		Media.put("Block", Block);
		
	}

	
	
	
}
