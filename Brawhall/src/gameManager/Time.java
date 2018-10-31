package gameManager;

public final class Time {
	static double lastTime = System.nanoTime();
	static double amountOfTicks = 60.0;
	 static double ns = 1000000000 / amountOfTicks;
	 static double delta = 0;
	public Time() {
		lastTime = System.nanoTime();
		
	}
	public static void update() {
		double now = System.nanoTime();
		delta = (now - lastTime) / ns;
		lastTime = now;
	}
	public static double deltaTime() {
		
		return delta;
	}

}
