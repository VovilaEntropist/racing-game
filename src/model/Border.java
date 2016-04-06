package model;

public class Border extends Colliding {

	public Border(CollisionBody collisionBody) {
		super(collisionBody);
	}

	@Override
	public boolean collidesWith(Colliding other) {
		return false;
	}

	@Override
	public void respondToCollision(Colliding other) {

	}
}
