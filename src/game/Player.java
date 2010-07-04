package game;

import javax.media.opengl.GL;

import math.Vector2f;

public class Player extends Entity {
	private Vector2f direction;
	final static private float velocityLimit = 250;
	final static private float acceleration = 550000;
	final static private float friction = 200;
	
	public Player() {
		direction = new Vector2f();
	}

	@Override
	public void update(float dt) {
		velocity = Vector2f.add(velocity, direction.scale(acceleration * dt));
		velocity = Vector2f.sub(velocity, velocity.normalize().scale(friction*dt));
		float vL = velocity.length();
		if (vL > velocityLimit) {
			velocity = velocity.normalize().scale(velocityLimit);
		}
		position = Vector2f.add(position, velocity.scale(dt));
	}

	@Override
	public void draw(GL gl) {
		// Test
		int width = 100;
		int height = 100;
		gl.glPushMatrix();
		{
			gl.glTranslatef(-position.getX(), -position.getY(), 0);
			gl.glColor3f(1.0f, 1.0f, 1.0f);
			gl.glBegin(GL.GL_QUADS);
			{
				gl.glTexCoord2d(0, 1); gl.glVertex3f(width/2-100, height/2-100, 0);  
				gl.glTexCoord2d(1, 1); gl.glVertex3f(width/2+100, height/2-100, 0);
				gl.glTexCoord2d(1, 0); gl.glVertex3f(width/2+100, height/2+100, 0);
				gl.glTexCoord2d(0, 0); gl.glVertex3f(width/2-100, height/2+100, 0);
			}
			gl.glEnd();	
		}
		gl.glPopMatrix();
	}

	public void setDirection(Vector2f v) {
		direction = new Vector2f(v);
	}

}
