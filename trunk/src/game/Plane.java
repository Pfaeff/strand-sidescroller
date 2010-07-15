package game;

import javax.media.opengl.GL;

import math.Vector2f;

import render.Animation;
import render.Renderer;
import render.TextureManager;

public class Plane extends Entity {
	private Animation idle;
	final static private float speed = 100;
	final static private Vector2f planeSize = new Vector2f(240, 60);	
	
	public Plane() {
		width = planeSize.getX();
		height = planeSize.getY();
		idle = new Animation(TextureManager.plane_tex, 1000, 1, 2, true, -1);
		idle.setSize(planeSize);	
		velocity = new Vector2f(-1, 0).scale(speed);
	}

	@Override
	public void draw(Renderer renderer, GL gl) {
		idle.render(gl, false, false);
	}

	@Override
	public void update(float dt) {
		position = Vector2f.add(position, velocity.scale(dt));
		idle.setPosition(position);
		idle.update(dt);	
	}

}
