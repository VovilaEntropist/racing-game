package model.handler;

import model.GameLoop;
import model.GameState;
import model.entity.GameEntity;
import model.input.InputKeeper;
import model.input.KeyType;
import utils.Settings;
import utils.Vector;

import java.awt.*;

public class PlayerHandler extends GameEntityHandler {

	private Rectangle playerBounds;
	private InputKeeper inputKeeper;

	private Settings settings;

	public PlayerHandler(GameEntity gameEntity, Rectangle playerBounds, InputKeeper inputKeeper, GameState gameState) {
		super(gameEntity, gameState);
		this.playerBounds = playerBounds;
		this.inputKeeper = inputKeeper;

		settings = Settings.getInstance();
	}

	@Override
	public void run(Double deltaTime) {
		gameEntity.checkCollision();

		Vector directionVector = new Vector();

		if (inputKeeper.hasPressedKey(KeyType.UP) && gameEntity.getObjectData().getRectangle().y > playerBounds.y) {
			directionVector.addPolar(1, Math.PI * 3/2);
		}
		if (inputKeeper.hasPressedKey(KeyType.DOWN) &&
				gameEntity.getObjectData().getRectangle().y + gameEntity.getObjectData().getRectangle().height < playerBounds.y + playerBounds.height) {
			directionVector.addPolar(1, Math.PI * 1/2);
		}
		if (inputKeeper.hasPressedKey(KeyType.RIGHT) &&
				gameEntity.getObjectData().getRectangle().x + gameEntity.getObjectData().getRectangle().width < playerBounds.x + playerBounds.width) {
			directionVector.addPolar(1, 0);
		}
		if (inputKeeper.hasPressedKey(KeyType.LEFT) && gameEntity.getObjectData().getRectangle().x > playerBounds.x) {
			directionVector.addPolar(1, Math.PI);
		}

		gameEntity.getVelocity().setPolar(settings.getInt("player.velocity"), directionVector.getAngle());

		gameEntity.move(GameLoop.BOUND_TIME);

		if (gameEntity.isDisappeared()) {
			gameState.setGameOver(true);
		}
	}
}
