package model;

import model.listener.EventData;
import model.listener.EventType;
import model.listener.ListenersList;
import model.listener.SenderType;
import utils.Settings;

public class PlayerCar extends Car {

	private int hitPoint;

	public PlayerCar(ObjectData objectData, CollisionBody collisionBody, CollisionManager collisionManager, ListenersList listeners) {
		super(objectData, collisionBody, collisionManager, listeners);
		hitPoint = Settings.getInstance().getInt("player.starting-lives");
	}

	public PlayerCar(ObjectData objectData, CollisionManager collisionManager, ListenersList listeners) {
		this(objectData, new CollisionBody(objectData.getRectangle()), collisionManager, listeners);
	}

	@Override
	protected void doCollisionResponse(Colliding colliding) {
		if (colliding == null) {
			return;
		}

		hitPoint--;
		notifyListeners(new EventData(SenderType.HP, EventType.UPDATE, new Integer(hitPoint)));
	}

	@Override
	public SenderType getSenderType() {
		return SenderType.PLAYER;
	}
}
