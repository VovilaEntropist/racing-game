package view;

import controller.Controller;
import model.Score;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOverPanel extends JFrame {

	private JLabel gameOverLabel = new JLabel("Game Over");
	private JLabel scoreLabel;

	private Integer score;

	public GameOverPanel(Integer score) {
		this.score = score;

		gameOverLabel.setForeground(new Color(160, 8, 15));
		gameOverLabel.setAlignmentX(CENTER_ALIGNMENT);
		gameOverLabel.setFont(new Font(null, Font.BOLD, 50));

		scoreLabel = new JLabel("Score: " + String.valueOf(this.score));
		scoreLabel.setAlignmentX(CENTER_ALIGNMENT);
		scoreLabel.setFont(new Font(null, Font.BOLD, 25));

		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setSize(300, 150);
		setLocation(50, 125);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBackground(new Color(255, 255, 255, 100));
		add(gameOverLabel);
		add(scoreLabel);

		setVisible(true);
	}

}
