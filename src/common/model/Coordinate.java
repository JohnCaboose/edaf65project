package common.model;

public class Coordinate {

	public int x, y;

	public static int width, height;

	public Coordinate(int x, int y) {
		moveTo(x, y);
	}

	@Override
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
	 * <p>
	 * Helper function for client.model.Snake. May be removed later.
	 * </p>
	 * 
	 * <p>
	 * Returns a new Coordinate which is <code>offset</code> units away from the
	 * <code>head</code> in the <b>negative</b> direction <code>dir</code>. Used
	 * when initially creating a new snake.
	 * </p>
	 * 
	 * @param head
	 *            the head of the snake
	 * @param dir
	 *            the travel direction of the snake
	 * @param offset
	 *            the distance between the returning coordinate and the head of
	 *            the snake
	 */
	public static Coordinate foo(Coordinate head, Direction dir, int offset) {
		int x = head.x;
		int y = head.y;
		switch (dir) {
		case UP:
			y += offset;
			break;
		case DOWN:
			y -= offset;
			break;
		case LEFT:
			x += offset;
			break;
		case RIGHT:
			x -= offset;
			break;
		}
		Coordinate coord = new Coordinate(x, y);
		return coord;
	}

}
