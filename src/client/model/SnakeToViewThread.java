package client.model;

import java.util.LinkedList;

import client.view.View;
import common.model.Coordinate;
import common.model.Direction;
import common.model.Snake;

/* Periodic thread, currently used for testing Snake. */
public class SnakeToViewThread extends Thread {
	private Snake snake;
	private View v;
	private String color;
	private static final long delay = 200;

	public SnakeToViewThread(View v) {
		this.v = v;
		color = "blue";
		Coordinate head = new Coordinate(10, 10);
		Direction dir = Direction.RIGHT;
		int size = 10;
		snake = new Snake(head, dir, size);
	}

	public void run() {
		while (true) {
			try {
				snake.setDirection(Direction.LEFT);
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
				
			} catch (InterruptedException e) {
			}
		}
	}

	private void moveSnake() throws InterruptedException {
		Thread.sleep(delay);
		removePreviouslyPaintedSnake();
		snake.moveForward();
		paintSnake();
	}

	private void paintSnake() {
		LinkedList<Coordinate> occupiedSpaces = snake.getOccupiedSpaces();
		for (Coordinate coordinate : occupiedSpaces) {
			v.colorTileAt(coordinate.x, coordinate.y, color);
		}
	}
	
	private void removePreviouslyPaintedSnake() {
		LinkedList<Coordinate> occupiedSpaces = snake.getOccupiedSpaces();
		for (Coordinate coordinate : occupiedSpaces) {
			v.colorTileAt(coordinate.x, coordinate.y, "black");
		}
	}
}
