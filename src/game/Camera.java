package game;

import javax.media.opengl.GL;

import render.Renderer;

import math.Vector2f;

public class Camera extends Entity {
	
	private Vector2f velocity;
	
	public Camera(Vector2f position, Vector2f velocity) {
		this.position = position;
		this.velocity = velocity;
	}
	
	public void move(Vector2f amount) {
		position = Vector2f.add(position, amount);
	}
	
	public void apply(GL gl) {
		gl.glTranslatef(-position.getX(), -position.getY(), 0);
	}
	
	public Vector2f getPosition() {
		return position;
	}

	@Override
	public void update(float dt) {
		position = Vector2f.add(position, velocity.scale(dt));
	}

	@Override
	public void draw(Renderer renderer, GL gl) {
		return;
	}
}
