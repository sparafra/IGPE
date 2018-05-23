package object;

import java.awt.Image;
import java.util.LinkedList;

import gameManager.Action;

public class ImageViewer extends Control {

	LinkedList<Image> list;
	int selectedIndex;
	public ImageViewer(int width, int height, LinkedList<Image> list) {
		super(width, height, null);
		this.list = list;
		selectedIndex = 0;
		// TODO Auto-generated constructor stub
	}
	
	public void Next()
	{
		
	}
	public void Prev()
	{
		
	}

}
