package common.model;

import java.util.LinkedList;
import java.util.Queue;

/**
 * The client and server common definition of a snake
 */
public class Snake {
	//public int score;
	private LinkedList<Coordinate> body;
	private Direction travelDirection;
	private boolean alive; 
	//TODO: implement what a snake being dead/alive means
	
	/**
	 * Creates a new snake
	 * @param tail where the end of the snake should be
	 * @param directionFromTailToHead what direction the head is from the tail
	 * @param length the size of the snake
	 */
	public Snake(Coordinate tail, Direction directionFromTailToHead, int length) {
		int startX = tail.x;
		int startY = tail.y;
		body = new LinkedList<Coordinate>();
		for(int i = 0; i< length; i++) {
			body.addFirst(new Coordinate(startX, startY));
		}
		for(int i = 0; i< length; i++) {
			Coordinate c = body.get(i);
			for(int k = 0; k < i; k++) {
				c.move(directionFromTailToHead);
			}
		}
		
	}
	
	/**
	 * Moves the snake one step in the direction provided
	 * @param direction the direction to move the snake
	 */
	public void move(Direction direction) {
		//Take last body part of snake and put it first (make it new head)
		body.addFirst(body.removeLast());
		//Give the new head the old head's coordinates
		body.getFirst().x = body.get(1).x;
		body.getFirst().y = body.get(1).y;
		//Move the new head
		body.getFirst().move(direction);
	}
	
	/**
	 * Moves this snake one space forward in its assigned traveling direction.
	 * All pieces of the snake that come after its head will follow.
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
	public LinkedList<Coordinate> getOccupiedSpaces() {
		return body;
	}
	
	public Coordinate getTail() {
		return body.getLast();
	}
}
