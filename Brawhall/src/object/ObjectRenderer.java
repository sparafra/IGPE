package object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;

import gameManager.GameManager;
import object.BoundingBox.Side;

public class ObjectRenderer {

	GameObject obj;
	GameManager gm;
	boolean drawbounds=true;
	public ObjectRenderer(GameObject o,GameManager g) {
		
		obj=o;
		gm=g;
	}
	
	public void DefaultRender(Graphics g) {
		
		if (obj.isUpdated()){
		if (obj instanceof Block) {
			g.setColor(Color.CYAN);
			g.fillRect(gm.ConvertPosX(obj.posX), gm.ConvertPosY(obj.posY), gm.ConvertX(obj.width), gm.ConvertY(obj.height));
			g.setColor(Color.BLACK);
			g.drawRect(gm.ConvertPosX(obj.posX), gm.ConvertPosY(obj.posY), gm.ConvertX(obj.width), gm.ConvertY(obj.height));
		}
		else {
			g.setColor(Color.PINK);
			g.fillRect(gm.ConvertPosX(obj.posX), gm.ConvertPosY(obj.posY), gm.ConvertX(obj.width), gm.ConvertY(obj.height));
			if(drawbounds)
			boundsRender(g);
		}
		
		}
	}

	public void boundsRender(Graphics g) {
		Graphics2D g2d= (Graphics2D)g;
		BoundingBox b;
		
		g2d.setColor(Color.BLACK);
		if (obj instanceof Player) {
			Player p= (Player)obj;
			b=p.getBounds(Side.Top);
			
			g2d.draw(scaled(b));
			b=p.getBounds(Side.Bottom);
			g2d.draw(scaled(b));
			b=p.getBounds(Side.Right);
			g2d.draw(scaled(b));
			b=p.getBounds(Side.Left);
			g2d.draw(scaled(b));
			
			if(p.attacking) {
				g2d.setColor(Color.RED);
				b=p.getHitBox();
				g2d.draw(scaled(b));
			}
		}
	
	}
	Rectangle2D.Float scaled(Rectangle2D.Float b){
		Float scaled = new Rectangle2D.Float(gm.ConvertPosX(b.x),gm.ConvertPosY(b.y),gm.ConvertX(b.width),gm.ConvertY(b.height));
		return scaled;
	}
}
