package render;

import game.Camera;
import game.Entity;

import javax.media.opengl.GL;

import math.Vector2f;

import com.sun.opengl.util.texture.Texture;

public class Background extends Entity {
	private Camera cam;
	private Texture texture;
	private int width;
	private int height;
	
	public Background(Texture texture, Camera cam, int width, int height) {
		this.cam = cam;
		this.texture = texture;
		this.width = width;
		this.height = height; 
		position = new Vector2f();
	}

	@Override
	public void draw(Renderer renderer, GL gl) {
		gl.glPushMatrix();
		{
			if (texture != null) {
				texture.bind();
			}
			gl.glTranslatef(position.getX(), position.getY(), 0);
			gl.glTranslatef(width/2.0f, height/2.0f, 0);
			gl.glScalef(width, height/2.0f, 1);
			gl.glBegin(GL.GL_QUADS);
			{
				gl.glTexCoord2d(0, 1); gl.glVertex3f(-1, -1, -10);
				gl.glTexCoord2d(2, 1); gl.glVertex3f( 1, -1, -10);
				gl.glTexCoord2d(2, 0); gl.glVertex3f( 1,  1, -10);
				gl.glTexCoord2d(0, 0); gl.glVertex3f(-1,  1, -10);			
			}
			gl.glEnd();
		}	
		gl.glPopMatrix();
	}

	@Override
	public void update(float dt) {
		// Endlosscrolling
		if ((cam.getPosition().getX() - (position.getX() + width/2.0f)) > 0) {
			position = Vector2f.add(position, new Vector2f(width, 0));
		}
	}
}
