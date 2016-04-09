package model.entity;

import model.collision.CollisionBody;
import model.collision.CollisionManager;
import model.listener.ListenersList;
import model.listener.SenderType;

public class Life extends Car {

	public Life(PhysicalBody physicalBody, CollisionBody collisionBody, ListenersList listeners, CollisionManager collisionManager) {
		super(physicalBody, collisionBody, listeners, collisionManager);
	}

	public Life(PhysicalBody physicalBody, ListenersList listeners, CollisionManager collisionManager) {
		this(physicalBody, new CollisionBody(physicalBody.getRectangle()), listeners, collisionManager);
	}

	@Override
	public SenderType getSenderType() {
		return SenderType.LIFE;
	}

}
