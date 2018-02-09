package common.model;

import client.view.View;

public class Coordinate {

	public int x, y;

	public static int width = View.FIELD_SIZE_X;
	public static int height = View.FIELD_SIZE_Y;

	public Coordinate(int x, int y) {
		moveTo(x, y);
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}

		if (other == null || (this.getClass() != other.getClass())) {
			return false;
		}

		Coordinate othercoord = (Coordinate) other;
		return (this.x == othercoord.x && this.y == othercoord.y);
	}

	/**
	 * Moves this coordinate one step in the direction provided. Uses the static
	 * width and height variables to wrap around when traversing outside the
	 * board.
	 * 
	 * @param direction
	 *            the direction to move in
	 */
	public void move(Direction direction) {
		switch (direction) {
		case UP:
			if (--y < 0) {
				y = height - 1;
			}
			break;
		case DOWN:
			if (++y >= height) {
				y = 0;
			}
			break;
		case LEFT:
			if (--x < 0) {
				x = width - 1;
			}
			break;
		case RIGHT:
			if (++x >= width) {
				x = 0;
			}
			break;
		default:
			break;
		}
	}

	/**
	 * Moves the coordinate to a fixed point.
	 * 
	 * @param x
	 *            the x value to move to
	 * @param y
	 *            the y value to move to
	 */
	public void moveTo(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns a <code>String</code> representation of this coordinate.
	 * 
	 * @return the <code>String</code> representation of this coordinate
	 */
	public String toString() {
		return String.format("(%d, %d)", x, y);
	}

}
