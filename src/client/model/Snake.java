package client.model;

import java.util.LinkedList;
import java.util.Queue;

import common.model.Coordinate;
import common.model.Direction;

public class Snake {
	private Queue<Coordinate> occupiedSpaces;
	private Direction travelDirection;

	public Snake(Coordinate head, Direction dir, int size) {
		this.occupiedSpaces = new LinkedList<>();
		setDirection(dir);
		for (int i = 0; i < size; i++) {
			if (i == 0) {
				occupiedSpaces.add(head);
			} else {
				Coordinate tail = Coordinate.foo(head, dir, i);
				occupiedSpaces.add(tail);
			}
		}
	}

	/**
	 * Moves this snake one space forward in its assigned traveling direction.
	 * All pieces of the snake that come after its head will follow.
	 */
	public void moveForward() {
		int x0, y0, x1, y1;
		Coordinate head = occupiedSpaces.peek();
		System.out.printf("Snake: (%d, %d)\n", head.x, head.y);
		x0 = head.x;
		y0 = head.y;
		head.move(travelDirection);
		for (Coordinate coordinate : occupiedSpaces) {
			if (coordinate == head)
				continue;
			x1 = coordinate.x;
			y1 = coordinate.y;
			coordinate.moveTo(x0, y0);
			x0 = x1;
			y0 = y1;
		}
	}

	/**
	 * Returns the length of this snake.
	 * 
	 * @return the length of the snake
	 */
	public int length() {
		return occupiedSpaces.size();
	}

	/**
	 * Assigns a new direction to this snake.
	 * 
	 * @param dir
	 *            the direction to change to
	 */
	public void setDirection(Direction dir) {
		travelDirection = dir;
	}

	/**
	 * Returns an array of coordinates which explain which spaces this snake is
	 * occupying. The coordinates are sorted from head to tail.
	 * 
	 * @return an array of coordinates occupied by the snake
	 */
	public Queue<Coordinate> getOccupiedSpaces() {
		return occupiedSpaces;
	}
}
