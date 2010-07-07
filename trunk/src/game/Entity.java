package game;

import javax.media.opengl.GL;

import render.Renderer;

import math.Vector2f;

public abstract class Entity {
	protected Vector2f position = new Vector2f();
	protected Vector2f velocity = new Vector2f();
	
	public abstract void update(float dt);
	
	public abstract void draw(Renderer renderer, GL gl);

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setVelocity(Vector2f velocity) {
		this.velocity = velocity;
	}

	public Vector2f getVelocity() {
		return velocity;
	}
}
