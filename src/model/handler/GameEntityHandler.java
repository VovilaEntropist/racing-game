package model.handler;

import model.GameState;
import model.entity.GameEntity;
import utils.Consumer;
import utils.Settings;

public class GameEntityHandler implements Consumer<Double> {

	protected GameEntity gameEntity;
	protected GameState gameState;

	public GameEntityHandler(GameEntity gameEntity, GameState gameState) {
		this.gameEntity = gameEntity;
		this.gameState = gameState;
	}

	@Override
	public void run(Double deltaTime) {
		gameEntity.move(deltaTime);
		gameEntity.checkCollision();
		if (gameEntity.isDisappeared()) {
			int step = Settings.getInstance().getInt("score.increment-per-car");
			this.gameState.getScore().increase(step);
			Thread.currentThread().stop();
		}
	}
}
