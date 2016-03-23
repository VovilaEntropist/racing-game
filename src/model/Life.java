package model;

import model.listener.ListenersList;
import model.listener.SenderType;

public class Life extends GameEntity {

	public Life(ObjectData body, ListenersList listeners) {
		super(body, listeners);
	}

	@Override
	protected void doMovementAction(double time) {
		objectData.getRectangle().x += (int) (getVelocity().x * time);
		objectData.getRectangle().y += (int) (getVelocity().y * time);
	}

	@Override
	public SenderType getSenderType() {
		return SenderType.LIFE;
	}




}
