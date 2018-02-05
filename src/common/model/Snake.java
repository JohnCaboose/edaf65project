package common.model;

import java.util.LinkedList;

/**
 * The client and server common definition of a snake
 */
public class Snake {
	//public int score;
	public LinkedList<Coordinate> body;
	
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
			body.add(new Coordinate(startX, startY));
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
}
