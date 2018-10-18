package World;

import javax.swing.JFrame;

public class MyFrame extends JFrame {


	private static final long serialVersionUID = 1L;

	public MyFrame(int w,int h) {
		super();
		
		this.setSize(w, h);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setUndecorated(true);
	
	}

	

}
