package model;

import model.listener.ListenersList;
import utils.Settings;
import utils.Vector;

import java.awt.*;
import java.util.*;
import java.util.List;

public class RoadTraffic {
	private Road road;
	//private GameEntityList entities;
	private ListenersList listeners;
	private CollisionManager collisionManager;

	private int generationInterval;
	private List<Integer> availableLanes = new ArrayList<Integer>();

	private long lastGenerationTime;

//	public RoadTraffic(Road road, GameEntityList entities, CollisionManager collisionManager, ListenersList listeners) {
//		this.road = road;
//		this.entities = entities;
//		this.collisionManager = collisionManager;
//		this.listeners = listeners;
//
//		generationInterval = 0;
//	}

	public RoadTraffic(Road road, CollisionManager collisionManager, ListenersList listeners) {
		this.road = road;
		this.collisionManager = collisionManager;
		this.listeners = listeners;

		lastGenerationTime = System.currentTimeMillis();
		generationInterval = 0;
	}

//	private int countLastGenerationY() {
//		int lastGenerationY = 0;
//		try {
//			lastGenerationY = Collections.min(entities.getSet(), new Comparator<GameEntity>() {
//				@Override
//				public int compare(GameEntity o1, GameEntity o2) {
//					return o1.getObjectData().getRectangle().y -
//							o2.getObjectData().getRectangle().y;
//				}
//			}).getObjectData().getRectangle().y;
//		} catch (NoSuchElementException e) {
//			lastGenerationY = generationInterval;
//		}
//
//		return lastGenerationY;
//	}

	public void generate() {
		//int lastGenerationY = countLastGenerationY();

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

				GameEntity entity = new Car(new ObjectData(new Rectangle(lane * roadLaneWidth +
						(roadLaneWidth - carWidth) / 2, -carHeight - offset, carWidth, carHeight),
						direction), collisionManager, listeners);
				collisionManager.add(entity);

				Vector vel = new Vector(0, velocityModifier * (random.nextInt(settings.getInt("car.max-velocity")
						- settings.getInt("car.min-velocity")) + settings.getInt("car.min-velocity"))
						+ settings.getInt("player.velocity"));

				entity.setVelocity(vel);
				//entities.add(entity);

				new GameLoop(new GameEntityHandler(entity, listeners, collisionManager)).start();
			}

			int maxGenerationInterval = settings.getInt("road-traffic.max-generation-interval");
			int minGenerationInterval = settings.getInt("road-traffic.min-generation-interval");

			generationInterval = random.nextInt(maxGenerationInterval - minGenerationInterval) + minGenerationInterval;
		}
	}

//	public void remove() {
//		Iterator<GameEntity> iterator = entities.getSet().iterator();
//		while(iterator.hasNext()) {
//			GameEntity entity = iterator.next();
//
//			if (disappeared(entity)) {
//				entity.disappear();
//				collisionManager.remove(entity);
//				iterator.remove();
//			}
//		}
//	}

//	private boolean disappeared(GameEntity entity) {
//		return entity.getObjectData().getRectangle().y >
//				road.getBody().getRectangle().height;
//	}

//	public void moveAll(double time) {
//		for (GameEntity entity : entities.getSet()) {
//			entity.move(time);
//		}
//	}

//	public void checkCollisionAll() {
//		Iterator<GameEntity> iterator = entities.getSet().iterator();
//		while(iterator.hasNext()) {
//			GameEntity entity = iterator.next();
//
//			Colliding colliding = entity.collides();
//			if (colliding != null) {
//				entity.respondToCollision(colliding);
//				entity.disappear();
//				collisionManager.remove(entity);
//				iterator.remove();
//			}
//		}
//	}
}