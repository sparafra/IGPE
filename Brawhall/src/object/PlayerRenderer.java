package object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import gameManager.GameManager;
import object.BoundingBox.Side;

public class PlayerRenderer extends ObjectRenderer{

	Player p;
	public PlayerRenderer(Player p, GameManager g) {
		super(p, g);
		this.p=p;
	}
	@Override
	public void DefaultRender(Graphics g) {
		
		if(!test) 
		{
			System.out.println(p.getName());
			g.drawImage(gm.getMedia().getImage(ObjectId.CHARACTER, p.getState(), p.getName(), gm.getMedia().nextCharacterFrames(p.getState(), p.getName())),gm.ConvertPosX(obj.posX), gm.ConvertPosY(obj.posY), gm.ConvertX(obj.width), gm.ConvertY(obj.height), null);
			 
		}
		else {
			g.setColor(Color.PINK);
			g.fillRect(gm.ConvertPosX(obj.posX), gm.ConvertPosY(obj.posY), gm.ConvertX(obj.width), gm.ConvertY(obj.height));
		}
		if(drawbounds)
			boundsRender(g);
	}
	@Override
	public void boundsRender(Graphics g) {
		if(drawbounds) {
			Graphics2D g2d= (Graphics2D)g;
			BoundingBox b;
			
			g2d.setColor(Color.BLACK);
			Player p= (Player)obj;
			b=p.getBounds(Side.Top);
			
			g2d.draw(scale(b));
			b=p.getBounds(Side.Bottom);
			g2d.draw(scale(b));
			b=p.getBounds(Side.Right);
			g2d.draw(scale(b));
			b=p.getBounds(Side.Left);
			g2d.draw(scale(b));
		
			if(p.attacking) {
				g2d.setColor(Color.RED);
				b=p.getHitBox();
				g2d.draw(scale(b));
			}
		}
	}
}