package game;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.media.opengl.GL;

import com.sun.opengl.util.j2d.TextRenderer;

import render.Background;
import render.Renderer;
import render.TextureManager;
import sound.AudioManager;

import math.Rectangle;
import math.Vector2f;
import engine.GameTimer;
import engine.HighScore;

/**
 * Klasse für die Spiel- und Interaktions-Logik
 * @author Kai
 */
public class Game implements KeyListener {
	private GameTimer gameTimer;
	private TextRenderer text;
	
	final private int width;
	final private int height;

	private Camera camera; 
	private Player player;
	private LifeGauge life;
	private Background bg;
	private ArrayList<Entity> entities;
	
	private boolean gameOver;
	private GameOver go;
	
	private int highScore;
	
	private int num_of_last_generated_entites = 0;
	
	private double timeMultiplier;
	
	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;
	
	private int score;
				
	
	public Game(GameTimer gameTimer, int width, int height) {
		this.gameTimer = gameTimer;
		this.width = width;
		this.height = height;	
		text = new TextRenderer(new Font("sansserif", Font.BOLD, 24), true, false);
		
		newGame();
	}
	
	public void newGame() {
		highScore = HighScore.readHighScore();
		camera = new Camera(new Vector2f(0, 0), new Vector2f(180, 0));
		entities = new ArrayList<Entity>();
		life = new LifeGauge();
		player = new Player(life);
		player.setPosition(new Vector2f(width/2.0f, height/2.0f));
		timeMultiplier = 1;
		gameOver = false;
		go = null;
		bg = new Background(TextureManager.background, camera, width, height);
		score = 0;
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
		life.setSpeedTimesFour(false);
		float dt = gameTimer.update();
		timeMultiplier += dt/100;
		dt = dt * (float)timeMultiplier;
		
		Vector2f direction = getMovementDirectionVector();
		direction = direction.scale(dt);
		player.setDirection(direction);
		player.update(dt);
		bg.update(dt);
		
		if (!player.isDead()) {
			// Kamera
			camera.update(dt);						
			// Kollision
			playerCollision(dt);
		}  else {
			if (!gameOver) {
				HighScore.writeHighScore(score);
				gameOver = true;
				go = new GameOver();
			}			
		}
	
		life.update(dt);		
		
		// Speicher sparen
		removeOldEntities();
		
		// Level weiter-erzeugen
		generateLevel();
		
		// GameOver
		if (gameOver) {
			if (go != null) {
				go.update(dt);
			}
		}
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
					score += 250;
					life.fill();
					// Sound abspielen
					Random r = new Random();
					int rs = r.nextInt(2);
					AudioManager.playSound(AudioManager.milk[rs]);
				}
			}
			// Obstacles
			if (e instanceof Obstacle) {
				if (e instanceof Shower) {
					if (player.collidesWith((ICollidable)e)) {
						life.setSpeedTimesFour(true);
					}
				} else {
					if (player.collidesWith((ICollidable)e)) {
						Vector2f mtd = Rectangle.getMTD(player.getRectangle(), ((ICollidable)e).getRectangle());
						player.position = Vector2f.add(player.position, mtd);
						if (e instanceof Crab) {
							AudioManager.playSound(AudioManager.hurt);
							player.applyImpulse(mtd.scale(10000));
						}
					}
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
			for (int i=1; i<=2; i++) {
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
			for (int i=1; i<=7; i++) {
				Obstacle obstacle;
				int ot = r.nextInt(4);
				// Zufälliges Hindernis
				if (ot == 0) {
					obstacle = new FatWoman();
				} else if (ot == 1){
					obstacle = new Crab();
				} else if (ot == 2) {
					obstacle = new Radio();
				} else {
					obstacle = new Shower();
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
			if (r.nextInt(100) < 20) {
				Plane plane = new Plane();
				AudioManager.playSound(AudioManager.plane);
				plane.setPosition(Vector2f.add(camera.position, new Vector2f(
						width + plane.width / 2.0f, 420)));
				entities.add(plane);
			}
		}
	}
	
	public void render(Renderer renderer, GL gl, int width, int height){
		gl.glColor3f(1.0f, 1.0f, 1.0f);
		camera.apply(gl);
		bg.draw(renderer, gl);
		player.draw(renderer, gl);		
		for (Entity e : entities) {
			e.draw(renderer, gl);
		}		
		life.draw(renderer, gl);
		if (go != null) {
			go.draw(renderer, gl);
		}		
	    text.beginRendering(width, height);
	    text.setColor(0.0f, 0.0f, 0.0f, 0.8f);
	    text.draw("Punkte: " + score, 740, 440);
	    text.draw("HighScore: " + highScore, 740, 410);
	    text.endRendering();
	    		
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
		case (KeyEvent.VK_ENTER): {
			newGame();
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
