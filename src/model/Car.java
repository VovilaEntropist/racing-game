package model;

import model.listener.ListenersList;
import model.listener.SenderType;

class Car extends GameEntity {

	public Car(ObjectData objectData, CollisionBody collisionBody, CollisionManager collisionManager, ListenersList listeners) {
		super(objectData, collisionBody, collisionManager, listeners);
	}

	public Car(ObjectData objectData, CollisionManager collisionManager, ListenersList listeners) {
		this(objectData, new CollisionBody(objectData.getRectangle()), collisionManager, listeners);
	}

	@Override
	protected void doMovementAction(double time) {
		objectData.getRectangle().x += (int) (getVelocity().x * time);
		objectData.getRectangle().y += (int) (getVelocity().y * time);
	}

	@Override
	protected void doCollisionResponse(Colliding colliding) {

	}

	@Override
	public SenderType getSenderType() {
		return SenderType.CAR;
	}

}
