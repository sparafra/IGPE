package windows;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.LinkedList;

import javax.swing.JPanel;

import gameManager.GameManager;
import object.ObjectRenderer;

public class MyPanel extends JPanel {

	LinkedList<ObjectRenderer> renderers;
	public void setRenderers(LinkedList<ObjectRenderer> renderers) {
		this.renderers = renderers;
	}
	GameManager gm;
	int h;
	int w;
	private static final long serialVersionUID = 1L;

	public MyPanel(GameManager g,int height,int width)  {
		gm=g;
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d= new Dimension(height,width);
		Dimension FullScreen = tk.getScreenSize();
		this.setSize(FullScreen);
		this.setMaximumSize(d);
		this.setMinimumSize(d);
		this.setDoubleBuffered(true);
		
		renderers=new LinkedList<ObjectRenderer>();
		
	}
	public void paint(Graphics g) {
		super.paint(g);
		for(int i=0;i<renderers.size();i++) {
			renderers.get(i).DefaultRender(g);
			
		}

	}
	/*public void render(LinkedList<ObjectRenderer> l) {
		list=l;
		repaint();
	}*/
	public void render() {
		
		repaint();
	}
}
