package game;

import javax.media.opengl.GL;

import render.Animation;
import render.Renderer;
import render.TextureManager;

import math.Rectangle;
import math.Vector2f;

public class Player extends Entity implements ICollidable {
	private Vector2f direction;
	private Animation stand;
	private Animation walk;
	private boolean moves;
	private boolean movesLeft;
	private float anim_dt;
	final static private float velocityLimit = 300;
	final static private float velocityMinimum = 20;
	final static private float acceleration = 500000;
	final static private float friction = 4f;
	final static private Vector2f playerSize = new Vector2f(50, 100);
	
	public Player() {
		direction = new Vector2f();
		stand = new Animation(TextureManager.horst_stand_tex, 5000, 4, 1, true);	
		stand.setSize(playerSize);
		walk = new Animation(TextureManager.horst_walk_tex, 1000, 4, 1, true);	
		walk.setSize(playerSize);
		moves = false;
		movesLeft = false;
	}

	@Override
	public void update(float dt) {
		// a = F/m und m=1 ^^ Die beiden wirkenden Kräfte sind die Tasten der Tastatur ;) und die Reibung,
		// die von der Geschwindigkeit abhängt und in entgegengesetzter Richtung wirkt
		Vector2f acc = Vector2f.sub(direction.scale(acceleration), velocity.scale(friction)).scale(dt); 
		velocity = Vector2f.add(velocity, acc);
		float vL = velocity.length();
		if (vL > velocityLimit) {
			velocity = velocity.normalize().scale(velocityLimit);
		}
		if ((direction.lengthSquare() == 0) && (vL < velocityMinimum)) {
			moves = false;			
			velocity = new Vector2f();
		} else {
			moves = true;
			position = Vector2f.add(position, velocity.scale(dt));				
		}
		
		if (velocity.getX() < 0) {
			movesLeft = true;
		} else {
			if (velocity.getX() > 0) {
				movesLeft = false;
			}
		}
		
		anim_dt = dt;		
	}

	@Override
	public void draw(Renderer renderer, GL gl) {
		stand.setPosition(position);
		walk.setPosition(position);
		if (!moves) {
			// Stehen
			stand.update(anim_dt);
			walk.reset();
			stand.render(gl, movesLeft, false);
		} else {
			// Gehen
			walk.update(anim_dt);	
			walk.render(gl, movesLeft, false);
		}
	}

	public void setDirection(Vector2f v) {
		direction = new Vector2f(v);
	}

	public void setStandAnimation(Animation stand) {
		stand.setSize(playerSize);
		this.stand = stand;
	}

	public void setWalkAnimation(Animation walk) {
		walk.setSize(playerSize);
		this.walk = walk;
	}

	@Override
	public boolean collidesWith(ICollidable c) {
		return Rectangle.intersect(getRectangle(), c.getRectangle());
	}

	@Override
	public Rectangle getRectangle() {
		return new Rectangle(Vector2f.sub(position, playerSize.scale(0.5f)), Vector2f.add(position, playerSize.scale(0.5f)));
	}

}
