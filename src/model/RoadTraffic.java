package model;

import model.collision.CollisionManager;
import model.entity.Car;
import model.entity.GameEntity;
import model.entity.Life;
import model.handler.GameEntityHandler;
import model.listener.ListenersList;
import utils.Settings;
import utils.Vector;

import java.awt.*;
import java.util.*;
import java.util.List;

public class RoadTraffic {
	private Road road;
	private ListenersList listeners;
	private CollisionManager collisionManager;
	private GameState gameState;

	private int generationInterval;
	private List<Integer> availableLanes = new ArrayList<Integer>();

	private long lastGenerationTime;

	public RoadTraffic(Road road, CollisionManager collisionManager, ListenersList listeners, GameState gameState) {
		this.road = road;
		this.collisionManager = collisionManager;
		this.listeners = listeners;
		this.gameState = gameState;

		lastGenerationTime = System.currentTimeMillis();
		generationInterval = 0;
	}

	public void generate() {
		final long currentTime = System.currentTimeMillis();

		if (currentTime - lastGenerationTime >= generationInterval) {
			lastGenerationTime = currentTime;

			Random random = new Random();
			Settings settings = Settings.getInstance();

			availableLanes.clear();
			for (int i = 0; i < road.getLaneCount() ; i++) {
				availableLanes.add(i);
			}

			double value = random.nextDouble();
			int generationQuantity = (int) Math.ceil((road.getLaneCount() - 1) * Math.pow(value, 2));

			for (int i = 0; i < generationQuantity; i++) {
				int laneIndex = random.nextInt(availableLanes.size());
				int lane = availableLanes.get(laneIndex);
				availableLanes.remove(laneIndex);

				int offset = random.nextInt(settings.getInt("road-traffic.max-offset"));

				Direction direction;
				int velocityModifier;

				if (lane < road.getLaneCount() / 2) {
					direction = Direction.DOWN;
					velocityModifier = 1;
				} else {
					direction = Direction.UP;
					velocityModifier = -1;
				}

				int carWidth = settings.getInt("car.width");
				int carHeight = settings.getInt("car.height");
				int roadLaneWidth = settings.getInt("road.lane-width");

				GameEntity entity;
				double lifeChance = settings.getDouble("life.chance");
				if (random.nextDouble() < lifeChance) {
					entity = new Life(new ObjectData(new Rectangle(lane * roadLaneWidth +
							(roadLaneWidth - carWidth) / 2, -carHeight - offset, carWidth, carHeight),
							direction), listeners, collisionManager);
				} else {
					entity = new Car(new ObjectData(new Rectangle(lane * roadLaneWidth +
							(roadLaneWidth - carWidth) / 2, -carHeight - offset, carWidth, carHeight),
							direction), listeners, collisionManager);
				}

				Vector vel = new Vector(0, velocityModifier * (random.nextInt(settings.getInt("car.max-velocity")
						- settings.getInt("car.min-velocity")) + settings.getInt("car.min-velocity"))
						+ settings.getInt("player.velocity"));

				entity.setVelocity(vel);

				collisionManager.add(entity);

				new GameLoop(new GameEntityHandler(entity, gameState), gameState).start();
			}

			int maxGenerationInterval = settings.getInt("road-traffic.max-generation-interval");
			int minGenerationInterval = settings.getInt("road-traffic.min-generation-interval");

			generationInterval = random.nextInt(maxGenerationInterval - minGenerationInterval) + minGenerationInterval;
		}
	}
}