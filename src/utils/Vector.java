package utils;


public class Vector {

	public double x;
	public double y;

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector() {
		this(0, 0);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getLength() {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}

	public double getAngle() {
//		if (x == 0 && y == 0) {
//			return 0;
//		}

		double result = Math.atan(y /x);
		if (x < 0) {
			result += Math.PI;
		}
		if(result < 0) {
			result += 2 * Math.PI;
		}

		return result;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void set(double x, double y) {
		setX(x);
		setY(y);
	}

	private double roundTo(double value, int digitCount) {
		return Math.round(value * Math.pow(10, digitCount)) / Math.pow(10, digitCount);
	}

	public void setPolar(double length, double angle) {
		setX(roundTo(Math.cos(angle) * length, 3));
		setY(roundTo(Math.sin(angle) * length, 3));
	}

	@Deprecated
	public void setLength(double length) {
		setPolar(length, getAngle());
	}

	@Deprecated
	public void setAngle(double angle) {
		setPolar(getLength(), angle);
	}

	public void multiply(double multiplier) {
		set(x * multiplier, y * multiplier);
	}

	public void addX(double x) {
		setX(this.x + x);
	}

	public void addY(double y) {
		setY(this.y + y);
	}

	public void add(double x, double y) {
		addX(x);
		addY(y);
	}

	public void addPolar(double length, double angle) {
		addX(roundTo(Math.cos(angle) * length, 3));
		addY(roundTo(Math.sin(angle) * length, 3));
	}

	@Override
	public String toString() {
		return String.format("[%.2f; %.2f]", x, y);
	}

}
