package game;

import javax.media.opengl.GL;

import math.Vector2f;

public class Player extends Entity {
	private Vector2f acceleration;
	
	public Player() {
		acceleration = new Vector2f();
	}

	@Override
	public void update(float dt) {
		velocity = Vector2f.add(velocity, acceleration.scale(dt));
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

	public void setAcceleration(Vector2f v) {
		acceleration = new Vector2f(v);
	}

}
