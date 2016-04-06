package model;

import model.listener.ListenersList;
import utils.Consumer;

public class GameEntityHandler implements Consumer<Double> {

	protected GameEntity gameEntity;

	public GameEntityHandler(GameEntity gameEntity) {
		this.gameEntity = gameEntity;
	}

	@Override
	public void run(Double deltaTime) {
		gameEntity.move(deltaTime);
		gameEntity.checkCollision();
		if (gameEntity.isDisappeared()) {
			Thread.currentThread().stop();
		}
	}
}
