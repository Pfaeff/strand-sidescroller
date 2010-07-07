package game;

import javax.media.opengl.GL;

import render.Animation;
import render.Renderer;

import math.Vector2f;

public class Player extends Entity {
	private Vector2f direction;
	private Animation stand;
	private Animation walk;
	private boolean moves;
	private boolean movesLeft;
	private float anim_dt;
	final static private float velocityLimit = 250;
	final static private float velocityMinimum = 1;
	final static private float acceleration = 600000;
	final static private float friction = 400;
	final static private Vector2f playerSize = new Vector2f(50, 100);
	
	public Player() {
		direction = new Vector2f();
		stand = new Animation();
		walk = new Animation();
		moves = false;
		movesLeft = false;
	}

	@Override
	public void update(float dt) {
		velocity = Vector2f.add(velocity, direction.scale(acceleration * dt));
		velocity = Vector2f.sub(velocity, velocity.normalize().scale(friction*dt));
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

}
