package render;

import javax.media.opengl.GL;

import com.sun.opengl.util.texture.Texture;

public class Background {
	private Texture texture;
	private int width;
	private int height;
	
	public Background(Texture texture, int width, int height) {
		this.texture = texture;
		this.width = width;
		this.height = height; 
	}
	
	public void render(GL gl) {
		gl.glPushMatrix();
		{
			if (texture != null) {
				texture.bind();
			}
			gl.glTranslatef(width/2, height/2, 0);
			gl.glScalef(0.5f, 0.5f, 1f);
			gl.glScalef(width, height, 1);
			gl.glBegin(GL.GL_QUADS);
			{
				gl.glTexCoord2d(0, 1); gl.glVertex3f(-1, -1, -10);
				gl.glTexCoord2d(1, 1); gl.glVertex3f( 1, -1, -10);
				gl.glTexCoord2d(1, 0); gl.glVertex3f( 1,  1, -10);
				gl.glTexCoord2d(0, 0); gl.glVertex3f(-1,  1, -10);			
			}
			gl.glEnd();
		}
		gl.glPopMatrix();		
	}
}
