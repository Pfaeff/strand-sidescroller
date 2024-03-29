package game;

import javax.media.opengl.GL;

import math.Rectangle;
import math.Vector2f;

import render.Animation;
import render.Renderer;
import render.TextureManager;

public class Shower extends Obstacle {
	private Animation idle;
	final static private Vector2f showerSize = new Vector2f(80, 160);
	
	public Shower() {
		width = showerSize.getX();
		height = showerSize.getY();
		idle = new Animation(TextureManager.shower_tex, 1000, 2, 1, true, -1);
		idle.setSize(showerSize);				
	}

	@Override
	public void draw(Renderer renderer, GL gl) {
		idle.render(gl, false, false);		
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
	
	public boolean collidesWithEnsure(ICollidable c) {
		Rectangle rect = getRectangle();
		Rectangle ensurance = rect.expand(Player.playerSize.scale(1.1f));
		return Rectangle.intersect(ensurance, c.getRectangle());
	}

	@Override
	public Rectangle getRectangle() {
		return new Rectangle(Vector2f.sub(position, showerSize.scale(0.5f)), Vector2f.add(position, showerSize.scale(0.5f)));
	}

}
