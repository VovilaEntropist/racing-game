package model.thread;

import model.GameLoop;
import model.GameState;
import model.Score;
import model.entity.GameEntity;
import model.input.InputKeeper;
import model.input.KeyType;
import utils.Consumer;
import utils.Settings;
import utils.Vector;

import java.awt.*;

public class CarThread extends GameEntityThread {

	private Score score;

	public CarThread(GameEntity entity, GameState gameState, Score score) {
		super(entity, gameState);
		this.score = score;
	}

	@Override
	protected void handle(Double deltaTime) {
		entity.move(deltaTime);
		entity.checkCollision();
		if (entity.isDisappeared()) {
            int step = Settings.getInstance().getInt("score.increment-per-car");
            this.score.increase(step);
			gameLoop.stop();
		}
	}
}
