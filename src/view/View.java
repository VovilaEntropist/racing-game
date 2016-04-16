package view;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.*;

import model.entity.GameEntity;
import model.Road;
import model.listener.EventData;
import model.listener.EventType;
import model.listener.Listener;
import model.listener.SenderType;

import controller.Controller;
import utils.ImageLoader;
import utils.ImageRandom;
import utils.Settings;

@SuppressWarnings("serial")
public class View extends JFrame implements Listener {
	private final Controller controller;
	private ImageRandom carImageRandom;

	private JPanel scorePanel = new JPanel();
	private JLabel scoreLabel = new JLabel();
	private JLabel hpLabel = new JLabel();
	private JPanel gameField = new JPanel();

	private JPanel roadPanel;

	public View(final Controller controller) {
		this.controller = controller;

		controller.addListener(this);

		init();
	}

	private void init() {
		Settings settings = Settings.getInstance();
		int frameWidth = settings.getInt("view.width");
		int frameHeight = settings.getInt("view.height");
		String gameFieldColor = settings.get("view.gamefield.color");
		String frameTitle = settings.get("view.tittle");

		this.setSize(frameWidth, frameHeight);
		this.setTitle(frameTitle);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);

		this.add(scorePanel, BorderLayout.NORTH);
		this.add(gameField, BorderLayout.CENTER);

		gameField.setBackground(Color.decode(gameFieldColor));
		gameField.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

		scorePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		scorePanel.add(new JLabel("Score: "));
		scorePanel.add(scoreLabel);
		scorePanel.add(new JLabel("lives: "));
		scorePanel.add(hpLabel);

		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				addPressedKey(e.getKeyCode());
			}

			@Override
			public void keyReleased(KeyEvent e) {
				addReleasedKey(e.getKeyCode());
			}
		});

		loadImages();

	}

	public void addPressedKey(int keyCode) {
		switch (keyCode) {
			case KeyEvent.VK_UP:
				controller.addActionUp();
				break;
			case KeyEvent.VK_DOWN:
				controller.addActionDown();
				break;
			case KeyEvent.VK_RIGHT:
				controller.addActionRight();
				break;
			case KeyEvent.VK_LEFT:
				controller.addActionLeft();
				break;
		}
	}

	public void addReleasedKey(int keyCode) {
		switch (keyCode) {
			case KeyEvent.VK_UP:
				controller.removeActionUp();
				break;
			case KeyEvent.VK_DOWN:
				controller.removeActionDown();
				break;
			case KeyEvent.VK_RIGHT:
				controller.removeActionRight();
				break;
			case KeyEvent.VK_LEFT:
				controller.removeActionLeft();
				break;
		}
	}

	private void loadImages() {
		Settings settings = Settings.getInstance();
		carImageRandom = new ImageRandom();
		carImageRandom.add(ImageLoader.load(new File(settings.get("image.car1"))));
		carImageRandom.add(ImageLoader.load(new File(settings.get("image.car2"))));
		carImageRandom.add(ImageLoader.load(new File(settings.get("image.car3"))));
		carImageRandom.add(ImageLoader.load(new File(settings.get("image.car4"))));
		carImageRandom.add(ImageLoader.load(new File(settings.get("image.car5"))));
	}

	private void initCar(EventData eventData) {
		GameEntity gameEntity = (GameEntity) eventData.getObject();
		GameEntityPanel gameEntityPanel = new GameEntityPanel(gameEntity.getPhysicalBody(), carImageRandom.getRandomImage());
		roadPanel.add(gameEntityPanel);
		controller.addGameEntityPrivateListener(gameEntity, gameEntityPanel);
	}

	private void initPlayer(EventData eventData) {
		Settings settings = Settings.getInstance();

		GameEntity gameEntity = (GameEntity) eventData.getObject();
		GameEntityPanel gameEntityPanel = new GameEntityPanel(gameEntity.getPhysicalBody(),
				ImageLoader.load(new File(settings.get("image.player"))));
		roadPanel.add(gameEntityPanel);
		controller.addGameEntityPrivateListener(gameEntity, gameEntityPanel);
	}

	private void initLife(EventData eventData) {
		Settings settings = Settings.getInstance();

		GameEntity gameEntity = (GameEntity) eventData.getObject();
		GameEntityPanel gameEntityPanel = new GameEntityPanel(gameEntity.getPhysicalBody(),
				ImageLoader.load(new File(settings.get("image.ambulance"))));
		roadPanel.add(gameEntityPanel);
		controller.addGameEntityPrivateListener(gameEntity, gameEntityPanel);
	}

	private void initRoad(EventData eventData) {
		Road road = (Road) eventData.getObject();

		if (roadPanel != null) {
			roadPanel.removeAll();
		} else {
			roadPanel = new RoadPanel(road);
			gameField.add(roadPanel);
		}
	}

	private void updateScore(EventData eventData) {
		Integer score = (Integer) eventData.getObject();
		scoreLabel.setText(String.valueOf(score));
	}

	private void updateHP(EventData eventData) {
		Integer hp = (Integer) eventData.getObject();
		hpLabel.setText(String.valueOf(hp));
	}

	private void setGameOver(EventData eventData) {
		Integer score = (Integer) eventData.getObject();
		new GameOverPanel(score);
	}

	@Override
	public void handleEvent(EventData eventData) {
		SenderType senderType = eventData.getSenderType();
		EventType eventType = eventData.getEventType();

		if (senderType == SenderType.CAR && eventType == EventType.INITIALIZE) {
			initCar(eventData);
		} else if (senderType == SenderType.PLAYER && eventType == EventType.INITIALIZE) {
			initPlayer(eventData);
		} else if (senderType == SenderType.LIFE && eventType == EventType.INITIALIZE) {
			initLife(eventData);
		} else if (senderType == SenderType.ROAD && eventType == EventType.INITIALIZE) {
			initRoad(eventData);
		} else if (senderType == SenderType.SCORE && eventData.getEventType() == EventType.UPDATE) {
			updateScore(eventData);
		} else if (senderType == SenderType.HP && eventData.getEventType() == EventType.UPDATE) {
			updateHP(eventData);
		} else if (senderType == SenderType.GAME && eventData.getEventType() == EventType.GAME_OVER) {
			setGameOver(eventData);
		}
		repaint();
	}

}
