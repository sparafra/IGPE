package Objects;

import java.awt.Graphics;
import java.awt.Image;

import gameManager.GameManager;
import gameManager.Time;

public class PlayerRenderer extends ObjectRenderer{

	Player p;
	double lastRender=12.0;
	double refreshRate=0.4;
	Image currentImage;
	int currentFrame=0;
	public PlayerRenderer(Player p, GameManager g) {
		super(p, g);
		this.p=p;
	}
	@Override
	public void DefaultRender(Graphics g) {
		lastRender+=Time.deltaTime();
		if(lastRender>refreshRate) {
			currentImage=Media.getImage(ObjectId.CHARACTER, p.getState(), p.getName(), Media.nextCharacterFrames(p.getState(), p.getName()));
			lastRender=0;
		}	 
		g.drawImage(currentImage,gm.ConvertPosX(obj.posX), gm.ConvertPosY(obj.posY), gm.ConvertX(obj.width), gm.ConvertY(obj.height), null);
	}


}