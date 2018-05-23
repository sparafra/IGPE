package object;

import java.awt.Image;
import java.util.LinkedList;

import gameManager.Action;

public class PlayerPreview extends Control {

	LinkedList<String> list;
	int selectedIndex;
	
	public PlayerPreview(int width, int height, LinkedList<String> PlayersName) {
		super(width, height, null);
		list = PlayersName;
		selectedIndex = 0;
		// TODO Auto-generated constructor stub
	}
	public void Next()
	{
		if(selectedIndex < list.size()-1)
		{
			selectedIndex++;	
		}
	}
	public void Prev()
	{
		if(selectedIndex > 0)
		{
			selectedIndex--;	
		}
	}
	
	public String getSelectedPlayer() {return list.get(selectedIndex);}
}
