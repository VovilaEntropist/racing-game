package model.entity;

import model.collision.Colliding;
import model.collision.CollisionBody;
import model.collision.CollisionManager;
import model.listener.*;
import utils.Vector;

public abstract class GameEntity extends Colliding {

	protected PhysicalBody physicalBody;
	protected Vector velocity = new Vector();

	private ListenersList listeners;
	private ListenersList privateListeners = new ListenersList();

	protected CollisionManager collisionManager;

	private boolean disappeared;

	public GameEntity(PhysicalBody physicalBody, CollisionBody collisionBody, ListenersList listeners, CollisionManager collisionManager) {
		super(collisionBody);
		this.listeners = listeners;
		this.collisionManager = collisionManager;
		setPhysicalBody(physicalBody);

		disappeared = false;
	}

	public GameEntity(PhysicalBody physicalBody, ListenersList listeners, CollisionManager collisionManager) {
		this(physicalBody, new CollisionBody(physicalBody.getRectangle()), listeners, collisionManager);
	}

	public PhysicalBody getPhysicalBody() {
		return physicalBody;

	}

	public void setPhysicalBody(PhysicalBody physicalBody) {
		this.physicalBody = physicalBody;

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

		notifyListeners(new EventData(getSenderType(), EventType.MOVE, physicalBody));
	}

	public abstract SenderType getSenderType();

	protected void notifyListeners(EventData eventData) {
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
		disappeared = true;
		notifyListeners(new EventData(getSenderType(), EventType.DISAPPEARANCE));
	}

	public boolean isDisappeared() {
		return disappeared;
	}

	public boolean checkCollision() {
		return collisionManager.checkCollisionFor(this);
	}

	@Override
	public boolean collidesWith(Colliding other) {
		return collisionBody.getRectangle().intersects(other.getCollisionBody().getRectangle());
	}

	@Override
	public void respondToCollision(Colliding other) {
		doCollisionResponse(other);
	}

	protected abstract void doCollisionResponse(Colliding colliding);

}
