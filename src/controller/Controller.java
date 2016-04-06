package controller;

import java.awt.event.KeyEvent;

import model.entity.GameEntity;
import model.input.KeyType;
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

	public void keyPressed(KeyEvent event) {
		model.getInputKeeper().pressKey(transformToKeyType(event.getKeyCode()));
	}

	public void keyReleased(KeyEvent event) {
		model.getInputKeeper().releaseKey(transformToKeyType(event.getKeyCode()));
	}

	private KeyType transformToKeyType(int keyCode) {
		switch (keyCode) {
			case KeyEvent.VK_UP:
				return KeyType.UP;
			case KeyEvent.VK_DOWN:
				return KeyType.DOWN;
			case KeyEvent.VK_RIGHT:
				return KeyType.RIGHT;
			case KeyEvent.VK_LEFT:
				return KeyType.LEFT;
			case KeyEvent.VK_SPACE:
				return KeyType.SPACE;
		}

		return KeyType.UNKNOWN;
	}
	
	public void startNewGame() {
		model.start();
	}
}
