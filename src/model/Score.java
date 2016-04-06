package model;


public class Score {
	private int points = 0;

	public Score() {

	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public void increase(int value) {
		this.points += value;
	}

	@Override
	public String toString() {
		return String.valueOf(points);
	}
}
