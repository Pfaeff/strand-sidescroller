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
	private int z;
	
	private Vector2f position;
	private Vector2f size;
	
	public Animation() {
		this.texture = null;
		this.duration = 0;
		this.numX = 1;
		this.numY = 1;
		this.loop = false;
		this.z = 0;
		
		position = new Vector2f();
		size = new Vector2f();
		
		reset();		
	}
	
	public Animation(Texture texture, int duration, int numX, int numY, boolean loop, int z) {
		this.texture = texture;
		this.duration = duration;
		if (numX < 1) {
			numX = 1;
		}
		if (numY < 1) {
			numY = 1;
		}
		this.numX = numX;
		this.numY = numY;
		this.loop = loop;
		this.z = z;
		
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
		if (time >= duration) {
			if (loop) {
				reset();
			} else {
				time = duration;
				posX = numX - 1;
				posY = numY - 1;
			}
		}
		int dp = (int)Math.floor(((float)time / (float)duration) * (numX * numY));
		if (dp > (numX * numY) - 1) {
			dp = numX * numY - 1;
		}
		posX = dp % numX;
		posY = dp / numX;
		if (posX >= numX) {
			posX = numX - 1;
		}
		if (posY >= numY) {
			posY = numY - 1;
		}
	}
	
	public void render(GL gl, boolean flipHorizontally, boolean flipVertically) {
		gl.glEnable(GL.GL_BLEND);
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA); 		
		gl.glPushMatrix();
		{
			if (texture != null) {
				texture.bind();
			}
			float lX = posX / (float)numX;
			float lY = posY / (float)numY;
			float rX = lX + (1 / (float)numX);
			float rY = lY + (1 / (float)numY);
			
			if (flipHorizontally) {
				float tmpX = lX;
				lX = rX;
				rX = tmpX;
			}
			
			if (flipVertically) {
				float tmpY = lY;
				lY = rY;
				rY = tmpY;
			}			
			
			gl.glTranslatef(position.getX(), position.getY(), 0);
			gl.glScalef(0.5f, 0.5f, 1);
			gl.glScalef(size.getX(), size.getY(), 1);
			gl.glBegin(GL.GL_QUADS);
			{
				gl.glTexCoord2d(lX, rY); gl.glVertex3f(-1, -1, z);
				gl.glTexCoord2d(rX, rY); gl.glVertex3f( 1, -1, z);
				gl.glTexCoord2d(rX, lY); gl.glVertex3f( 1,  1, z);
				gl.glTexCoord2d(lX, lY); gl.glVertex3f(-1,  1, z);			
			}
			gl.glEnd();
		}
		gl.glPopMatrix();
		gl.glDisable(GL.GL_BLEND);
	}
	
	public void setTexture(Texture tex) {
		texture = tex;
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
