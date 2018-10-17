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
			g.drawImage(gm.getMedia().getImage(ObjectId.CHARACTER, p.getState(), p.getName(), gm.getMedia().nextCharacterFrames(p.getState(), p.getName())),gm.ConvertPosX(obj.posX), gm.ConvertPosY(obj.posY), gm.ConvertX(obj.width), gm.ConvertY(obj.height), null);
			 
		}
	}
}