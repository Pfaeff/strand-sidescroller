package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import engine.GameTimer;

/**
 * Klasse für die Spiel- und Interaktions-Logik
 * @author Kai
 */
public class Game implements KeyListener {
	private GameTimer gameTimer;
	
	public Game(GameTimer gameTimer) {
		this.gameTimer = gameTimer;
		run();
	}
	
	private void run() {
		while (true) {
			float dt = gameTimer.update();
			
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
