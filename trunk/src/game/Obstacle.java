package game;

public abstract class Obstacle extends Entity implements ICollidable {

	public abstract boolean collidesWithEnsure(ICollidable e);

}
