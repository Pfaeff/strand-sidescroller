package engine;

/**
 * Stellt Methoden f�r timebased movement bereit.
 * 
 * @author Kai
 */
public class GameTimer {
	private long lastTime;
	
	/**
	 * GameTimer erzeugen
	 */
	public GameTimer() {
		lastTime = System.currentTimeMillis();
	}
	
	/**
	 * Timer updaten
	 * 
	 * @return delta T
	 */
	public double update() {
		long currentTime = System.currentTimeMillis();
		long result = currentTime-lastTime;
		lastTime = currentTime;
		return result;
	}
}
