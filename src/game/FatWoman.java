package game;

import java.util.Random;

import javax.media.opengl.GL;

import math.Rectangle;
import math.Vector2f;
import render.Animation;
import render.Renderer;
import render.TextureManager;

public class FatWoman extends Obstacle {
	private Animation idle;
	private int womanType;
	private boolean verticalFlip;
	private boolean horizontalFlip;
	final static private Vector2f fatWomanSize = new Vector2f(60, 120);
	
	public FatWoman() {
		// Zufälliges Design auswählen
		Random r = new Random();
		womanType = r.nextInt(2);
		verticalFlip = r.nextBoolean();
		horizontalFlip = r.nextBoolean();
		width = fatWomanSize.getX();
		height = fatWomanSize.getY();
		idle = new Animation(TextureManager.fatwoman_tex[womanType], 1500, 2, 1, true, -1);
		idle.setSize(fatWomanSize);
	}

	@Override
	public void draw(Renderer renderer, GL gl) {
		idle.render(gl, horizontalFlip, verticalFlip);		
	}

	@Override
	public void update(float dt) {
		idle.setPosition(position);
		idle.update(dt);
	}

	@Override
	public boolean collidesWith(ICollidable c) {
		return Rectangle.intersect(getRectangle(), c.getRectangle());
	}

	@Override
	public Rectangle getRectangle() {
		return new Rectangle(Vector2f.sub(position, fatWomanSize.scale(0.5f)), Vector2f.add(position, fatWomanSize.scale(0.5f)));
	}

}
