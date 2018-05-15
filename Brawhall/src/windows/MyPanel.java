package windows;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.LinkedList;

import javax.swing.JPanel;

import gameManager.GameManager;
import object.ObjectRenderer;

public class MyPanel extends JPanel {

	LinkedList<ObjectRenderer> list;
	GameManager gm;
	int h;
	int w;
	private static final long serialVersionUID = 1L;

	public MyPanel(GameManager g,int height,int width)  {
		gm=g;
		Dimension d= new Dimension(height,width);
		this.setSize(d);
		this.setMaximumSize(d);
		this.setMinimumSize(d);
		this.setDoubleBuffered(true);
		list=new LinkedList<ObjectRenderer>();
		
	}
	
	public void paint(Graphics g) {
		
		super.paint(g);
		
	for(int i=0;i<list.size();i++) {
			list.get(i).DefaultRender(g);
			
			
		}

	}
	
	
	public void render(LinkedList<ObjectRenderer> l) {
		list=l;
		super.repaint();
	}
	
	
}
