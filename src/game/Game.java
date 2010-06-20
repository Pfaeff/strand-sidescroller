package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.opengl.GL;

import engine.GameTimer;

/**
 * Klasse für die Spiel- und Interaktions-Logik
 * @author Kai
 */
public class Game implements KeyListener {
	private GameTimer gameTimer;
	
	public Game(GameTimer gameTimer) {
		this.gameTimer = gameTimer;
		new Thread() {
			public void run() {
				while (true) {
					update();
				}
			}
		};
	}
	
	private synchronized void update() {
		float dt = gameTimer.update();
		
	}
	
	public synchronized void draw(GL gl, int width, int height){
		/*
		 * Test (ein Dreieck)
		 */
		gl.glColor3f(1.0f, 1.0f, 1.0f);
		gl.glBegin(GL.GL_TRIANGLES);
			gl.glVertex3f(width/2-100, height/2-100, 0);  
			gl.glVertex3f(width/2+100, height/2-100, 0);
			gl.glVertex3f(width/2    , height/2+100, 0);
		gl.glEnd();		
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
