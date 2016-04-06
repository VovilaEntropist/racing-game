package model;

import model.listener.ListenersList;
import model.listener.SenderType;

class Car extends GameEntity {

	public Car(ObjectData objectData, CollisionBody collisionBody, ListenersList listeners, CollisionManager collisionManager) {
		super(objectData, collisionBody, listeners, collisionManager);
	}

	public Car(ObjectData objectData, ListenersList listeners, CollisionManager collisionManager) {
		this(objectData, new CollisionBody(objectData.getRectangle()), listeners, collisionManager);
	}

	@Override
	protected void doMovementAction(double time) {
		objectData.getRectangle().x += (int) (getVelocity().x * time);
		objectData.getRectangle().y += (int) (getVelocity().y * time);
	}

	@Override
	protected void doCollisionResponse(Colliding colliding) {
		if (colliding != null) {
			collisionManager.remove(this);
			disappear();
		}
	}

	@Override
	public SenderType getSenderType() {
		return SenderType.CAR;
	}

}
