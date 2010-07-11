package game;

import javax.media.opengl.GL;

import math.Vector2f;

import render.Renderer;
import render.TextureManager;

public class LifeGauge extends Entity {	
	private float currentValue;
	private boolean depleted;
	final static private float maxValue = 1000;
	final static private float speed = 50;
	final static private float fillAmount = 100;
	final static private float width = 100;
	final static private float height = 25;
	
	public LifeGauge() {
		reset();
		position = new Vector2f(75, 450);
	}
	
	public void reset() {
		currentValue = maxValue;
		depleted = false;
	}

	@Override
	public void draw(Renderer renderer, GL gl) {
		gl.glEnable(GL.GL_ALPHA_TEST);
		gl.glAlphaFunc(GL.GL_GREATER, 0.1f);		
		gl.glPushMatrix();
		{
			gl.glLoadIdentity();
			gl.glDisable(GL.GL_DEPTH_TEST);
			gl.glTranslatef(position.getX(), position.getY(), 0);
			gl.glScalef(0.5f, 0.5f, 1f);
			gl.glScalef(width, height, 1);	
			
			if (TextureManager.life_empty_tex != null) {
				TextureManager.life_empty_tex.bind();
			}
			gl.glBegin(GL.GL_QUADS);
			{
				gl.glTexCoord2d(0, 1); gl.glVertex3f(-1, -1, 0);
				gl.glTexCoord2d(1, 1); gl.glVertex3f( 1, -1, 0);
				gl.glTexCoord2d(1, 0); gl.glVertex3f( 1,  1, 0);
				gl.glTexCoord2d(0, 0); gl.glVertex3f(-1,  1, 0);			
			}
			gl.glEnd();			
			
			if (TextureManager.life_full_tex != null) {
				TextureManager.life_full_tex.bind();
			}
			float r = (currentValue / maxValue);
			if (r <= 0) {
				r = 0;
			}
			gl.glTranslatef(-1, 0, 0);
			gl.glBegin(GL.GL_QUADS);
			{
				gl.glTexCoord2d(0, 1); gl.glVertex3f(0, -1, 0);
				gl.glTexCoord2d(r, 1); gl.glVertex3f(2*r, -1, 0);
				gl.glTexCoord2d(r, 0); gl.glVertex3f(2*r,  1, 0);
				gl.glTexCoord2d(0, 0); gl.glVertex3f(0,  1, 0);			
			}
			gl.glEnd();
			
			gl.glEnable(GL.GL_DEPTH_TEST);
		}
		gl.glPopMatrix();
		gl.glDisable(GL.GL_ALPHA_TEST);		
	}

	@Override
	public void update(float dt) {
		currentValue -= speed * dt;
		if (currentValue <= 0) {
			depleted = true;
		}
	}
	
	public void fill() {
		currentValue += fillAmount;
		currentValue = Math.min(currentValue, maxValue);
	}

	public boolean hasDepleted() {
		return depleted;
	}
	
}
