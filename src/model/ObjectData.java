package model;

import java.awt.*;

public class ObjectData {
	private Rectangle rectangle;
	private Direction direction;


	public ObjectData(Point position, Dimension dimension, Direction direction) {
		rectangle = new Rectangle(position, dimension);
		this.direction = direction;
	}

	public ObjectData(Rectangle rectangle, Direction direction) {
		this.rectangle = rectangle;
		this.direction = direction;
	}

	public ObjectData(Point position, Dimension dimension) {
		this(position, dimension, Direction.DOWN);
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public double getX() {
		return rectangle.getX();
	}

	public double getY() {
		return rectangle.getY();
	}

	public double getWidth() {
		return rectangle.getWidth();
	}

	public double getHeight() {
		return rectangle.getHeight();
	}

	public Direction getDirection() {
		return direction;
	}
}
