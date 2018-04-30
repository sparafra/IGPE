package world;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Camera extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	World world;
	
	int posX,posY,viewH,viewW;
	
	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getViewH() {
		return viewH;
	}

	public void setViewH(int viewH) {
		this.viewH = viewH;
	}

	public int getViewW() {
		return viewW;
	}

	public void setViewW(int viewW) {
		this.viewW = viewW;
	}

	public Camera(World w) {
		

		
			Dimension d= new Dimension(viewH,viewW);
			this.setSize(d);
			this.setMaximumSize(d);
			this.setMinimumSize(d);
			
	
		
		
		world=w;

	}
	public void paint(Graphics g) {
		g.drawRect(-25, 200, 50, 50);
	}
	
	public void render() {
		super.repaint();
	}

}
