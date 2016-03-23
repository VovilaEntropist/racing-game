package model;

import java.awt.*;

public class CollisionBody {

	private Rectangle rectangle;

	public CollisionBody(Rectangle rectangle) {
		this.rectangle = rectangle;
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
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
}
