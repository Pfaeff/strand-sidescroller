package render;

import javax.media.opengl.GL;

import math.Vector2f;

import com.sun.opengl.util.texture.Texture;

public class Animation {
	private Texture texture;
	private int duration;
	private int numX;
	private int numY;
	private int posX;
	private int posY;
	private boolean loop;
	private float time;
	
	private Vector2f position;
	private Vector2f size;
	
	public Animation(Texture texture, int duration, int numX, int numY, boolean loop) {
		this.texture = texture;
		this.duration = duration;
		this.numX = numX;
		this.numY = numY;
		this.loop = loop;
		
		position = new Vector2f();
		size = new Vector2f();
		
		reset();
	}
	
	public void reset() {
		time = 0;
		posX = 0;
		posY = 0;		
	}
	
	public void update(float dt) {
		time += 1000 * dt;
		if ((time >= duration) && (loop)) {
			reset();
		} else {
			float dp = duration / time;
			posX = (int)Math.ceil(dp / numX);		
			posY = (int)Math.ceil(dp / numY);
		}		
	}
	
	public void render(GL gl) {
		gl.glPushMatrix();
		{
			texture.bind();
			int lX = numX / posX;
			int lY = numY / posY;
			int rX = lX + (1 / numX);
			int rY = lY + (1 / numY);
			gl.glTranslatef(-position.getX(), -position.getY(), 0);
			gl.glScalef(0.5f, 0.5f, 1f);
			gl.glScalef(size.getX(), size.getY(), 1);
			gl.glBegin(GL.GL_QUADS);
			{
				gl.glTexCoord2d(lX, rY); gl.glVertex3f(-1, -1, 0);
				gl.glTexCoord2d(rX, rY); gl.glVertex3f(1, -1, 0);
				gl.glTexCoord2d(rX, lY); gl.glVertex3f(1, 1, 0);
				gl.glTexCoord2d(lX, lY); gl.glVertex3f(-1, 1, 0);
			}
			gl.glEnd();
		}
		gl.glPopMatrix();
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

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setSize(Vector2f size) {
		this.size = size;
	}

	public Vector2f getSize() {
		return size;
	}
}
