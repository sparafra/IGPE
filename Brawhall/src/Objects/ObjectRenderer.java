package Objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import gameManager.GameManager;

public class ObjectRenderer {

	GameObject obj;
	GameManager gm;
	static boolean test=false;
	static boolean drawbounds=false;
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
					g.drawImage(gm.getMedia().getImage(ObjectId.BLOCK, PlayerState.NULL, "Standard", 0), gm.ConvertPosX(obj.posX), gm.ConvertPosY(obj.posY), gm.ConvertX(obj.width), gm.ConvertY(obj.height), null);
				}
				else if(obj instanceof Button) 
				{
					g.drawImage(gm.getMedia().getImage(ObjectId.BUTTON, PlayerState.NULL, "Start", 0), gm.ConvertPosX(obj.getPosX()), gm.ConvertPosY(obj.getPosY()), gm.ConvertX(obj.getWidth()), gm.ConvertY(obj.getHeight()), null);
				}
				else if(obj instanceof Background)
				{
					if(((Background) obj).getState() == "Null")
					{
						Image i=gm.getMedia().getImage(ObjectId.BACKGROUND, PlayerState.NULL, "Sky", 0);
						g.drawImage(i, gm.ConvertPosX(obj.getPosX()), gm.ConvertPosY(obj.getPosY()), gm.ConvertX(obj.getWidth()), gm.ConvertY(obj.getHeight()), null);
					}
					if(((Background) obj).getState() == "Waiting")
					{
						Image i=gm.getMedia().getImage(ObjectId.BACKGROUND, PlayerState.WAITING, "Sky", 0);
						Image i1=gm.getMedia().getImage(ObjectId.BACKGROUND, PlayerState.NULL, "Sky", 0);
						g.drawImage(i1, gm.ConvertPosX(obj.getPosX()), gm.ConvertPosY(obj.getPosY()), gm.ConvertX(obj.getWidth()), gm.ConvertY(obj.getHeight()), null);
						g.drawImage(i, gm.ConvertPosX((obj.getWidth()/2)-(i.getWidth(null)/7)), gm.ConvertPosY((obj.getHeight()/2)-(i.getHeight(null)/7)), gm.ConvertX(i.getWidth(null)/4), gm.ConvertY(i.getHeight(null)/4), null);
					}
					
				}
				else
				{
					g.setColor(Color.PINK);
					g.fillRect(gm.ConvertPosX(obj.posX), gm.ConvertPosY(obj.posY), gm.ConvertX(obj.width), gm.ConvertY(obj.height));
					
				}
				
			}
			
		
	}

}
