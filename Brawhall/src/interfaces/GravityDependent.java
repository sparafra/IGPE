package interfaces;

public interface GravityDependent {
	
	float gravity=0.2f;
	float MAX_FALL_SPEED=10;

	public abstract void fall(double delta);
}
