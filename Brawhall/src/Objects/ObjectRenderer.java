package Objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import gameManager.GameManager;

public class ObjectRenderer {

	GameObject obj;
	GameManager gm;
	static Boolean test=false;
	static Boolean drawbounds=false;
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
					g.drawImage(Media.getImage(ObjectId.BLOCK, PlayerState.NULL, "Standard", 0),(int) gm.ConvertPosX(obj.posX),(int) gm.ConvertPosY(obj.posY), (int)gm.ConvertX(obj.width),(int) gm.ConvertY(obj.height), null);
				}
				else if(obj instanceof Button) 
				{
					g.drawImage(Media.getImage(ObjectId.BUTTON, PlayerState.NULL, "Start", 0), (int)gm.ConvertPosX(obj.getPosX()),(int) gm.ConvertPosY(obj.getPosY()),(int) gm.ConvertX(obj.getWidth()), (int)gm.ConvertY(obj.getHeight()), null);
				}
				else if(obj instanceof Background)
				{
					if(((Background) obj).getState() == "Null")
					{
						Image i=Media.getImage(ObjectId.BACKGROUND, PlayerState.NULL, "Sky", 0);
						g.drawImage(i,(int) gm.ConvertPosX(obj.getPosX()),(int) gm.ConvertPosY(obj.getPosY()),(int) gm.ConvertX(obj.getWidth()),(int) gm.ConvertY(obj.getHeight()), null);
					}
					if(((Background) obj).getState() == "Waiting")
					{
						Image i=Media.getImage(ObjectId.BACKGROUND, PlayerState.WAITING, "Sky", 0);
						Image i1=Media.getImage(ObjectId.BACKGROUND, PlayerState.NULL, "Sky", 0);
						g.drawImage(i1,(int) gm.ConvertPosX(obj.getPosX()),(int) gm.ConvertPosY(obj.getPosY()),(int) gm.ConvertX(obj.getWidth()),(int) gm.ConvertY(obj.getHeight()), null);
						g.drawImage(i,(int) gm.ConvertPosX((obj.getWidth()/2)-(i.getWidth(null)/7)),(int) gm.ConvertPosY((obj.getHeight()/2)-(i.getHeight(null)/7)),(int) gm.ConvertX(i.getWidth(null)/4), (int)gm.ConvertY(i.getHeight(null)/4), null);
					}
					
				}
				else
				{
					g.setColor(Color.PINK);
					g.fillRect((int)gm.ConvertPosX(obj.posX),(int) gm.ConvertPosY(obj.posY),(int) gm.ConvertX(obj.width),(int) gm.ConvertY(obj.height));
					
				}
				
			}
			
		
	}
}
	
