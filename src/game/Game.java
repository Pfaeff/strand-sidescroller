package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.opengl.GL;

import math.Vector2f;
import engine.GameTimer;

/**
 * Klasse für die Spiel- und Interaktions-Logik
 * @author Kai
 */
public class Game implements KeyListener {
	private GameTimer gameTimer;
	
	private Camera camera; 
	private float testVelocity = 500;
	
	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;
				
	
	public Game(GameTimer gameTimer) {
		camera = new Camera(new Vector2f(0, 0));

		
		this.gameTimer = gameTimer;
	}
	
	public void updateGame() {
		float dt = gameTimer.update();
		
		float x = 0;
		float y = 0;
		if (left) {
			x = -1;
		} else {
			if (right){
				x = 1;
			}
		}
		if (down) {
			y = -1;
		} else {
			if (up){
				y = 1;
			}
		}		
		Vector2f direction = new Vector2f(-x, -y).normalize();
		direction = direction.scale(testVelocity*dt);
		camera.move(direction);	
	}
	
	public void draw(GL gl, int width, int height){
		/*
		 * Test (ein Dreieck)
		 */
		camera.apply(gl);
		gl.glColor3f(1.0f, 1.0f, 1.0f);
		gl.glBegin(GL.GL_QUADS);
			gl.glTexCoord2d(0, 1); gl.glVertex3f(width/2-100, height/2-100, 0);  
			gl.glTexCoord2d(1, 1); gl.glVertex3f(width/2+100, height/2-100, 0);
			gl.glTexCoord2d(1, 0); gl.glVertex3f(width/2+100, height/2+100, 0);
			gl.glTexCoord2d(0, 0); gl.glVertex3f(width/2-100, height/2+100, 0);
		gl.glEnd();	
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case (KeyEvent.VK_LEFT): {
			left = true;
			right = false;
			break;
		}
		case (KeyEvent.VK_RIGHT): {
			right = true;
			left = false;
			break;
		}
		case (KeyEvent.VK_UP): {
			up = true;
			down = false;
			break;
		}
		case (KeyEvent.VK_DOWN): {
			down = true;
			up = false;
			break;
		}		
		}		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case (KeyEvent.VK_LEFT): {
			left = false;
			break;
		}
		case (KeyEvent.VK_RIGHT): {
			right = false;
			break;
		}
		case (KeyEvent.VK_UP): {
			up = false;
			break;
		}
		case (KeyEvent.VK_DOWN): {
			down = false;
			break;
		}		
		}	
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
