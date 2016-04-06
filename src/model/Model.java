package model;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import model.listener.*;
import utils.Consumer;
import utils.Settings;
import utils.Vector;

public class Model {

	private ListenersList listeners;
	private InputKeeper inputKeeper;
	private CollisionManager collisionManager;

	private GameState gameState;

	private RoadTraffic roadTraffic;
	private Road road;
	private Colliding border;
	private Colliding endGameBorder;

	private Settings settings;

	public Model() {
		gameState = new GameState();
		listeners = new ListenersList();
		inputKeeper = new InputKeeper();
		settings = Settings.getInstance();
	}

	public InputKeeper getInputKeeper() {
		return inputKeeper;
	}

	private void initNewGame() {
		final int roadWidth = settings.getInt("road.width");
		final int roadHeight = settings.getInt("road.height");
		final int playerBoundsHeight = settings.getInt("player-bounds.height");
		final int playerInitialX = settings.getInt("player.initial-x");
		final int playerInitialY = settings.getInt("player.initial-y");
		final int playerWidth = settings.getInt("player.width");
		final int playerHeight = settings.getInt("player.height");

		gameState.setGameOver(false);

		collisionManager = new CollisionManager();
		road = new Road(new ObjectData(new Point(0, 0), new Dimension(roadWidth, roadHeight)),
				settings.getInt("road.lane-count"), listeners);
		border = new Border(new CollisionBody(new Rectangle(0, roadHeight, roadWidth, 10)));
		collisionManager.add(border);
		endGameBorder = new Border(new CollisionBody(new Rectangle(0, 0, roadWidth, roadHeight)));
		roadTraffic = new RoadTraffic(road, collisionManager, listeners, gameState);

		GameEntity player = new PlayerCar(new ObjectData(new Point(playerInitialX, playerInitialY),
				new Dimension(playerWidth, playerHeight)), listeners, collisionManager);
		collisionManager.add(player);
		Rectangle playerBounds = new Rectangle(0, roadHeight - playerBoundsHeight, roadWidth, playerBoundsHeight);
		PlayerHandler playerHandler = new PlayerHandler(player, playerBounds, inputKeeper, gameState);
		new GameLoop(playerHandler, gameState).start();
	}

	public void start() {
		initNewGame();

		while(!gameState.isGameOver()) {
			roadTraffic.generate();

			gameState.getScore().increase(settings.getInt("score.increment-per-time"));
			listeners.notify(new EventData(SenderType.SCORE, EventType.UPDATE,
					new Integer(gameState.getScore().getPoints())));

			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if (gameState.isGameOver()) {
			listeners.notify(new EventData(SenderType.GAME, EventType.GAME_OVER, new Integer(gameState.getScore().getPoints())));
		}
		gameState.getScore().increase(settings.getInt("score.increment-per-time"));
		listeners.notify(new EventData(SenderType.SCORE, EventType.UPDATE,
				new Integer(gameState.getScore().getPoints())));

	}

	public void addListener(Listener listener) {
		this.listeners.add(listener);
	}

	public void addGameEntityPrivateListener(GameEntity gameEntity, Listener listener) {
		gameEntity.addPrivateListener(listener);
	}
}
