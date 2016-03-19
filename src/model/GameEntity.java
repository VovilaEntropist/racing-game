package model;

import model.listener.*;
import utils.Vector;

public abstract class GameEntity {

	protected PhysicalBody body;
	protected Vector velocity = new Vector();

	private ListenersList listeners;
	private ListenersList privateListeners = new ListenersList();

	public GameEntity(PhysicalBody body, ListenersList listeners) {
		this.listeners = listeners;

		setBody(body);
	}

	public PhysicalBody getBody() {
		return body;
	}

	public void setBody(PhysicalBody body) {
		this.body = body;

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

		notifyListeners(new EventData(getSenderType(), EventType.MOVE, body));
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

	protected abstract boolean doCheckCollision(GameEntity anotherGameEntity);

	protected abstract void doCollisionAction(GameEntity anotherGameEntity);

	public boolean collide(GameEntity anotherGameEntity) {
		boolean result = doCheckCollision(anotherGameEntity);

		if (result) {
			doCollisionAction(anotherGameEntity);
		}

		return result;
	}
}
