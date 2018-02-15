package client;

import java.util.List;

import client.view.View;
import common.model.Coordinate;
import common.model.GameState;
import common.model.Snake;

public class PaintSnakesOnViewThread extends Thread {
	private ClientGameStateMonitor monitor;
	private View view;
	private final String[] colors;
	
	public PaintSnakesOnViewThread(ClientGameStateMonitor monitor) {
		this.monitor = monitor;
		colors = new String[4];
		colors[0] = "blue";
		colors[1] = "green";
		colors[2] = "pink";
		colors[3] = "orange";
	}
	
	public void run() {
		while (!isInterrupted()) {
			GameState state = monitor.getGameState();
			List<Snake> snakes = state.getPlayerSnakes();
			int k = 0;
			for (Snake snake : snakes) {
				String color = colors[k++];
				for (Coordinate c : snake.getBody()) {
					if (!view.isColoredAt(c.x, c.y)) {
						view.colorTileAt(c.x, c.y, color);
					} else {
						String msg = String.format("Tile skipped: (%d, %d)\n", c.x, c.y);
						System.out.println(msg);
					}
				}
			}
		}
	}
}
