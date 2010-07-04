package render;

import javax.media.opengl.GL;

import com.sun.opengl.util.texture.Texture;

public class Animation {
	private Texture texture;
	private int duration;
	private int numX;
	private int numY;
	private int posX;
	private int posY;
	private boolean loop;
	private float position;
	
	public Animation(Texture texture, int duration, int numX, int numY, boolean loop) {
		this.texture = texture;
		this.duration = duration;
		this.numX = numX;
		this.numY = numY;
		this.loop = loop;
		reset();
	}
	
	public void reset() {
		position = 0;
		posX = 0;
		posY = 0;		
	}
	
	public void update(float dt) {
		position += 1000 * dt;
		if ((position >= duration) && (loop)) {
			reset();
		} else {
			float dp = duration / position;
			posX = (int)Math.ceil(dp / numX);		
			posY = (int)Math.ceil(dp / numY);
		}		
	}
	
	public void render(GL gl) {
		texture.bind();
		int tX = numX / posX;
		int tY = numY / posY;		
		gl.glBegin(GL.GL_QUADS);
		{
			gl.glScalef(0.5f, 0.5f, 1f);
			gl.glTexCoord2d( 0, tY); gl.glVertex3f(-1, -1, 0);  
			gl.glTexCoord2d(tX, tY); gl.glVertex3f( 1, -1, 0);
			gl.glTexCoord2d(tX,  0); gl.glVertex3f( 1,  1, 0);
			gl.glTexCoord2d( 0,  0); gl.glVertex3f(-1,  1, 0);			
		}
		gl.glEnd();
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getDuration() {
		return duration;
	}

	public void setLoop(boolean loop) {
		this.loop = loop;
	}

	public boolean isLoop() {
		return loop;
	}
}
