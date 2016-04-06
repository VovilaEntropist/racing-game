package model;

import model.entity.GameEntity;

import java.util.HashSet;
import java.util.Set;

public class GameEntityList {

	private Set<GameEntity> entities;

	public GameEntityList() {
		entities = new HashSet<GameEntity>();
	}

	public Set<GameEntity> getSet() {
		return entities;
	}

	public boolean add(GameEntity gameEntity) {
		return entities.add(gameEntity);
	}

	public boolean remove(Object o) {
		return entities.remove(o);
	}


}
