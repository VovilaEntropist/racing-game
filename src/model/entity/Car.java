package model.entity;

import model.collision.Colliding;
import model.collision.CollisionBody;
import model.collision.CollisionManager;
import model.listener.ListenersList;
import model.listener.SenderType;

public class Car extends GameEntity {

	public Car(PhysicalBody physicalBody, CollisionBody collisionBody, ListenersList listeners, CollisionManager collisionManager) {
		super(physicalBody, collisionBody, listeners, collisionManager);
	}

	public Car(PhysicalBody physicalBody, ListenersList listeners, CollisionManager collisionManager) {
		this(physicalBody, new CollisionBody(physicalBody.getRectangle()), listeners, collisionManager);
	}

	@Override
	protected void doMovementAction(double time) {
		physicalBody.getRectangle().x += (int) (getVelocity().x * time);
		physicalBody.getRectangle().y += (int) (getVelocity().y * time);
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
