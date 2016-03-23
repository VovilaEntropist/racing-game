package view;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.*;

import model.GameEntity;
import model.Road;
import model.listener.EventData;
import model.listener.EventType;
import model.listener.Listener;
import model.listener.SenderType;

import controller.Controller;
import utils.ImageLoader;
import utils.ImageRandom;

@SuppressWarnings("serial")
public class View extends JFrame implements Listener {
	public static final int FRAME_WIDTH = 400;
	public static final int FRAME_HEIGHT = 550;

	private final Controller controller;
	private ImageRandom carImageRandom;

	private JPanel scorePanel = new JPanel();
	private JLabel scoreLabel = new JLabel();
	private JLabel hpLabel = new JLabel();
	private JPanel gameField = new JPanel();

	private JPanel roadPanel;

	//private JPanel playerPanel;

	public View(final Controller controller) {
		this.controller = controller;

		gameField.setBackground(new Color(48, 139, 4));
		gameField.setLayout(new FlowLayout());

		scorePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		scorePanel.add(new JLabel("Score: "));
		scorePanel.add(scoreLabel);
		scorePanel.add(new JLabel("lives: "));
		scorePanel.add(hpLabel);

		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setLayout(new BorderLayout());
		this.add(scorePanel, BorderLayout.NORTH);
		this.add(gameField, BorderLayout.CENTER);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		controller.addListener(this);
		
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				controller.keyPressed(e);
			}

			@Override
			public void keyReleased(KeyEvent e) {
				controller.keyReleased(e);
			}
		});

		carImageRandom = new ImageRandom();
		carImageRandom.add(ImageLoader.load(new File("src/resources/car1.png")));
		carImageRandom.add(ImageLoader.load(new File("src/resources/car2.png")));
		carImageRandom.add(ImageLoader.load(new File("src/resources/car3.png")));
	}

	@Override
	public void handleEvent(EventData eventData) {
		SenderType senderType = eventData.getSenderType();
		EventType eventType = eventData.getEventType();

		if (senderType == SenderType.CAR && eventType == EventType.INITIALIZE) {
			GameEntity gameEntity = (GameEntity) eventData.getObject();
			GameEntityPanel gameEntityPanel = new GameEntityPanel(gameEntity.getObjectData(), carImageRandom.getRandomImage());
			roadPanel.add(gameEntityPanel);
			controller.addGameEntityPrivateListener(gameEntity, gameEntityPanel);
		} else if (senderType == SenderType.PLAYER && eventType == EventType.INITIALIZE) {
			GameEntity gameEntity = (GameEntity) eventData.getObject();
			GameEntityPanel gameEntityPanel = new GameEntityPanel(gameEntity.getObjectData(),
					ImageLoader.load(new File("src/resources/player.png")));
			roadPanel.add(gameEntityPanel);
			controller.addGameEntityPrivateListener(gameEntity, gameEntityPanel);
		} else if (senderType == SenderType.ROAD && eventType == EventType.INITIALIZE) {
			Road road = (Road) eventData.getObject();
			roadPanel = new RoadPanel(road);

			gameField.add(roadPanel);
		} else if (senderType == SenderType.SCORE && eventData.getEventType() == EventType.UPDATE) {
			Integer score = (Integer) eventData.getObject();
			scoreLabel.setText(String.valueOf(score));
		} else if (senderType == SenderType.HP && eventData.getEventType() == EventType.UPDATE) {
			Integer hp = (Integer) eventData.getObject();
			hpLabel.setText(String.valueOf(hp));
		}
		repaint();
	}

}
