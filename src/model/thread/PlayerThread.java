package model.thread;

import model.GameLoop;
import model.GameState;
import model.entity.GameEntity;
import model.input.InputKeeper;
import model.input.KeyType;
import utils.Settings;
import utils.Vector;

import java.awt.*;

public class PlayerThread extends GameEntityThread {

	private Rectangle playerBounds;
	private InputKeeper inputKeeper;

	private Settings settings;

	public PlayerThread(GameEntity entity, GameState gameState, Rectangle playerBounds, InputKeeper inputKeeper) {
		super(entity, gameState);

		this.playerBounds = playerBounds;
		this.inputKeeper = inputKeeper;

		settings = Settings.getInstance();
	}

	@Override
	protected void handle(Double deltaTime) {
		entity.checkCollision();

		Vector directionVector = new Vector();

		if (inputKeeper.hasPressedKey(KeyType.UP) && entity.getPhysicalBody().getRectangle().y > playerBounds.y) {
			directionVector.addPolar(1, Math.PI * 3/2);
		}
		if (inputKeeper.hasPressedKey(KeyType.DOWN) &&
				entity.getPhysicalBody().getRectangle().y + entity.getPhysicalBody().getRectangle().height < playerBounds.y + playerBounds.height) {
			directionVector.addPolar(1, Math.PI * 1/2);
		}
		if (inputKeeper.hasPressedKey(KeyType.RIGHT) &&
				entity.getPhysicalBody().getRectangle().x + entity.getPhysicalBody().getRectangle().width < playerBounds.x + playerBounds.width) {
			directionVector.addPolar(1, 0);
		}
		if (inputKeeper.hasPressedKey(KeyType.LEFT) && entity.getPhysicalBody().getRectangle().x > playerBounds.x) {
			directionVector.addPolar(1, Math.PI);
		}

		entity.getVelocity().setPolar(settings.getInt("player.velocity"), directionVector.getAngle());

		entity.move(GameLoop.BOUND_TIME);

		if (entity.isDisappeared()) {
			gameState.setGameOver(true);
		}
	}
}
