package client;

import java.util.List;

import client.view.View;
import common.model.Coordinate;
import common.model.GameState;
import common.model.Snake;

public class PaintSnakesOnViewThread extends Thread {
	private final ClientGameStateMonitor monitor;
	private final View view;
	private final String[] colors;
	
	public PaintSnakesOnViewThread(ClientGameStateMonitor monitor, View view) {
		this.monitor = monitor;
		this.view = view;
		colors = new String[4];
		colors[0] = "blue";
		colors[1] = "green";
		colors[2] = "pink";
		colors[3] = "orange";
	}
	
	public void run() {
		while (true) {
			GameState state = monitor.getGameState();
			System.out.println("Got a state!");
			List<Snake> snakes = state.getPlayerSnakes();
			int k = 0;
			for (Snake snake : snakes) {
				System.out.println("Printing snake: " + k);
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
			if(state.isGameOver()){
				System.out.println("Game is over, goodbye!");
				view.println("Game is over, goodbye!");
				break;
			}
		}
		System.err.println("paintsnakes is dead");
	}
}
