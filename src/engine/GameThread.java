package engine;

import game.Game;

import javax.media.opengl.GLCanvas;

final public class GameThread extends Thread {
	private boolean runs = true;
	final private GLCanvas canvas;

	public GameThread(GLCanvas canvas) {
		super();
		this.canvas = canvas;
	}

	final public void run() {
		while (isRunning()) {
			canvas.display();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized void endGameLoop() {
		runs = false;
	}

	public synchronized boolean isRunning() {
		return runs;
	}
}
