package engine;

/**
 * Stellt Methoden f�r timebased movement bereit.
 * 
 * @author Kai
 */
public class GameTimer {
	private long lastTime;
	private long difference;

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
	public synchronized float update() {
		long currentTime = System.currentTimeMillis();
		difference = currentTime - lastTime;
		lastTime = currentTime;
		return difference / 1000.0f;
	}

	/**
	 * Timestep zur�ckgeben
	 * 
	 * @return delta T
	 */
	public synchronized float deltaT() {
		return difference / 1000.0f;
	}
}
