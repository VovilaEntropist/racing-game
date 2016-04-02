package model;

public abstract class Colliding {

	protected CollisionBody collisionBody;

	public Colliding(CollisionBody collisionBody) {
		this.collisionBody = collisionBody;
	}

	public CollisionBody getCollisionBody() {
		return collisionBody;
	}

	public void setCollisionBody(CollisionBody collisionBody) {
		this.collisionBody = collisionBody;
	}

	public abstract boolean collidesWith(Colliding other);

	public abstract void respondToCollision(Colliding other);
}
