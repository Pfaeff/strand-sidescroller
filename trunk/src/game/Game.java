package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.media.opengl.GL;

import render.Background;
import render.Renderer;
import render.TextureManager;

import math.Rectangle;
import math.Vector2f;
import engine.GameTimer;

/**
 * Klasse f�r die Spiel- und Interaktions-Logik
 * @author Kai
 */
public class Game implements KeyListener {
	private GameTimer gameTimer;
	
	private Camera camera; 
	private Player player;
	
	private Background bg;
	private ArrayList<Entity> entities;
	
	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;
	
	// TEST
	private int collectedMilks = 0;
				
	
	public Game(GameTimer gameTimer, int width, int height) {
		camera = new Camera(new Vector2f(0, 0), new Vector2f(50, 0));
		player = new Player();
		entities = new ArrayList<Entity>();
		player.setPosition(new Vector2f(100, 100));
		this.gameTimer = gameTimer;
		
		bg = new Background(TextureManager.background, camera, width, height);
		
		// Test
		Random r = new Random();
		for (int i=1; i<=10; i++) {
			SunMilk m = new SunMilk();
			m.setPosition(new Vector2f(r.nextInt(2000), r.nextInt(300)));
			entities.add(m);			
		}
		for (int i=1; i<=5; i++) {
			FatWoman fw = new FatWoman();
			fw.setPosition(new Vector2f(r.nextInt(2000), r.nextInt(300)));
			entities.add(fw);			
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
		camera.update(dt);
		bg.update(dt);
		
		Iterator<Entity> it = entities.iterator();
		while (it.hasNext()) {
			Entity e = it.next();
			e.update(dt);
			// Milk
			if (e instanceof SunMilk) {
				if (player.collidesWith((ICollidable)e)) {
					it.remove();
					collectedMilks++;
					System.out.println("Eingesammelte Sonnencreme: " + collectedMilks);
				}
			}
			// Fat Women
			if (e instanceof FatWoman) {
				if (player.collidesWith((ICollidable)e)) {
					Vector2f mtd = Rectangle.getMTD(player.getRectangle(), ((ICollidable)e).getRectangle());
					player.position = Vector2f.add(player.position, mtd);
				}
			}
		}
	}
	
	public void render(Renderer renderer, GL gl, int width, int height){
		camera.apply(gl);
		bg.draw(renderer, gl);
		player.draw(renderer, gl);
		for (Entity e : entities) {
			e.draw(renderer, gl);
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
