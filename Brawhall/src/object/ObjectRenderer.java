package object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;

import gameManager.GameManager;
import object.BoundingBox.Side;
import object.State;

public class ObjectRenderer {

	GameObject obj;
	GameManager gm;
	static boolean test=false;
	static boolean drawbounds=true;
	Toolkit tk;
	public ObjectRenderer(GameObject o,GameManager g) {
		
		obj=o;
		gm=g;
		tk = Toolkit.getDefaultToolkit();;
	}
	
	public void DefaultRender(Graphics g) {
		
			if(!test) {
				if (obj instanceof Block) 
				{
					g.drawImage(gm.getMedia().getImage(ObjectId.BLOCK, State.NULL, "Standard", 0), gm.ConvertPosX(obj.posX), gm.ConvertPosY(obj.posY), gm.ConvertX(obj.width), gm.ConvertY(obj.height), null);
				}
				else if(obj instanceof Button) 
				{
					g.drawImage(gm.getMedia().getImage(ObjectId.BUTTON, State.NULL, "Start", 0), gm.ConvertPosX(obj.getPosX()), gm.ConvertPosY(obj.getPosY()), gm.ConvertX(obj.getWidth()), gm.ConvertY(obj.getHeight()), null);
				}
				else if(obj instanceof Background)
				{
					Image i=gm.getMedia().getImage(ObjectId.BACKGROUND, State.NULL, "Sky", 0);
					g.drawImage(i, gm.ConvertPosX(obj.getPosX()), gm.ConvertPosY(obj.getPosY()), gm.ConvertX(obj.getWidth()), gm.ConvertY(obj.getHeight()), null);
				}
				else
				{
					g.setColor(Color.PINK);
					g.fillRect(gm.ConvertPosX(obj.posX), gm.ConvertPosY(obj.posY), gm.ConvertX(obj.width), gm.ConvertY(obj.height));
					
				}
				
			}
			else {
				if (obj instanceof Block) 
				{
					g.setColor(Color.YELLOW);
					g.fillRect(gm.ConvertPosX(obj.posX), gm.ConvertPosY(obj.posY), gm.ConvertX(obj.width), gm.ConvertY(obj.height));
					boundsRender(g);
				}
				else if(obj instanceof Button) 
				{
					g.setColor(Color.PINK);
					g.fillRect(gm.ConvertPosX(obj.posX), gm.ConvertPosY(obj.posY), gm.ConvertX(obj.width), gm.ConvertY(obj.height));
				}
				else if(obj instanceof Background)
				{
					g.setColor(Color.cyan);
					g.fillRect(gm.ConvertPosX(obj.posX), gm.ConvertPosY(obj.posY), gm.ConvertX(obj.width), gm.ConvertY(obj.height));
					
				}
				else
				{
					g.setColor(Color.PINK);
					g.fillRect(gm.ConvertPosX(obj.posX), gm.ConvertPosY(obj.posY), gm.ConvertX(obj.width), gm.ConvertY(obj.height));
					
				}
				
			}
			if(drawbounds)
						boundsRender(g);
		
	}

	public void boundsRender(Graphics g) {
		if(drawbounds) {
		Graphics2D g2d= (Graphics2D)g;
		BoundingBox b;
		
		g2d.setColor(Color.BLACK);
		
			
			b=(obj).getBounds(Side.Top);
			g2d.draw(scale(b));
			
		
	}
		
	}
	Rectangle2D.Float scale(Rectangle2D.Float b){
		Rectangle2D.Float scaled = new Rectangle2D.Float(gm.ConvertPosX(b.x),gm.ConvertPosY(b.y),gm.ConvertX(b.width),gm.ConvertY(b.height));
		return scaled;
	}
}
