package render;

import javax.media.opengl.GL;

import com.sun.opengl.util.texture.Texture;

public class Animation {
	private Texture texture;
	private int duration;
	private int numX;
	private int numY;
	private boolean loop;
	private float position;
	
	public Animation(Texture texture, int duration, int numX, int numY, boolean loop) {
		this.texture = texture;
		this.duration = duration;
		this.numX = numX;
		this.numY = numY;
		this.loop = loop;
		position = 0;
	}
	
	public void reset() {
		position = 0;
	}
	
	public void update(float dt) {
		
	}
	
	public void render(GL gl) {
		texture.bind();
		gl.glBegin(GL.GL_QUADS);
		{
			
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
