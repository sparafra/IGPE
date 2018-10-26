package World;

import java.util.LinkedList;

import Objects.ObjectRenderer;

public class Painter {
	
	LinkedList<ObjectRenderer>renderers;
	

	MyPanel p;
	public Painter() {
		// TODO Auto-generated constructor stub
	}

	public void addRenderer(ObjectRenderer r) {
		// TODO Auto-generated method stub
		renderers.add(r);
	}

	public void clear() {
		renderers.clear();
		
	}

	public void setPanel(MyPanel pn) {
		p=pn;
		
	}

	public void render() {
		
		p.render();
	}

	public void setRenderers(LinkedList<ObjectRenderer> r) {
		renderers=r;
		p.setRenderers(r);
	}

	public LinkedList<ObjectRenderer> getRenderers() {
		// TODO Auto-generated method stub
		return renderers;
	}

	public MyPanel getPanel() {
		return p;
	}

	
	
}
