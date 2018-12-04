package Objects;

import java.util.LinkedList;

public class Anchor extends GameObject {

	public Anchor(float x, float y, ObjectId id) {
		super(x, y, id);
		// TODO Auto-generated constructor stub
	}

	public void update(LinkedList<Player> linkedList, double delta) {
		float sumX=0,sumY=0;
		for (int i=0;i<linkedList.size();i++) {
			sumX+=linkedList.get(i).posX;
			sumY+=linkedList.get(i).posY;
		}
		posX=sumX/linkedList.size();
		posY=sumY/linkedList.size();
		
	}

	@Override
	public void tick(LinkedList<GameObject> objects, double delta) {
		// TODO Auto-generated method stub
		
	}
	

}
