package model;

import model.listener.EventData;
import model.listener.EventType;
import model.listener.ListenersList;
import model.listener.SenderType;
import utils.Settings;

public class PlayerCar extends Car {

	private int hitPoint;

	public PlayerCar(ObjectData objectData, CollisionBody collisionBody, ListenersList listeners, CollisionManager collisionManager) {
		super(objectData, collisionBody, listeners, collisionManager);
		hitPoint = Settings.getInstance().getInt("player.starting-lives");
		notifyListeners(new EventData(SenderType.HP, EventType.UPDATE, new Integer(hitPoint)));
	}

	public PlayerCar(ObjectData objectData, ListenersList listeners, CollisionManager collisionManager) {
		this(objectData, new CollisionBody(objectData.getRectangle()), listeners, collisionManager);
	}

	@Override
	protected void doCollisionResponse(Colliding colliding) {
		if (colliding instanceof Life) {
			hitPoint++;
		} else if (colliding instanceof Car) {
			hitPoint--;
		}

		if (hitPoint < 0) {
			disappear();
		}
		notifyListeners(new EventData(SenderType.HP, EventType.UPDATE, new Integer(hitPoint)));
	}

	@Override
	public SenderType getSenderType() {
		return SenderType.PLAYER;
	}
}
