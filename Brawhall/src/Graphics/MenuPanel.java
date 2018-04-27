package Graphics;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JPanel;

public class MenuPanel extends JPanel {

	Image background;
	Image Start;
	Image Setting;
	Image Exit;
	public MenuPanel(Image background)
	{
		super();
		this.background = background;
		initGUI();
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		Dimension d = this.getSize();
		
		g2d.drawImage(background, 0, 0, this);
		g.drawString(d.width + " " + d.height, 30, 30);
		g2d.drawImage(Start, (d.width/2)-(Start.getWidth(null)/2), (d.height/2)-((Start.getHeight(null)*3)/2), this);
		g2d.drawImage(Setting,(d.width/2)-(Start.getWidth(null)/2), (d.height/2)-((Start.getHeight(null)*2)/2), this);
		g2d.drawImage(Exit, (d.width/2)-(Start.getWidth(null)/2), (d.height/2)-((Start.getHeight(null)*1)/2), this);
	}
	public void initGUI()
	{
		Toolkit tk = Toolkit.getDefaultToolkit();
		
		Start = tk.getImage("C:\\Users\\spara\\eclipse-workspace\\Brawhall\\src\\Images\\Buttons\\Start.png");
		Setting = tk.getImage("C:\\Users\\spara\\eclipse-workspace\\Brawhall\\src\\Images\\Buttons\\Setting.png");
		Exit = tk.getImage("C:\\Users\\spara\\eclipse-workspace\\Brawhall\\src\\Images\\Buttons\\Exit.png");
		
		Dimension d = tk.getScreenSize();
		this.setSize(d);
	}
	
}
