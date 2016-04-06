package model;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import model.listener.*;
import utils.Consumer;
import utils.Settings;
import utils.Vector;

public class Model implements Consumer<Double> {

	private ListenersList listeners;
	private InputKeeper inputKeeper;
	private CollisionManager collisionManager;

	private Score score;

	private RoadTraffic roadTraffic;
	private Road road;
	private Colliding border;
	private Colliding endGameBorder;

	private Settings settings;
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

	private void initNewGame() {
		final int roadWidth = settings.getInt("road.width");
		final int roadHeight = settings.getInt("road.height");
		final int playerBoundsHeight = settings.getInt("player-bounds.height");
		final int playerInitialX = settings.getInt("player.initial-x");
		final int playerInitialY = settings.getInt("player.initial-y");
		final int playerWidth = settings.getInt("player.width");
		final int playerHeight = settings.getInt("player.height");

		collisionManager = new CollisionManager();
		road = new Road(new ObjectData(new Point(0, 0), new Dimension(roadWidth, roadHeight)),
				settings.getInt("road.lane-count"), listeners);
		border = new Border(new CollisionBody(new Rectangle(0, roadHeight, roadWidth, 10)));
		collisionManager.add(border);
		endGameBorder = new Border(new CollisionBody(new Rectangle(0, 0, roadWidth, roadHeight)));
		roadTraffic = new RoadTraffic(road, collisionManager, listeners);

		GameEntity player = new PlayerCar(new ObjectData(new Point(playerInitialX, playerInitialY),
				new Dimension(playerWidth, playerHeight)), listeners, collisionManager);
		collisionManager.add(player);
		Rectangle playerBounds = new Rectangle(0, roadHeight - playerBoundsHeight, roadWidth, playerBoundsHeight);
		PlayerHandler playerHandler = new PlayerHandler(player, playerBounds, inputKeeper);
		new GameLoop(playerHandler).start();

		score = new Score();
	}

	public void start() {
		initNewGame();
		gameloop.start();
	}

	public void addListener(Listener listener) {
		this.listeners.add(listener);
	}

	public void addGameEntityPrivateListener(GameEntity gameEntity, Listener listener) {
		gameEntity.addPrivateListener(listener);
	}

	@Override
	public void run(Double deltaTime) {
		roadTraffic.generate();

		System.out.println(Thread.activeCount());

		score.increase(settings.getInt("score.increment-per-time"));
		listeners.notify(new EventData(SenderType.SCORE, EventType.UPDATE, new Integer(score.getPoints())));
	}
}
