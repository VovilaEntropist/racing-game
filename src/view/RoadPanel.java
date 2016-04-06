package view;

import model.Road;
import utils.Settings;

import javax.swing.*;
import java.awt.*;

public class RoadPanel extends JPanel {

	private int laneCount;

	public RoadPanel(Road road) {
		super();

		laneCount = road.getLaneCount();

		setBackground(Color.GRAY);
		setBounds(road.getBody().getRectangle());

		setPreferredSize(road.getBody().getRectangle().getSize());

		setLayout(null);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		int roadLaneWidth = Settings.getInstance().getInt("road.lane-width");

		for (int i = 0; i < laneCount - 1; i++) {
			final int x = i * roadLaneWidth + roadLaneWidth;

			g2d.setColor(Color.WHITE);
			g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
			if ((laneCount - 1) / 2 == i) {
				g2d.drawLine(x-3, 0, x-3, getHeight());
				g2d.drawLine(x+3, 0, x+3, getHeight());
			} else {
				g2d.drawLine(x, 0, x, getHeight());
			}

		}
	}
}
