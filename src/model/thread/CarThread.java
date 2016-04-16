package model.thread;

import model.GameState;
import model.Score;
import model.entity.Car;
import model.entity.GameEntity;
import utils.Settings;

public class CarThread extends GameEntityThread {

	private Score score;

	public CarThread(GameEntity entity, GameState gameState, Score score) {
		super(entity, gameState);
		this.score = score;
	}

	@Override
	protected void handle(Double deltaTime) {
		Car entity = (Car) this.entity;

		entity.move(deltaTime);
		entity.checkCollision();
		if (entity.isDisappeared()) {
			if (!entity.isCrashed()) {
				int step = Settings.getInstance().getInt("score.increment-per-car");
				this.score.increase(step);
			}
			gameLoop.stop();
		}
	}
}
