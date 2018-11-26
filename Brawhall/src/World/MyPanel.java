package World;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
//import java.awt.Toolkit;
import java.util.LinkedList;

import javax.swing.JPanel;

import gameManager.GameManager;
import Objects.ObjectRenderer;

public class MyPanel extends JPanel {

	LinkedList<ObjectRenderer> renderers;
	
	public void setRenderers(LinkedList<ObjectRenderer> renderers) {
		this.renderers = renderers;
	}
	public LinkedList<ObjectRenderer> getRenderers() {
		return renderers ;
	}
	GameManager gm;

	private static final long serialVersionUID = 1L;

	
	
	
	public MyPanel(GameManager g,int height,int width)  {
		gm=g;
		
		//Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d= new Dimension(height,width);
		this.setPreferredSize(d);
		this.setBackground(Color.BLACK);
		//Dimension FullScreen = tk.getScreenSize();
				
		this.setDoubleBuffered(true);
		this.renderers=new LinkedList<ObjectRenderer>();
		
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d= (Graphics2D)g;
		g2d.translate(-gm.getCamera().getPosX(), -gm.getCamera().getPosY());
		for(int i=0;i<renderers.size();i++) {
			
			renderers.get(i).DefaultRender(g2d);
			
		}
		g2d.translate(0,0);

	}
	

	
	public void render() {
		
		repaint();
	}
	
}
