package model;

import model.listener.ListenersList;
import model.listener.SenderType;

public class PlayerCar extends Car {

	public PlayerCar(PhysicalBody body, ListenersList listeners) {
		super(body, listeners);
	}

	@Override
	public SenderType getSenderType() {
		return SenderType.PLAYER;
	}
}
