package model;

import java.util.Iterator;
import java.util.Set;

public class CollisionManager {

	public Set<GameEntity> entities;
	public GameEntity player;

	public CollisionManager(Set<GameEntity> entities, GameEntity player) {
		this.entities = entities;
		this.player = player;
	}

	public boolean checkCollision() {
		Iterator<GameEntity> iterator = entities.iterator();
		while(iterator.hasNext()) {
			GameEntity entity = iterator.next();

			if (entity.collide(player)) {
				entity.disappear();
				iterator.remove();
				return true;
			}
		}

		return false;
	}

}
