package model;

import model.listener.ListenersList;
import utils.Consumer;

public class GameEntityHandler implements Consumer<Double> {

	private GameEntity gameEntity;
	private ListenersList listenersList;
	private CollisionManager collisionManager;

	public GameEntityHandler(GameEntity gameEntity, ListenersList listenersList, CollisionManager collisionManager) {
		this.gameEntity = gameEntity;
		this.listenersList = listenersList;
		this.collisionManager = collisionManager;
	}

	@Override
	public void run(Double deltaTime) {
		gameEntity.move(deltaTime);
	}
}
