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

	public SnakeToViewThread(View v) {
		this.v = v;
		color = "blue";
		Coordinate head = new Coordinate(10, 10);
		Direction dir = Direction.UP;
		int size = 2;
		snake = new Snake(head, dir, size);
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(3000);
				v.clearPlayField();
				Queue<Coordinate> occupiedSpaces = snake.getOccupiedSpaces();
				for (Coordinate coordinate : occupiedSpaces) {
					v.colorTileAt(coordinate.x, coordinate.y, color);
				}
				snake.moveForward();
			} catch (InterruptedException e) {
			}
		}
	}
}
