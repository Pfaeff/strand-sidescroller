package game;

import javax.media.opengl.GL;

import math.Rectangle;
import math.Vector2f;
import render.Animation;
import render.Renderer;
import render.TextureManager;

public class Crab extends Obstacle {
	private Animation idle;
	final static private Vector2f crabSize = new Vector2f(80, 80);
	
	public Crab() {
		width = crabSize.getX();
		height = crabSize.getY();
		idle = new Animation(TextureManager.crab_tex, 1000, 2, 1, true, -3);
		idle.setSize(crabSize);		
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
		return new Rectangle(Vector2f.sub(position, crabSize.scale(0.5f)), Vector2f.add(position, crabSize.scale(0.5f)));
	}

}
