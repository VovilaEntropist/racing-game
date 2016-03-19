package model;

import model.listener.ListenersList;
import model.listener.SenderType;

public class Life extends GameEntity {

	public Life(PhysicalBody body, ListenersList listeners) {
		super(body, listeners);
	}

	@Override
	protected void doMovementAction(double time) {
		body.getRectangle().x += (int) (getVelocity().x * time);
		body.getRectangle().y += (int) (getVelocity().y * time);
	}

	@Override
	public SenderType getSenderType() {
		return SenderType.LIFE;
	}

	@Override
	protected boolean doCheckCollision(GameEntity anotherGameEntity) {
		return this.getBody().getRectangle().intersects(anotherGameEntity.getBody().getRectangle());
	}

	@Override
	protected void doCollisionAction(GameEntity anotherGameEntity) {

	}


}
