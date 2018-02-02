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
		Coordinate head = body.removeFirst();
		body.addFirst(body.removeLast());
		body.getFirst().x = head.x;
		body.getFirst().y = head.y;
		head.move(direction);
		body.addFirst(head);
	}
}
