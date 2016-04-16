package model.thread;

import model.GameLoop;
import model.GameState;
import model.action.ActionKeeper;
import model.action.ActionType;
import model.entity.GameEntity;
import utils.Settings;
import utils.Vector;

import java.awt.*;

public class PlayerThread extends GameEntityThread {

	private Rectangle playerBounds;
	private ActionKeeper actionKeeper;

	private Settings settings;

	public PlayerThread(GameEntity entity, GameState gameState, Rectangle playerBounds, ActionKeeper actionKeeper) {
		super(entity, gameState);

		this.playerBounds = playerBounds;
		this.actionKeeper = actionKeeper;

		settings = Settings.getInstance();
	}

	@Override
	protected void handle(Double deltaTime) {
		entity.checkCollision();

		Vector directionVector = new Vector();

		if (actionKeeper.hasAction(ActionType.RIDING_UP) && entity.getPhysicalBody().getRectangle().y > playerBounds.y) {
			directionVector.addPolar(1, Math.PI * 3/2);
		}
		if (actionKeeper.hasAction(ActionType.RIDING_DOWN) &&
				entity.getPhysicalBody().getRectangle().y + entity.getPhysicalBody().getRectangle().height < playerBounds.y + playerBounds.height) {
			directionVector.addPolar(1, Math.PI * 1/2);
		}
		if (actionKeeper.hasAction(ActionType.RIDING_RIGHT) &&
				entity.getPhysicalBody().getRectangle().x + entity.getPhysicalBody().getRectangle().width < playerBounds.x + playerBounds.width) {
			directionVector.addPolar(1, 0);
		}
		if (actionKeeper.hasAction(ActionType.RIDING_LEFT) && entity.getPhysicalBody().getRectangle().x > playerBounds.x) {
			directionVector.addPolar(1, Math.PI);
		}

		entity.getVelocity().setPolar(settings.getInt("player.velocity"), directionVector.getAngle());

		entity.move(GameLoop.BOUND_TIME);

		if (entity.isDisappeared()) {
			gameState.setGameOver(true);
		}
	}
}
