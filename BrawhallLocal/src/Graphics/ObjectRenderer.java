package Graphics;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JPanel;

import GameManager.GameManager;
import World.World;
import object.GameObject;

public class ObjectRenderer {
	
	World w;
	myPanel panel;
	public ObjectRenderer(World w, myPanel panel)
	{
		this.w = w;
		this.panel = panel;
	}
	/*
	public ObjectRenderer(GameObject Object, ArrayList<Image>setImages, JPanel p)
	{
		this.Object = Object;
		this.setImages = setImages;
		this.p = p;
		
	}
	*/
	public void Render(Graphics g, GameObject Object, LinkedList<Image>setImages)
	{
		
	}
	public void Render(Graphics g, GameObject Object, Image Image)
	{
		//Graphics2D g2d = (Graphics2D) g.create();
		//g.fillRect(ConvertX(Object.getX()), ConvertY(Object.getY()), ConvertX(Object.getWidth()), ConvertY(Object.getHeight()));

		g.drawImage(Image, ConvertX(Object.getX()), ConvertY(Object.getY()), ConvertX(Object.getWidth()), ConvertY(Object.getHeight()), panel);

	}
	public int ConvertX(float wx) {
		return (int) ((wx*panel.getWidth())/w.getWidth()) ;
		
	}
	public int ConvertY(float wy) {
		return (int) ((wy*panel.getHeight())/w.getHeight()) ;
		
	}
}
