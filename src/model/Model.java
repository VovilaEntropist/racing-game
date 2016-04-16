package model;

import java.awt.*;

import model.action.ActionKeeper;
import model.action.ActionType;
import model.collision.Colliding;
import model.collision.CollisionBody;
import model.collision.CollisionManager;
import model.entity.GameEntity;
import model.entity.PhysicalBody;
import model.entity.PlayerCar;
import model.thread.PlayerThread;
import model.listener.*;
import utils.Settings;

public class Model {

	private ListenersList listeners;
	private CollisionManager collisionManager;

	private ActionKeeper actionKeeper;

	private Score score;
	private GameState gameState;

	private RoadTraffic roadTraffic;
	private Road road;
	private Colliding border;

	private Settings settings;

	public Model() {
		gameState = new GameState();
		score = new Score();
		listeners = new ListenersList();
		actionKeeper = new ActionKeeper();
		settings = Settings.getInstance();
	}

	private void initNewGame() {
		final int roadWidth = settings.getInt("road.width");
		final int roadHeight = settings.getInt("road.height");
		final int playerBoundsHeight = settings.getInt("player-bounds.height");
		final int playerInitialX = settings.getInt("player.initial-x");
		final int playerInitialY = settings.getInt("player.initial-y");
		final int playerWidth = settings.getInt("player.width");
		final int playerHeight = settings.getInt("player.height");

		collisionManager = new CollisionManager();
		road = new Road(new PhysicalBody(new Point(0, 0), new Dimension(roadWidth, roadHeight)),
				settings.getInt("road.lane-count"), listeners);
		border = new Border(new CollisionBody(new Rectangle(0, roadHeight+150, roadWidth, 10)));
		collisionManager.add(border);
		roadTraffic = new RoadTraffic(road, collisionManager, listeners, gameState, score);

		GameEntity player = new PlayerCar(new PhysicalBody(new Point(playerInitialX, playerInitialY),
				new Dimension(playerWidth, playerHeight)), listeners, collisionManager);
		collisionManager.add(player);
		Rectangle playerBounds = new Rectangle(0, roadHeight - playerBoundsHeight, roadWidth, playerBoundsHeight);
		PlayerThread playerThread = new PlayerThread(player, gameState, playerBounds, actionKeeper);
		playerThread.start();
	}

	public void start() {
		initNewGame();

		while(!gameState.isGameOver()) {
			roadTraffic.generate();

			listeners.notify(new EventData(SenderType.SCORE, EventType.UPDATE, score.getPoints()));

			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if (gameState.isGameOver()) {
			listeners.notify(new EventData(SenderType.SCORE, EventType.UPDATE, score.getPoints()));
			listeners.notify(new EventData(SenderType.GAME, EventType.GAME_OVER, score.getPoints()));
		}

	}

	public void addListener(Listener listener) {
		this.listeners.add(listener);
	}

	public void addGameEntityPrivateListener(GameEntity gameEntity, Listener listener) {
		gameEntity.addPrivateListener(listener);
	}

	public void addActionUp() {
		actionKeeper.addAction(ActionType.RIDING_UP);
	}

	public void addActionDown() {
		actionKeeper.addAction(ActionType.RIDING_DOWN);
	}

	public void addActionRight() {
		actionKeeper.addAction(ActionType.RIDING_RIGHT);
	}

	public void addActionLeft() {
		actionKeeper.addAction(ActionType.RIDING_LEFT);
	}

	public void removeActionUp() {
		actionKeeper.removeAction(ActionType.RIDING_UP);
	}

	public void removeActionDown() {
		actionKeeper.removeAction(ActionType.RIDING_DOWN);
	}

	public void removeActionRight() {
		actionKeeper.removeAction(ActionType.RIDING_RIGHT);
	}

	public void removeActionLeft() {
		actionKeeper.removeAction(ActionType.RIDING_LEFT);
	}
}
