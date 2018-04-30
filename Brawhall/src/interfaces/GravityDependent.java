package interfaces;

public interface GravityDependent {
	boolean falling=false;
	float gravity=0.05f;
	float MAX_FALL_SPEED=10;

	public abstract void fall();
}
