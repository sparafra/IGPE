package object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

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
			g.fillRect(gm.ConvertX(obj.posX), gm.ConvertY(obj.posY), gm.ConvertX(obj.width), gm.ConvertY(obj.height));
			g.setColor(Color.BLACK);
			g.drawRect(gm.ConvertX(obj.posX), gm.ConvertY(obj.posY), gm.ConvertX(obj.width), gm.ConvertY(obj.height));
		}
		else {
			g.setColor(Color.PINK);
			g.fillRect(gm.ConvertX(obj.posX), gm.ConvertY(obj.posY), gm.ConvertX(obj.width), gm.ConvertY(obj.height));
			if(drawbounds)
			boundsRender(g);
		}
		
		}
	}

	public void boundsRender(Graphics g) {
		Graphics2D g2d= (Graphics2D)g;
		BoundingBox b;
		BoundingBox scaled;
		g2d.setColor(Color.BLACK);
		if (obj instanceof Player) {
			
			b=((Player) obj).getBounds(Side.Top);
			scaled=new BoundingBox(gm.ConvertX((float)b.x),gm.ConvertY((float)b.y),gm.ConvertX((float)b.width),gm.ConvertY((float)b.height));
			g2d.draw(scaled);
			b=((Player) obj).getBounds(Side.Bottom);
			scaled=new BoundingBox(gm.ConvertX((float)b.x),gm.ConvertY((float)b.y),gm.ConvertX((float)b.width),gm.ConvertY((float)b.height));
			g2d.draw(scaled);
			b=((Player) obj).getBounds(Side.Right);
			scaled=new BoundingBox(gm.ConvertX((float)b.x),gm.ConvertY((float)b.y),gm.ConvertX((float)b.width),gm.ConvertY((float)b.height));
			g2d.draw(scaled);
			b=((Player) obj).getBounds(Side.Left);
			scaled=new BoundingBox(gm.ConvertX((float)b.x),gm.ConvertY((float)b.y),gm.ConvertX((float)b.width),gm.ConvertY((float)b.height));
			g2d.draw(scaled);
		}
	}
}
