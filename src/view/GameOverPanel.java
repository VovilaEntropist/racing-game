package view;

import controller.Controller;
import model.Score;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOverPanel extends JPanel {

	private final Controller controller;
	private Integer score;

	public GameOverPanel(final Controller controller, Integer score) {
		this.controller = controller;
		this.score = score;

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setSize(300, 150);
		setLocation(50, 125);
		setBackground(new Color(255, 255, 255, 100));

		JLabel gameOverLabel = new JLabel("Game Over");
		gameOverLabel.setForeground(new Color(160, 8, 15));
		gameOverLabel.setAlignmentX(CENTER_ALIGNMENT);
		gameOverLabel.setFont(new Font(null, Font.BOLD, 50));

		JLabel scoreLabel = new JLabel("Score: " + String.valueOf(score));
		scoreLabel.setAlignmentX(CENTER_ALIGNMENT);
		scoreLabel.setFont(new Font(null, Font.BOLD, 25));

		add(gameOverLabel);
		add(scoreLabel);
	}

}
