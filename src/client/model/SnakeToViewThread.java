package client.model;

import java.util.Queue;

import client.view.View;
import common.model.Coordinate;
import common.model.Direction;

/* Periodic thread, currently used for testing Snake. */
public class SnakeToViewThread extends Thread {
	private Snake snake;
	private View v;
	private String color;
	private static final long delay = 250;

	public SnakeToViewThread(View v) {
		this.v = v;
		color = "blue";
		Coordinate head = new Coordinate(10, 10);
		Direction dir = Direction.RIGHT;
		int size = 4;
		snake = new Snake(head, dir, size);
	}

	public void run() {
		while (true) {
			try {
				moveSnake();
				moveSnake();
				moveSnake();
				snake.setDirection(Direction.DOWN);
				
				moveSnake();
				moveSnake();
				moveSnake();
				snake.setDirection(Direction.LEFT);
				
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
				snake.setDirection(Direction.LEFT);
				
				moveSnake();
				moveSnake();
				moveSnake();
				snake.setDirection(Direction.UP);
				
				moveSnake();
				moveSnake();
				moveSnake();
				snake.setDirection(Direction.RIGHT);
				
			} catch (InterruptedException e) {
			}
		}
	}

	private void moveSnake() throws InterruptedException {
		Thread.sleep(delay);
		paintSnake();
		snake.moveForward();
	}

	private void paintSnake() {
		v.clearPlayField();
		Queue<Coordinate> occupiedSpaces = snake.getOccupiedSpaces();
		for (Coordinate coordinate : occupiedSpaces) {
			v.colorTileAt(coordinate.x, coordinate.y, color);
		}
	}
}
