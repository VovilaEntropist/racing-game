package controller;

import model.entity.GameEntity;
import model.Model;
import model.listener.Listener;

public class Controller {
	private Model model;
	
	public Controller(Model model) {
		this.model = model;
	}

	public void addListener(Listener listener) {
		model.addListener(listener);
	}

	public void addGameEntityPrivateListener(GameEntity gameEntity, Listener listener) {
		model.addGameEntityPrivateListener(gameEntity, listener);
	}

	public void addActionUp() {
		model.addActionUp();
	}

	public void addActionDown() {
		model.addActionDown();
	}

	public void addActionRight() {
		model.addActionRight();
	}

	public void addActionLeft() {
		model.addActionLeft();
	}

	public void removeActionUp() {
		model.removeActionUp();
	}

	public void removeActionDown() {
		model.removeActionDown();
	}

	public void removeActionRight() {
		model.removeActionRight();
	}

	public void removeActionLeft() {
		model.removeActionLeft();
	}
}
