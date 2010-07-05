package game;

import javax.media.opengl.GL;

import render.Animation;

import math.Vector2f;

public class Player extends Entity {
	private Vector2f direction;
	private Animation stand;
	private Animation walk;
	private boolean moves;
	private float anim_dt;
	final static private float velocityLimit = 250;
	final static private float velocityMinimum = 1;
	final static private float acceleration = 550000;
	final static private float friction = 200;
	
	public Player() {
		direction = new Vector2f();
		stand = new Animation();
		walk = new Animation();
		moves = false;
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
		anim_dt = dt;		
	}

	@Override
	public void draw(GL gl) {
		stand.setPosition(position);
		walk.setPosition(position);
		if (!moves) {
			// Stehen
			stand.update(anim_dt);
			walk.reset();
			stand.render(gl);
		} else {
			// Gehen
			walk.update(anim_dt);	
			walk.render(gl);
		}
	}

	public void setDirection(Vector2f v) {
		direction = new Vector2f(v);
	}

	public void setStandAnimation(Animation stand) {
		this.stand = stand;
	}

	public void setWalkAnimation(Animation walk) {
		this.walk = walk;
	}

}
