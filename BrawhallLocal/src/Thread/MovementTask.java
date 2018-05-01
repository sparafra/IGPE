package Thread;

import java.util.LinkedList;
import java.util.TimerTask;

import Graphics.myPanel;
import object.Character;
import object.GameObject;

public class MovementTask extends TimerTask {

	GameObject obj;
	myPanel panel;
	LinkedList<GameObject> list;
	String Direction;
	public MovementTask(GameObject obj, myPanel panel, String Direction)
	{
		this.obj = obj;
		this.panel = panel;
		this.Direction = Direction;
		list = new LinkedList<GameObject>();
		list.add(obj);
	}
	public void run()
	{
		((Character) list.get(0)).Move(Direction);
	}
}
