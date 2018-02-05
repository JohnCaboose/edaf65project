package common.model;

import java.util.LinkedList;

/**
 * The client and server common definition of a snake
 */
public class Snake {
	//public int score;
	public LinkedList<Coordinate> body;
	
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
