package model.entity;

import model.ObjectData;
import model.collision.CollisionBody;
import model.collision.CollisionManager;
import model.listener.ListenersList;
import model.listener.SenderType;

public class Life extends Car {

	public Life(ObjectData objectData, CollisionBody collisionBody, ListenersList listeners, CollisionManager collisionManager) {
		super(objectData, collisionBody, listeners, collisionManager);
	}

	public Life(ObjectData objectData, ListenersList listeners, CollisionManager collisionManager) {
		this(objectData, new CollisionBody(objectData.getRectangle()), listeners, collisionManager);
	}

	@Override
	public SenderType getSenderType() {
		return SenderType.LIFE;
	}

}