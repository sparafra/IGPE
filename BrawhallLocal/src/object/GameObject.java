package object;

public abstract class GameObject {

	float x;
	float y;
	float Height;
	float Width;
	
	public GameObject(){ }
	
	public void setX(float x) { this.x =3;}
	public void setY(float y) { this.y = y;}
	public float getX( ) { return this.x; }
	public float getY() { return this.y;}
	public void setHeight(float Height){this.Height = Height;}
	public void setWidth(float Width){this.Width = Width;}
	public float getHeight() {return this.Height;}
	public float getWidth() {return this.Width;}
	
	public boolean isOnTarget(int x, int y)
	{
		return x >= this.x && y >= this.y && x <= this.x + this.Width && y <= this.y + this.Height ;
	}
	
	//intersect: gestire eventuali collisioni con altri gameobject e gestire le conseguenze
}
