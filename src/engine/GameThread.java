package engine;

import game.Game;

import javax.media.opengl.GLCanvas;

final public class GameThread extends Thread {
	private boolean runs = true;
	final private Game game;
	final private GLCanvas canvas;

	public GameThread(Game game, GLCanvas canvas) {
		super();
		this.game = game;
		this.canvas = canvas;
	}

	final public void run() {
		while (isRunning()) {
			game.updateGame();
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
