package game;

import javax.media.opengl.GL;

import math.Vector2f;

public class Camera {
	private Vector2f position;
	
	public Camera(Vector2f position) {
		this.position = position;
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
}