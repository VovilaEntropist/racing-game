package model;

import model.listener.ListenersList;
import utils.Settings;
import utils.Vector;

import java.awt.*;
import java.util.*;
import java.util.List;

public class RoadTraffic {
	private Road road;
	private Set<GameEntity> entities;
	private ListenersList listeners;

	private int generationInterval;
	private List<Integer> availableLanes = new ArrayList<Integer>();

	public RoadTraffic(Road road, Set<GameEntity> entities, ListenersList listeners) {
		this.road = road;
		this.entities = entities;
		this.listeners = listeners;

		generationInterval = 0;
	}

	private int countLastGenerationY() {
		int lastGenerationY = 0;
		try {
			lastGenerationY = Collections.min(entities, new Comparator<GameEntity>() {
				@Override
				public int compare(GameEntity o1, GameEntity o2) {
					return o1.getBody().getRectangle().y -
							o2.getBody().getRectangle().y;
				}
			}).getBody().getRectangle().y;
		} catch (NoSuchElementException e) {
			lastGenerationY = generationInterval;
		}

		return lastGenerationY;
	}

	public void generate() {
		int lastGenerationY = countLastGenerationY();

		if (lastGenerationY >= generationInterval) {
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

				Direction direction;

				int offset = random.nextInt(settings.getInt("road-traffic.max-offset"));

				int velocityModifier;

				if (lane < road.getLaneCount() / 2) {
					direction = Direction.DOWN;
					velocityModifier = 1;
				} else {
					direction = Direction.UP;
					velocityModifier = -1;
				}

				utils.Vector vel = new Vector(0, settings.getInt("player.velocity")
						+ velocityModifier * (random.nextInt(settings.getInt("car.max-velocity")
						- settings.getInt("car.min-velocity")) + settings.getInt("car.min-velocity")));

				int carWidth = settings.getInt("car.width");
				int carHeight = settings.getInt("car.height");
				int roadLaneWidht = settings.getInt("road.lane-width");

				GameEntity entity = new Car(new PhysicalBody(new Rectangle(lane * roadLaneWidht +
						(roadLaneWidht - carWidth) / 2, -carHeight - offset, carWidth, carHeight),
						direction), listeners);
				entity.setVelocity(vel);
				entities.add(entity);
			}

			int maxGenerationInterval = settings.getInt("road-traffic.max-generation-interval");
			int minGenerationInterval = settings.getInt("road-traffic.min-generation-interval");

			generationInterval = random.nextInt(maxGenerationInterval - minGenerationInterval) + minGenerationInterval;
		}
	}

	public void remove() {
		Iterator<GameEntity> iterator = entities.iterator();
		while(iterator.hasNext()) {
			GameEntity entity = iterator.next();

			if (disappeared(entity)) {
				entity.disappear();
				iterator.remove();
			}
		}
	}

	private boolean disappeared(GameEntity entity) {
		return entity.getBody().getRectangle().y >
				road.getBody().getRectangle().height;
	}

	public void moveAll(double time) {
		for (GameEntity entity : entities) {
			entity.move(time);
		}
	}
}