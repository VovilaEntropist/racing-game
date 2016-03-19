package model;

import java.awt.*;

public class PhysicalBody {
	private Rectangle rectangle;
	private Direction direction;

	public PhysicalBody(Point position, Dimension dimension, Direction direction) {
		rectangle = new Rectangle(position, dimension);
		this.direction = direction;
	}

	public PhysicalBody(Rectangle rectangle, Direction direction) {
		this.rectangle = rectangle;
		this.direction = direction;
	}

	public PhysicalBody(Point position, Dimension dimension) {
		this(position, dimension, Direction.DOWN);
	}
//
//	@Deprecated
//	public Dimension getDimension() {
//		return rectangle.getSize();
//	}
//
//	@Deprecated
//	public Point getPos() {
//		return rectangle.getLocation();
//	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public Direction getDirection() {
		return direction;
	}
}
