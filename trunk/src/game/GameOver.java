package game;

import java.util.Random;

import javax.media.opengl.GL;

import math.Vector2f;
import render.Renderer;
import render.TextureManager;
import sound.AudioManager;

public class GameOver {	
	private int goType;
	private float transparency;
	final static private Vector2f goSize = new Vector2f(1000, 500);	
	
	public GameOver() {
		Random r = new Random();
		goType = r.nextInt(4);
		if (goType == 1) {
			AudioManager.playSound(AudioManager.fatality);
		} else {
			AudioManager.playSound(AudioManager.scream);
		}
		transparency = 0;
	}

	public void draw(Renderer renderer, GL gl) {
		gl.glColor4f(1.0f, 1.0f, 1.0f, transparency);
		gl.glEnable(GL.GL_BLEND);
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA); 			
		gl.glPushMatrix();
		{
			gl.glLoadIdentity();
			gl.glDisable(GL.GL_DEPTH_TEST);
			gl.glTranslatef(goSize.getX() /2.0f, goSize.getY() / 2.0f, 0);
			gl.glScalef(0.5f, 0.5f, 1f);
			gl.glScalef(goSize.getX(), goSize.getY(), 1);	
			
			if (TextureManager.go_tex[goType] != null) {
				TextureManager.go_tex[goType].bind();
			}
			gl.glBegin(GL.GL_QUADS);
			{
				gl.glTexCoord2d(0, 1); gl.glVertex3f(-1, -1, 0);
				gl.glTexCoord2d(1, 1); gl.glVertex3f( 1, -1, 0);
				gl.glTexCoord2d(1, 0); gl.glVertex3f( 1,  1, 0);
				gl.glTexCoord2d(0, 0); gl.glVertex3f(-1,  1, 0);			
			}
			gl.glEnd();						
			gl.glEnable(GL.GL_DEPTH_TEST);
		}
		gl.glPopMatrix();	
		gl.glDisable(GL.GL_BLEND);		
		gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
	}

	public void update(float dt) {
		transparency += dt / 2;
		transparency = Math.min(1, transparency);
	}

}
