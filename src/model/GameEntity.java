package model;

import model.listener.*;
import utils.Vector;

public abstract class GameEntity extends Colliding {

	protected ObjectData objectData;
	protected Vector velocity = new Vector();

	private ListenersList listeners;
	private ListenersList privateListeners = new ListenersList();

	public GameEntity(ObjectData objectData, CollisionBody collisionBody, ListenersList listeners) {
		super(collisionBody);
		this.listeners = listeners;
		setObjectData(objectData);
	}

	public GameEntity(ObjectData objectData, ListenersList listeners) {
		this(objectData, new CollisionBody(objectData.getRectangle()), listeners);
	}

	public ObjectData getObjectData() {
		return objectData;
	}

	public void setObjectData(ObjectData objectData) {
		this.objectData = objectData;

		notifyListeners(new EventData(getSenderType(), EventType.INITIALIZE, this));
	}

	public void addPrivateListener(Listener listener) {
		privateListeners.add(listener);
	}

	public Vector getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector velocity) {
		this.velocity = velocity;
	}

	protected abstract void doMovementAction(double time);

	public void move(double time) {
		doMovementAction(time);

		notifyListeners(new EventData(getSenderType(), EventType.MOVE, objectData));
	}

	public abstract SenderType getSenderType();

	private void notifyListeners(EventData eventData) {
		try {
			listeners.notify(eventData);
		} catch (NullPointerException e) {

		}

		try {
			privateListeners.notify(eventData);
		} catch (NullPointerException e) {

		}
	}

	public void disappear() {
		notifyListeners(new EventData(getSenderType(), EventType.DISAPPEARANCE));
	}

//	protected abstract boolean doCheckCollision(GameEntity anotherGameEntity);
//
//	protected abstract void doCollisionAction(GameEntity anotherGameEntity);
//
//	public boolean collide(GameEntity anotherGameEntity) {
//		boolean result = doCheckCollision(anotherGameEntity);
//
//		if (result) {
//			doCollisionAction(anotherGameEntity);
//		}
//
//		return result;
//	}

	@Override
	public boolean collidesWith(Colliding other) {
		return collisionBody.getRectangle().intersects(other.getCollisionBody().getRectangle());

	}

}
