package model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class CollisionManager {

	public Set<Colliding> entities = new HashSet<Colliding>();

	public CollisionManager() {

	}

	public boolean add(Colliding colliding) {
		return entities.add(colliding);
	}

	public boolean remove(Object o) {
		return entities.remove(o);
	}

	public boolean checkCollision(Colliding entity) {
		Iterator<Colliding> iterator = entities.iterator();
		while(iterator.hasNext()) {
			Colliding anotherEntity = iterator.next();

			if (entity.collidesWith(anotherEntity) && entity != anotherEntity) {
				return true;
			}
		}

		return false;
	}



}
