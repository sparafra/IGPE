package object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import gameManager.GameManager;
import object.BoundingBox.Side;
import object.Media.State;

public class PlayerRenderer extends ObjectRenderer{

	public PlayerRenderer(GameObject o, GameManager g) {
		super(o, g);
		
	}

	@Override
	public void DefaultRender(Graphics g) {
		
		g.setColor(Color.PINK);
		g.fillRect(gm.ConvertPosX(obj.posX), gm.ConvertPosY(obj.posY), gm.ConvertX(obj.width), gm.ConvertY(obj.height));
		
		if(!test) {
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
		// TODO Auto-generated method stub
			Player p= (Player)obj;
			b=p.getBounds(Side.Top);
			
			g2d.draw(scale(b));
			b=p.getBounds(Side.Bottom);
			g2d.draw(scale(b));
			b=p.getBounds(Side.Right);
			g2d.draw(scale(b));
			b=p.getBounds(Side.Left);
			g2d.draw(scale(b));
	}
	}
}
