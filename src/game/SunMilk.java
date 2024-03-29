package game;

import javax.media.opengl.GL;

import math.Rectangle;
import math.Vector2f;

import render.Renderer;
import render.TextureManager;

public class SunMilk extends Entity implements ICollidable {	

	public SunMilk() {
		width = 25;
		height = 50;
	}

	@Override
	public void draw(Renderer renderer, GL gl) {
		gl.glEnable(GL.GL_BLEND);
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA); 		
		gl.glPushMatrix();
		{
			if (TextureManager.sunmilk_tex != null) {
				TextureManager.sunmilk_tex.bind();
			}
			gl.glTranslatef(position.getX(), position.getY(), 0);
			gl.glScalef(0.5f, 0.5f, 1f);
			gl.glScalef(width, height, 1);
			gl.glBegin(GL.GL_QUADS);
			{
				gl.glTexCoord2d(0, 1); gl.glVertex3f(-1, -1, -3);
				gl.glTexCoord2d(1, 1); gl.glVertex3f( 1, -1, -3);
				gl.glTexCoord2d(1, 0); gl.glVertex3f( 1,  1, -3);
				gl.glTexCoord2d(0, 0); gl.glVertex3f(-1,  1, -3);			
			}
			gl.glEnd();
		}
		gl.glPopMatrix();
		gl.glDisable(GL.GL_BLEND);		
	}

	@Override
	public void update(float dt) {
	}

	@Override
	public boolean collidesWith(ICollidable c) {
		return Rectangle.intersect(getRectangle(), c.getRectangle());
	}

	@Override
	public Rectangle getRectangle() {
		return new Rectangle(Vector2f.sub(position, new Vector2f(width/2, height/2)), Vector2f.add(position, new Vector2f(width/2, height/2)));
	}
}
