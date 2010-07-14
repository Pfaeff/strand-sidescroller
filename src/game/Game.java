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
import sound.AudioManager;

import math.Rectangle;
import math.Vector2f;
import engine.GameTimer;

/**
 * Klasse f�r die Spiel- und Interaktions-Logik
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
	
	private int num_of_last_generated_entites = 0;
	
	private double timeMultiplier;
	
	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;
	
	// TEST
	private int collectedMilks = 0;
				
	
	public Game(GameTimer gameTimer, int width, int height) {
		camera = new Camera(new Vector2f(0, 0), new Vector2f(180, 0));
		entities = new ArrayList<Entity>();
		this.gameTimer = gameTimer;
		this.width = width;
		this.height = height;
		
		life = new LifeGauge();
		player = new Player(life);
		player.setPosition(new Vector2f(width/2.0f, height/2.0f));
		bg = new Background(TextureManager.background, camera, width, height);
		timeMultiplier = 1;
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
		timeMultiplier += dt/100;
		dt = dt * (float)timeMultiplier;
		
		Vector2f direction = getMovementDirectionVector();
		direction = direction.scale(dt);
		player.setDirection(direction);
		player.update(dt);
		bg.update(dt);
		life.update(dt);
		
		if (!player.isDead()) {
			// Kamera
			camera.update(dt);						
			// Kollision
			playerCollision(dt);		
		} 
		
		// Speicher sparen
		removeOldEntities();
		
		// Level weiter-erzeugen
		generateLevel();
	}
	
	private void playerCollision(float dt) {
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
					// Sound abspielen
					Random r = new Random();
					int rs = r.nextInt(2);
					AudioManager.playSound(AudioManager.milk[rs]);
				}
			}
			// Fat Women
			if (e instanceof Obstacle) {
				if (player.collidesWith((ICollidable)e)) {
					Vector2f mtd = Rectangle.getMTD(player.getRectangle(), ((ICollidable)e).getRectangle());
					player.position = Vector2f.add(player.position, mtd);
				}
			}
		}			
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
	
	private void generateLevel() {
		if (entities.size() <= num_of_last_generated_entites / 2) {	
			num_of_last_generated_entites = 0;
			final int bruteForceLimit = 50;
			Random r = new Random();
			// Sonnenmilch erzeugen
			for (int i=1; i<=5; i++) {
				SunMilk m = new SunMilk();
				boolean doesCollide;
				int c = 0;
				// Kollision mit anderen Objekten verhindern (Brute Force)
				do {
					doesCollide = false;
					float x = r.nextInt(Math.round(1.5f*width)) + camera.position.getX() + width;
					float y = r.nextInt(height-Math.round(m.height)-120) + m.height/2;
					m.setPosition(new Vector2f(x, y));
					for (Entity e : entities) {
						if (e instanceof ICollidable) {
							if (m.collidesWith((ICollidable)e)) {
								doesCollide = true;
								break;
							}
						}
					}
					c++;
					if (c >= bruteForceLimit) {
						break;
					}
				} while (doesCollide);
				entities.add(m);		
				num_of_last_generated_entites++;
			}
			// Hindernisse erzeugen
			for (int i=1; i<=6; i++) {
				Obstacle obstacle;
				int ot = r.nextInt(3);
				// Zufälliges Hindernis
				if (ot == 0) {
					obstacle = new FatWoman();
				} else if (ot == 1){
					obstacle = new Crab();
				} else {
					obstacle = new Radio();
				}
				boolean doesCollide;
				int c = 0;
				// Kollision mit anderen Objekten verhindern (Brute Force)
				do {
					doesCollide = false;
					float x = r.nextInt(Math.round(1.5f*width)) + camera.position.getX() + width;
					float y = r.nextInt(height-Math.round(obstacle.height)-120) + obstacle.height/2;
					obstacle.setPosition(new Vector2f(x, y));
					for (Entity e : entities) {
						if (e instanceof ICollidable) {
							if (obstacle.collidesWithEnsure((ICollidable)e)) {
								doesCollide = true;
								break;
							}
						}
					}
					c++;
					if (c >= bruteForceLimit) {
						break;
					}					
				} while (doesCollide);
				entities.add(obstacle);	
				num_of_last_generated_entites++;
			}	
			// flugzeug
			if (r.nextInt(100) < 30) {
				Plane plane = new Plane();
				plane.setPosition(Vector2f.add(camera.position, new Vector2f(width, 420)));
				entities.add(plane);
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
