package model;

public class Health {

	private int points;

	public Health(int points) {
		this.points = points;
	}

	public Health() {
		this(0);
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public void decrease() {
		if (!isZero()) {
			points--;
		}
	}

	public void increase() {
		points++;
	}

	public boolean isZero() {
		return points <= 0;
	}
}
