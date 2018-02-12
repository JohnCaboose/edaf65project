package common.model;

import java.util.LinkedList;

/**
 * The client and server common definition of a snake
 */
public class Snake {
	// public int score;
	private LinkedList<Coordinate> body;
	private Direction travelDirection;
	private boolean alive;

	/**
	 * Creates a new snake
	 * 
	 * @param tail
	 *            where the end of the snake should be
	 * @param directionFromTailToHead
	 *            what direction the head is from the tail
	 * @param length
	 *            the size of the snake
	 */
	public Snake(Coordinate tail, Direction directionFromTailToHead, int length) {
		int startX = tail.x;
		int startY = tail.y;
		body = new LinkedList<Coordinate>();
		for (int i = 0; i < length; i++) {
			body.addFirst(new Coordinate(startX, startY));
		}
		for (int i = 0; i < length; i++) {
			Coordinate c = body.get(i);
			for (int k = 0; k < i; k++) {
				c.move(directionFromTailToHead);
			}
		}
		alive = true;
		travelDirection = directionFromTailToHead;
	}

	/**
	 * Moves this snake one step in the direction provided. This method has no
	 * effect if the snake is not alive.
	 * 
	 * @param direction
	 *            the direction to move the snake
	 */
	public void move(Direction direction) {
		if (alive) {
			Coordinate oldHead = body.getFirst();
			Coordinate newHead = body.removeLast();
			body.addFirst(newHead);
			newHead.x = oldHead.x;
			newHead.y = oldHead.y;
			newHead.move(direction);
		}
	}

	/**
	 * Moves this snake one space forward in its assigned traveling direction.
	 * All pieces of the snake that come after its head will follow. This method
	 * has no effect if the snake is not alive.
	 */
	public void moveForward() {
		move(this.travelDirection);
	}

	/**
	 * Returns the length of this snake.
	 * 
	 * @return the length of the snake
	 */
	public int length() {
		return body.size();
	}

	/**
	 * Assigns a new direction to this snake, but only if it doesn't walk right back into itself by doing so
	 * 
	 * @param dir
	 *            the direction to change to
	 */
	public void setDirection(Direction dir) {
		if(travelDirection.getOppositeDirection() != dir) {
			travelDirection = dir;
		}
	}

	/**
	 * Returns a list of coordinates which this snake is occupying. The
	 * coordinates are sorted from head to tail.
	 * 
	 * @return a list of coordinates occupied by the snake
	 */
	public LinkedList<Coordinate> getOccupiedSpaces() {
		return body;
	}

	/**
	 * Checks if this snake is currently alive or not.
	 * 
	 * @return <code>true</code> if snake is alive, otherwise <code>false</code>
	 */
	public boolean isAlive() {
		return alive;
	}
	
	/**
	 * Sets the snake's alive status to false
	 */
	public void kill() {
		//TODO decide if Snake should be removed from playing field
		alive = false;
	}
	
	/**
	 * This snake's current travel direction
	 * @return travel direction of this snake
	 */
	public Direction getDirection() {
		return this.travelDirection;
	}
	
	//TODO implement comparison functions for purpose of effectively syncing predictions with server (optional)
}
