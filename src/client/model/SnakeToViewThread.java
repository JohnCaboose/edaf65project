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
				snake.setDirection(Direction.RIGHT);
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
		Coordinate tail = snake.getTail();
		int x = tail.x;
		int y = tail.y;
		snake.moveForward();
		paintSnake();
		v.colorTileAt(x, y, "black");
		Thread.sleep(delay);
	}

	private void paintSnake() {
		LinkedList<Coordinate> occupiedSpaces = snake.getOccupiedSpaces();
		for (Coordinate coordinate : occupiedSpaces) {
			v.colorTileAt(coordinate.x, coordinate.y, color);
		}
	}
}
