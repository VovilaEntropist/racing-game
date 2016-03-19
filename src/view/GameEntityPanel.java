package view;

import model.Direction;
import model.GameEntity;
import model.PhysicalBody;
import model.listener.EventData;
import model.listener.EventType;
import model.listener.Listener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class GameEntityPanel extends JPanel implements Listener {
	protected BufferedImage image;
	protected Direction direction;

	private GameEntityPanel(PhysicalBody body) {
		super();
		initialize(body);
	}

	public GameEntityPanel(PhysicalBody body, BufferedImage bufImage) {
		this(body);
		this.image = bufImage;
	}

	@Deprecated
	public GameEntityPanel(PhysicalBody body, Image image) {
		this(body);
		this.image = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D bGr = (Graphics2D) image.getGraphics();
		bGr.drawImage(image, 0, 0, image.getWidth(null), image.getHeight(null), null);
		bGr.dispose();
	}

	private void initialize(PhysicalBody body) {
		this.setBackground(new Color(0, 0, 0, 0));
		this.setBounds(body.getRectangle());
		this.direction = body.getDirection();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		if (direction == Direction.UP) {
			AffineTransform at = AffineTransform.getScaleInstance(1, -1);
			at.translate(0, -image.getHeight(null));
			AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

			g2d.drawImage(op.filter(image, null), 0, 0, getWidth(), getHeight(), null);
		} else {
			g2d.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		}
	}

	@Override
	public void handleEvent(EventData eventData) {
		if(eventData.getEventType() == EventType.MOVE) {
			PhysicalBody body = (PhysicalBody) eventData.getObject();
			this.setLocation(body.getRectangle().getLocation());
		} else if (eventData.getEventType() == EventType.DISAPPEARANCE) {
			this.getParent().remove(this);
		}
	}


}
