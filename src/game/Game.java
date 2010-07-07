package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.media.opengl.GL;

import render.Renderer;

import math.Vector2f;
import engine.GameTimer;

/**
 * Klasse für die Spiel- und Interaktions-Logik
 * @author Kai
 */
public class Game implements KeyListener {
	private GameTimer gameTimer;
	
	private Camera camera; 
	private Player player;
	
	private ArrayList<Entity> entities;
	
	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;
				
	
	public Game(GameTimer gameTimer) {
		camera = new Camera(new Vector2f(0, 0));
		player = new Player();
		entities = new ArrayList<Entity>();
		player.setPosition(new Vector2f(100, 100));
		this.gameTimer = gameTimer;
		
		// Test
		Random r = new Random();
		for (int i=0; i<6; i++) {
			SunMilk m = new SunMilk();
			m.setPosition(new Vector2f(r.nextInt(800), r.nextInt(800)));
			entities.add(m);			
		}
	}
	
	private Vector2f getMovementDirectionVector() {
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
		return new Vector2f(x, y).normalize();
	}
	
	public void updateGame() {
		float dt = gameTimer.update();
		
		
		Vector2f direction = getMovementDirectionVector();
		direction = direction.scale(dt);
		player.setDirection(direction);
		player.update(dt);
	//	camera.move(direction);	
	}
	
	public void render(Renderer renderer, GL gl, int width, int height){
		camera.apply(gl);
		player.draw(renderer, gl);
		for (Entity m : entities) {
			m.draw(renderer, gl);
		}
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

	public Player getPlayer() {
		return player;
	}

}
