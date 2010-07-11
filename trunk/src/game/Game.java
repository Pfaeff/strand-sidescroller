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
 * Klasse für die Spiel- und Interaktions-Logik
 * @author Kai
 */
public class Game implements KeyListener {
	private GameTimer gameTimer;
	
	final private int width;
	final private int height;

	private Camera camera; 
	private Player player;
	private LifeGauge life;
	private Background bg;
	private ArrayList<Entity> entities;
	
	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;
	
	// TEST
	private int collectedMilks = 0;
				
	
	public Game(GameTimer gameTimer, int width, int height) {
		camera = new Camera(new Vector2f(0, 0), new Vector2f(80, 0));
		player = new Player();
		entities = new ArrayList<Entity>();
		player.setPosition(new Vector2f(width/2.0f, height/2.0f));
		this.gameTimer = gameTimer;
		this.width = width;
		this.height = height;
		
		life = new LifeGauge();
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
		life.update(dt);
		
		// Kollision (Boundaries)
		// links
		if (((player.getPosition().getX()-(player.getPlayerSize().getX()/2.0f)) <= camera.getPosition().getX())) {
			player.setPosition(new Vector2f(camera.getPosition().getX() + (player.getPlayerSize().getX()/2.0f), player.getPosition().getY()));
		}
		// rechts
		if (((player.getPosition().getX()+(player.getPlayerSize().getX()/2.0f)) >= (camera.getPosition().getX()+width))) {
			player.setPosition(new Vector2f(camera.getPosition().getX() - (player.getPlayerSize().getX()/2.0f) + width, player.getPosition().getY()));
		}		
		// unten
		if (((player.getPosition().getY()-(player.getPlayerSize().getY()/2.0f)) <= 0)) {
			player.setPosition(new Vector2f(player.getPosition().getX(), player.getPlayerSize().getY()/2.0f));
		}		
		// oben
		if (((player.getPosition().getY()+(player.getPlayerSize().getY()/2.0f)) >= (height-50))) {
			player.setPosition(new Vector2f(player.getPosition().getX(), (height-50) - (player.getPlayerSize().getY()/2.0f)));
		}			
		// Kollision
		Iterator<Entity> it = entities.iterator();
		while (it.hasNext()) {
			Entity e = it.next();
			e.update(dt);
			// Milk
			if (e instanceof SunMilk) {
				if (player.collidesWith((ICollidable)e)) {
					it.remove();
					collectedMilks++;
					life.fill();
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
		// Speicher sparen
		removeOldEntities();
	}
	
	private void removeOldEntities() {
		Iterator<Entity> it = entities.iterator();
		while (it.hasNext()) {
			Entity e = it.next();
			if (((e.getPosition().getX()+(e.getWidth()/2.0f)) <= camera.getPosition().getX())) {
				it.remove();
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
		life.draw(renderer, gl);
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
