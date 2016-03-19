package model;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import model.listener.*;
import utils.Settings;
import utils.Vector;

public class Model implements Runnable {

	private ListenersList listeners;
	private InputKeeper inputKeeper;
	private CollisionManager collisionManager;

	private GameEntity player;

	private Score score;
	private int hitPoint;

	private RoadTraffic roadTraffic;
	private Road road;
	private Set<GameEntity> entities;

	private Settings settings;

	private Rectangle playerBounds;

	private GameLoop gameloop;

	public Model() {
		listeners = new ListenersList();
		inputKeeper = new InputKeeper();
		settings = Settings.getInstance();
		gameloop = new GameLoop(this);
	}

	public InputKeeper getInputKeeper() {
		return inputKeeper;
	}

	private void initialNewGame() {
		final int roadWidth = settings.getInt("road.width");
		final int roadHeight = settings.getInt("road.height");
		final int playerBoundsHeight = settings.getInt("player-bounds.height");

		entities = new HashSet<GameEntity>();
		road = new Road(new PhysicalBody(new Point(0, 0), new Dimension(roadWidth, roadHeight)),
				settings.getInt("road.lane-count"), listeners);
		roadTraffic = new RoadTraffic(road, entities, listeners);
		player = new PlayerCar(new PhysicalBody(new Point(125, 400), new Dimension(50, 100)), listeners);

		collisionManager = new CollisionManager(entities, player);

		playerBounds = new Rectangle(0, roadHeight - playerBoundsHeight, roadWidth, playerBoundsHeight);

		score = new Score();
		hitPoint = settings.getInt("player.starting-lives");
	}

	public void start() {
		initialNewGame();
		gameloop.start();
	}

	@Override
	public void run() {
		roadTraffic.generate();
		roadTraffic.moveAll(GameLoop.BOUND_TIME);
		roadTraffic.remove();

		if (collisionManager.checkCollision()) {
			hitPoint--;
		}

//		if (hitPoint < 0) {
//			gameloop.stop();
//			listeners.notify(new EventData(SenderType.GAME, EventType.GAME_OVER, new Integer(score.getPoints())));
//		}

		listeners.notify(new EventData(SenderType.HP, EventType.UPDATE, new Integer(hitPoint)));

		Vector directionVector = new Vector();

		if (inputKeeper.hasPressedKey(KeyType.UP) && player.getBody().getRectangle().y > playerBounds.y) {
			directionVector.addPolar(1, Math.PI * 3/2);
		}
		if (inputKeeper.hasPressedKey(KeyType.DOWN) &&
				player.getBody().getRectangle().y + player.getBody().getRectangle().height < playerBounds.y + playerBounds.height) {
			directionVector.addPolar(1, Math.PI * 1/2);
		}
		if (inputKeeper.hasPressedKey(KeyType.RIGHT) &&
				player.getBody().getRectangle().x + player.getBody().getRectangle().width < playerBounds.x + playerBounds.width) {
			directionVector.addPolar(1, 0);
		}
		if (inputKeeper.hasPressedKey(KeyType.LEFT) && player.getBody().getRectangle().x > playerBounds.x) {
			directionVector.addPolar(1, Math.PI);
		}

		player.getVelocity().setPolar(settings.getInt("player.velocity"), directionVector.getAngle());

		player.move(GameLoop.BOUND_TIME);

		score.increase(settings.getInt("score.increment-per-time"));
		listeners.notify(new EventData(SenderType.SCORE, EventType.UPDATE, new Integer(score.getPoints())));
	}

	public void addListener(Listener listener) {
		this.listeners.add(listener);
	}

	public void addGameEntityPrivateListener(GameEntity gameEntity, Listener listener) {
		gameEntity.addPrivateListener(listener);
	}
}
