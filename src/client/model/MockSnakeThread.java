package client.model;

import java.util.LinkedList;

import client.view.View;
import common.model.Coordinate;
import common.model.Direction;
import common.model.Snake;

/* Periodic thread, currently used for testing Snake. */
public class MockSnakeThread extends Thread {
	private Snake snake;
	private View v;
	private String color;
	private static final long delay = 300;

	public MockSnakeThread(View v) {
		this.v = v;
		color = "blue";
		Coordinate head = new Coordinate(10, 10);
		Direction dir = Direction.RIGHT;
		int size = 10;
		snake = new Snake(head, dir, size);
	}

	public void run() {
		snake.setDirection(Direction.DOWN);
		paintSnake();
		while (true) {
			try {
				moveSnake();
				moveSnake();
				moveSnake();
				snake.setDirection(Direction.DOWN);
				
				moveSnake();
				moveSnake();
				moveSnake();
				snake.setDirection(Direction.RIGHT);
				
				moveSnake();
				moveSnake();
				moveSnake();
				moveSnake();
				moveSnake();
				moveSnake();
				snake.setDirection(Direction.UP);
				
				moveSnake();
				moveSnake();
				moveSnake();
				snake.setDirection(Direction.RIGHT);
				
				moveSnake();
				moveSnake();
				moveSnake();
				snake.setDirection(Direction.UP);
				
				moveSnake();
				moveSnake();
				moveSnake();
				snake.setDirection(Direction.LEFT);
				
				/* For testing purposes */
				v.displayDeadSnakeStatus();
				
			} catch (InterruptedException e) {
			}
		}
	}

	private void moveSnake() throws InterruptedException {
		Thread.sleep(delay);
		removeTailOfSnake();
		snake.moveForward();
		paintHeadOfSnake();
	}

	private void paintSnake() {
		LinkedList<Coordinate> occupiedSpaces = snake.getBody();
		for (Coordinate coordinate : occupiedSpaces) {
			v.colorTileAt(coordinate.x, coordinate.y, color);
		}
	}
	
	private void removeTailOfSnake() {
		LinkedList<Coordinate> occupiedSpaces = snake.getBody();
		Coordinate tail = occupiedSpaces.getLast();
		v.colorTileAt(tail.x, tail.y, "black");
	}
	
	private void paintHeadOfSnake() {
		LinkedList<Coordinate> occupiedSpaces = snake.getBody();
		Coordinate head = occupiedSpaces.getFirst();
		v.colorTileAt(head.x, head.y, color);
		v.println(String.format("Snake moved to (%d, %d).", head.x, head.y));
	}
}
