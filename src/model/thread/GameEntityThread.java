package model.thread;

import model.GameLoop;
import model.GameState;
import model.entity.GameEntity;
import utils.Consumer;

public abstract class GameEntityThread extends Thread implements Consumer<Double> {

	protected GameLoop gameLoop;
	protected GameState gameState;
	protected GameEntity entity;

	public GameEntityThread(GameEntity entity, GameState gameState) {
		this.entity = entity;
		this.gameState = gameState;
		gameLoop = new GameLoop(this);
	}

	@Override
	public void run() {
		gameLoop.start();
	}

	@Override
	public void accept(Double deltaTime) {
		handle(deltaTime);

		if (gameState.isGameOver()) {
			gameLoop.stop();
		}
	}

	protected abstract void handle(Double deltaTime);


}
