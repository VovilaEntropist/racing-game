package model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class CollisionManager {

	private Set<Colliding> entities;

	public CollisionManager() {
		entities = new HashSet<Colliding>();
	}

	public boolean add(Colliding colliding) {
		return entities.add(colliding);
	}

	public boolean remove(Object o) {
		return entities.remove(o);
	}

	public synchronized boolean checkCollisionFor(Colliding entity) {
		Iterator<Colliding> iterator = entities.iterator();
		while(iterator.hasNext()) {
			Colliding anotherEntity = iterator.next();

			if (entity.collidesWith(anotherEntity) && entity != anotherEntity) {
				entity.respondToCollision(anotherEntity);
				anotherEntity.respondToCollision(entity);
				return true;
			}
		}

		return false;
	}
}
