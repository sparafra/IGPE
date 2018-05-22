package object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

import gameManager.GameManager;
import object.BoundingBox.Side;
import object.Media.State;

public class ObjectRenderer {

	GameObject obj;
	GameManager gm;
	boolean drawbounds=true;
	Toolkit tk;
	public ObjectRenderer(GameObject o,GameManager g) {
		
		obj=o;
		gm=g;
		tk = Toolkit.getDefaultToolkit();;
	}
	
	public void DefaultRender(Graphics g) {
		
		if (obj.isUpdated())
		{
			if (obj instanceof Block) 
			{
				//g.setColor(Color.CYAN);
				//g.fillRect(gm.ConvertPosX(obj.posX), gm.ConvertPosY(obj.posY), gm.ConvertX(obj.width), gm.ConvertY(obj.height));
				//g.setColor(Color.BLACK);
				//g.drawRect(gm.ConvertPosX(obj.posX), gm.ConvertPosY(obj.posY), gm.ConvertX(obj.width), gm.ConvertY(obj.height));
				g.drawImage(gm.getMedia().getImage(ObjectId.BLOCK, State.NULL, "Standard", 0), gm.ConvertPosX(obj.posX), gm.ConvertPosY(obj.posY), gm.ConvertX(obj.width), gm.ConvertY(obj.height), null);
			}
			else if(obj instanceof Button) 
			{
				//g.setColor(Color.PINK);
				//g.fillRect(gm.ConvertWX(obj.posX), gm.ConvertWY(obj.posY), gm.ConvertWX(obj.width), gm.ConvertWY(obj.height));
				
				
				g.drawImage(gm.getMedia().getImage(ObjectId.BUTTON, State.NULL, "Start", 0), gm.ConvertWX(obj.getPosX()), gm.ConvertWY(obj.getPosY()), gm.ConvertWX(obj.getWidth()), gm.ConvertWY(obj.getHeight()), null);

			}
			else if(obj instanceof Background)
			{
				g.drawImage(gm.getMedia().getImage(ObjectId.BACKGROUND, State.NULL, "Sky", 0), gm.ConvertWX(obj.getPosX()), gm.ConvertWY(obj.getPosY()), gm.ConvertWX(obj.getWidth()), gm.ConvertWY(obj.getHeight()), null);
			}
			else if(obj instanceof Player)
			{
				//g.setColor(Color.PINK);
				//g.fillRect(gm.ConvertPosX(obj.posX), gm.ConvertPosY(obj.posY), gm.ConvertX(obj.width), gm.ConvertY(obj.height));
				//g.drawImage(tk.getImage("C:\\Users\\spara\\eclipse-workspace\\BrawhallLocal\\src\\Images\\Buttons\\Start.png"),gm.ConvertPosX(obj.posX), gm.ConvertPosY(obj.posY), gm.ConvertX(obj.width), gm.ConvertY(obj.height), null);
				
				if(((Player) obj).isMovingRight())
					g.drawImage(gm.getMedia().getImage(ObjectId.CHARACTER, State.FORWARD, "Zombie", gm.getMedia().nextCharacterFrames(State.FORWARD, "Zombie")),gm.ConvertPosX(obj.posX), gm.ConvertPosY(obj.posY), gm.ConvertX(obj.width), gm.ConvertY(obj.height), null);
				else if (((Player) obj).isMovingLeft())
					g.drawImage(gm.getMedia().getImage(ObjectId.CHARACTER, State.BACK, "Zombie", gm.getMedia().nextCharacterFrames(State.BACK, "Zombie")),gm.ConvertPosX(obj.posX), gm.ConvertPosY(obj.posY), gm.ConvertX(obj.width), gm.ConvertY(obj.height), null);
				else if (((Player) obj).isFalling())
				{
					g.drawImage(gm.getMedia().getImage(ObjectId.CHARACTER, State.FALLING, "Zombie", gm.getMedia().nextCharacterFrames(State.FALLING, "Zombie")),gm.ConvertPosX(obj.posX), gm.ConvertPosY(obj.posY), gm.ConvertX(obj.width), gm.ConvertY(obj.height), null);
					//System.out.println("Cade");
				}
				else if (((Player) obj).isCrouching())
					g.drawImage(gm.getMedia().getImage(ObjectId.CHARACTER, State.CROUCHING, "Zombie", gm.getMedia().nextCharacterFrames(State.CROUCHING, "Zombie")),gm.ConvertPosX(obj.posX), gm.ConvertPosY(obj.posY), gm.ConvertX(obj.width), gm.ConvertY(obj.height), null);
				else if (((Player) obj).isJumping())
				{
					g.drawImage(gm.getMedia().getImage(ObjectId.CHARACTER, State.JUMPING, "Zombie", gm.getMedia().nextCharacterFrames(State.JUMPING, "Zombie")),gm.ConvertPosX(obj.posX), gm.ConvertPosY(obj.posY), gm.ConvertX(obj.width), gm.ConvertY(obj.height), null); 
					System.out.println("Salta");
				}
				else if(((Player) obj).isResting())
				{
					g.drawImage(gm.getMedia().getImage(ObjectId.CHARACTER, State.STEADYFORWARD, "Zombie", gm.getMedia().nextCharacterFrames(State.STEADYFORWARD, "Zombie")),gm.ConvertPosX(obj.posX), gm.ConvertPosY(obj.posY), gm.ConvertX(obj.width), gm.ConvertY(obj.height), null); 
					System.out.println("Steady");

				}
				

				
				
				if(drawbounds)
					boundsRender(g);
			}
			else
			{
				//g.setColor(Color.PINK);
				//g.fillRect(gm.ConvertPosX(obj.posX), gm.ConvertPosY(obj.posY), gm.ConvertX(obj.width), gm.ConvertY(obj.height));
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
			scaled=new BoundingBox(gm.ConvertPosX((float)b.x),gm.ConvertPosY((float)b.y),gm.ConvertX((float)b.width),gm.ConvertY((float)b.height));
			g2d.draw(scaled);
			b=((Player) obj).getBounds(Side.Bottom);
			scaled=new BoundingBox(gm.ConvertPosX((float)b.x),gm.ConvertPosY((float)b.y),gm.ConvertX((float)b.width),gm.ConvertY((float)b.height));
			g2d.draw(scaled);
			b=((Player) obj).getBounds(Side.Right);
			scaled=new BoundingBox(gm.ConvertPosX((float)b.x),gm.ConvertPosY((float)b.y),gm.ConvertX((float)b.width),gm.ConvertY((float)b.height));
			g2d.draw(scaled);
			b=((Player) obj).getBounds(Side.Left);
			scaled=new BoundingBox(gm.ConvertPosX((float)b.x),gm.ConvertPosY((float)b.y),gm.ConvertX((float)b.width),gm.ConvertY((float)b.height));
			g2d.draw(scaled);
		}
	}
}
