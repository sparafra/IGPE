package Objects;

import java.util.LinkedList;

public class PlayerPreview extends Control {

	LinkedList<String> list;
	int selectedIndex;
	boolean active;
	public PlayerPreview(int width, int height, LinkedList<String> PlayersName, boolean active) {
		super(width, height, null);
		list = PlayersName;
		selectedIndex = 0;
		this.active = active;
		// TODO Auto-generated constructor stub
	}
	public void Next()
	{
		if(selectedIndex < list.size()-1)
		{
			selectedIndex++;	
		}
		System.out.println(selectedIndex);
	}
	public void Prev()
	{
		if(selectedIndex > 0)
		{
			selectedIndex--;	
		}
		System.out.println(selectedIndex);

	}
	
	public String getSelectedPlayer() {return list.get(selectedIndex);}
	
	public void setActive(boolean active) {this.active = active;}
	public boolean getActive() {return active;}
}
