package model.entity;

import model.Health;
import model.collision.Colliding;
import model.collision.CollisionBody;
import model.collision.CollisionManager;
import model.listener.EventData;
import model.listener.EventType;
import model.listener.ListenersList;
import model.listener.SenderType;
import utils.Settings;

public class PlayerCar extends Car {

	private Health health;

	public PlayerCar(PhysicalBody physicalBody, CollisionBody collisionBody, ListenersList listeners, CollisionManager collisionManager) {
		super(physicalBody, collisionBody, listeners, collisionManager);
		health = new Health(Settings.getInstance().getInt("player.starting-lives"));
		notifyListeners(new EventData(SenderType.HP, EventType.UPDATE, health.getPoints()));
	}

	public PlayerCar(PhysicalBody physicalBody, ListenersList listeners, CollisionManager collisionManager) {
		this(physicalBody, new CollisionBody(physicalBody.getRectangle()), listeners, collisionManager);
	}

	@Override
	protected void doCollisionResponse(Colliding colliding) {
		if (colliding instanceof Life) {
			health.increase();
		} else if (colliding instanceof Car) {
			health.decrease();
		}

		if (health.isZero()) {
			disappear();
		}
		notifyListeners(new EventData(SenderType.HP, EventType.UPDATE, health.getPoints()));
	}

	@Override
	public SenderType getSenderType() {
		return SenderType.PLAYER;
	}
}
