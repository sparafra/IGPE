package Objects;

import java.awt.Graphics;

import gameManager.Action;
import gameManager.GameManager;

public class ControlRenderer extends ObjectRenderer 
{

	public ControlRenderer(Control o, GameManager g) {
		super(o, g);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void DefaultRender(Graphics g) {
		// TODO Auto-generated method stub
		if(!test)
		{
			if(obj instanceof Button)
			{
				Button b = (Button)obj;
				if(b.act == Action.MENU_START_LOCAL_GAME)
					g.drawImage(Media.getImage(ObjectId.BUTTON, PlayerState.NULL, "Start", 0), (int)gm.ConvertPosX(obj.getPosX()),(int) gm.ConvertPosY(obj.getPosY()),(int) gm.ConvertX(obj.getWidth()),(int) gm.ConvertY(obj.getHeight()), null);
				else if (b.act == Action.MENU_START_MULTIPLAYER_GAME)
					g.drawImage(Media.getImage(ObjectId.BUTTON, PlayerState.NULL, "Multiplayer", 0), (int)gm.ConvertPosX(obj.getPosX()),(int) gm.ConvertPosY(obj.getPosY()), (int)gm.ConvertX(obj.getWidth()),(int) gm.ConvertY(obj.getHeight()), null);
				else if (b.act == Action.OPEN_SETTING)
					g.drawImage(Media.getImage(ObjectId.BUTTON, PlayerState.NULL, "Settings", 0),(int) gm.ConvertPosX(obj.getPosX()),(int) gm.ConvertPosY(obj.getPosY()),(int) gm.ConvertX(obj.getWidth()),(int) gm.ConvertY(obj.getHeight()), null);
				else if (b.act == Action.MENU_CLOSE_GAME)
					g.drawImage(Media.getImage(ObjectId.BUTTON, PlayerState.NULL, "Exit", 0), (int)gm.ConvertPosX(obj.getPosX()),(int) gm.ConvertPosY(obj.getPosY()),(int) gm.ConvertX(obj.getWidth()),(int) gm.ConvertY(obj.getHeight()), null);
				else if (b.act == Action.RESUME)
					g.drawImage(Media.getImage(ObjectId.BUTTON, PlayerState.NULL, "Resume", 0),(int) gm.ConvertPosX(obj.getPosX()),(int) gm.ConvertPosY(obj.getPosY()), (int)gm.ConvertX(obj.getWidth()),(int) gm.ConvertY(obj.getHeight()), null);
				else if (b.act == Action.CREAPARTITA)
					g.drawImage(Media.getImage(ObjectId.BUTTON, PlayerState.NULL, "CreaPartita", 0),(int) gm.ConvertPosX(obj.getPosX()),(int) gm.ConvertPosY(obj.getPosY()),(int) gm.ConvertX(obj.getWidth()),(int) gm.ConvertY(obj.getHeight()), null);
				else if (b.act == Action.PARTECIPA)
					g.drawImage(Media.getImage(ObjectId.BUTTON, PlayerState.NULL, "Partecipa", 0),(int) gm.ConvertPosX(obj.getPosX()),(int) gm.ConvertPosY(obj.getPosY()),(int) gm.ConvertX(obj.getWidth()), (int)gm.ConvertY(obj.getHeight()), null);
				else if (b.act == Action.BACKTOMENU)
					g.drawImage(Media.getImage(ObjectId.BUTTON, PlayerState.NULL, "Back", 0),(int) gm.ConvertPosX(obj.getPosX()),(int) gm.ConvertPosY(obj.getPosY()), (int)gm.ConvertX(obj.getWidth()),(int) gm.ConvertY(obj.getHeight()), null);
				
				if(b.isSelected())
					g.drawImage(Media.getImage(ObjectId.BUTTON, PlayerState.NULL, "Selected", 0),(int) gm.ConvertPosX(obj.getPosX()),(int) gm.ConvertPosY((obj.getPosY()+obj.getHeight())),(int) gm.ConvertX(obj.getWidth()),(int) gm.ConvertY((obj.getHeight()/6)), null);
			}
			else if(obj instanceof PlayerPreview)
			{
				PlayerPreview pp = (PlayerPreview)obj;
				g.drawImage(Media.getImage(ObjectId.CHARACTER, PlayerState.PREVIEW, pp.getSelectedPlayer(), 0), (int)gm.ConvertPosX(obj.getPosX()),(int) gm.ConvertPosY(obj.getPosY()),(int) gm.ConvertX(obj.getWidth()),(int) gm.ConvertY(obj.getHeight()), null);
				if(!pp.getActive())
					g.drawImage(Media.getImage(ObjectId.CHARACTER, PlayerState.PREVIEW, pp.getSelectedPlayer(), 1),(int) gm.ConvertPosX(obj.getPosX()-20), (int)gm.ConvertPosY(obj.getPosY()-10),(int) gm.ConvertX(obj.getWidth()+40), (int)gm.ConvertY(obj.getHeight()+20), null);

			}
		}
	}

	

}
