package game;

import javax.media.opengl.GL;

import math.Rectangle;
import math.Vector2f;
import render.Animation;
import render.Renderer;
import render.TextureManager;

public class Radio extends Obstacle {
	private Animation idle;
	final static private Vector2f radioSize = new Vector2f(80, 80);
	
	public Radio() {
		width = radioSize.getX();
		height = radioSize.getY();
		idle = new Animation(TextureManager.radio_tex, 1000, 4, 1, true, -3);
		idle.setSize(radioSize);		
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
		return new Rectangle(Vector2f.sub(position, radioSize.scale(0.5f)), Vector2f.add(position, radioSize.scale(0.5f)));
	}

}
