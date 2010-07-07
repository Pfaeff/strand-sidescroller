package game;

import math.Rectangle;

public interface ICollidable {
	public boolean collidesWith(ICollidable c);
	public Rectangle getRectangle();
}
