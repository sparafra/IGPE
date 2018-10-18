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
				if(b.act == Action.START_GAME)
					g.drawImage(gm.getMedia().getImage(ObjectId.BUTTON, PlayerState.NULL, "Start", 0), gm.ConvertPosX(obj.getPosX()), gm.ConvertPosY(obj.getPosY()), gm.ConvertX(obj.getWidth()), gm.ConvertY(obj.getHeight()), null);
				else if (b.act == Action.START_MULTIPLAYER_GAME)
					g.drawImage(gm.getMedia().getImage(ObjectId.BUTTON, PlayerState.NULL, "Multiplayer", 0), gm.ConvertPosX(obj.getPosX()), gm.ConvertPosY(obj.getPosY()), gm.ConvertX(obj.getWidth()), gm.ConvertY(obj.getHeight()), null);
				else if (b.act == Action.START_TRAINING)
					g.drawImage(gm.getMedia().getImage(ObjectId.BUTTON, PlayerState.NULL, "EditorMap", 0), gm.ConvertPosX(obj.getPosX()), gm.ConvertPosY(obj.getPosY()), gm.ConvertX(obj.getWidth()), gm.ConvertY(obj.getHeight()), null);
				else if (b.act == Action.OPEN_SETTING)
					g.drawImage(gm.getMedia().getImage(ObjectId.BUTTON, PlayerState.NULL, "Settings", 0), gm.ConvertPosX(obj.getPosX()), gm.ConvertPosY(obj.getPosY()), gm.ConvertX(obj.getWidth()), gm.ConvertY(obj.getHeight()), null);
				else if (b.act == Action.CLOSE_GAME)
					g.drawImage(gm.getMedia().getImage(ObjectId.BUTTON, PlayerState.NULL, "Exit", 0), gm.ConvertPosX(obj.getPosX()), gm.ConvertPosY(obj.getPosY()), gm.ConvertX(obj.getWidth()), gm.ConvertY(obj.getHeight()), null);
				else if (b.act == Action.RESUME)
					g.drawImage(gm.getMedia().getImage(ObjectId.BUTTON, PlayerState.NULL, "Resume", 0), gm.ConvertPosX(obj.getPosX()), gm.ConvertPosY(obj.getPosY()), gm.ConvertX(obj.getWidth()), gm.ConvertY(obj.getHeight()), null);
				else if (b.act == Action.CREAPARTITA)
					g.drawImage(gm.getMedia().getImage(ObjectId.BUTTON, PlayerState.NULL, "CreaPartita", 0), gm.ConvertPosX(obj.getPosX()), gm.ConvertPosY(obj.getPosY()), gm.ConvertX(obj.getWidth()), gm.ConvertY(obj.getHeight()), null);
				else if (b.act == Action.PARTECIPA)
					g.drawImage(gm.getMedia().getImage(ObjectId.BUTTON, PlayerState.NULL, "Partecipa", 0), gm.ConvertPosX(obj.getPosX()), gm.ConvertPosY(obj.getPosY()), gm.ConvertX(obj.getWidth()), gm.ConvertY(obj.getHeight()), null);
				else if (b.act == Action.BACKTOMENU)
					g.drawImage(gm.getMedia().getImage(ObjectId.BUTTON, PlayerState.NULL, "Back", 0), gm.ConvertPosX(obj.getPosX()), gm.ConvertPosY(obj.getPosY()), gm.ConvertX(obj.getWidth()), gm.ConvertY(obj.getHeight()), null);
				
				if(b.isSelected())
					g.drawImage(gm.getMedia().getImage(ObjectId.BUTTON, PlayerState.NULL, "Selected", 0), gm.ConvertPosX(obj.getPosX()), gm.ConvertPosY((obj.getPosY()+obj.getHeight())), gm.ConvertX(obj.getWidth()), gm.ConvertY((obj.getHeight()/6)), null);
			}
			else if(obj instanceof PlayerPreview)
			{
				PlayerPreview pp = (PlayerPreview)obj;
				g.drawImage(gm.getMedia().getImage(ObjectId.CHARACTER, PlayerState.PREVIEW, pp.getSelectedPlayer(), 0), gm.ConvertPosX(obj.getPosX()), gm.ConvertPosY(obj.getPosY()), gm.ConvertX(obj.getWidth()), gm.ConvertY(obj.getHeight()), null);
				if(!pp.getActive())
					g.drawImage(gm.getMedia().getImage(ObjectId.CHARACTER, PlayerState.PREVIEW, pp.getSelectedPlayer(), 1), gm.ConvertPosX(obj.getPosX()-20), gm.ConvertPosY(obj.getPosY()-10), gm.ConvertX(obj.getWidth()+40), gm.ConvertY(obj.getHeight()+20), null);

			}
		}
	}

	

}
