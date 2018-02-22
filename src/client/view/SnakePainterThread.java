package client.view;

import java.util.List;

import client.model.ClientGameStateMonitor;
import common.constants.Constants;
import common.model.Coordinate;
import common.model.GameState;
import common.model.Snake;

public class SnakePainterThread extends Thread {
	private final ClientGameStateMonitor monitor;
	private final View view;
	private final String[] colors;

	public SnakePainterThread(ClientGameStateMonitor monitor, View view) {
		this.monitor = monitor;
		this.view = view;
		colors = new String[4];
		for (int i = 0; i < colors.length; i++) {
			colors[i] = Constants.playerColors[i];
		}
	}

	public void run() {
		GameState previousGameState = null;
		boolean deathReported = false;
		while (true) {
			GameState state = monitor.getGameState();
			
			if(state.getTickCounter() < 1) {
				view.println("Game starting in: " + -state.getTickCounter());
			}

			if (previousGameState != null) {
				// rita svansarna svarta
				for (Snake s : previousGameState.getPlayerSnakes()) {
					Coordinate c = s.getBody().getLast();
					view.colorTileAt(c.x, c.y, "BLACK");
				}
				// Missed a state, redraw entire screen so as to not leave old
				// snake bits on the screen
				if (previousGameState.getTickCounter() < (state.getTickCounter() - 1)) {
					view.clearPlayField();
				}
			}

			// System.out.println("Got a state!");
			List<Snake> snakes = state.getPlayerSnakes();
			int k = 0;
			for (Snake snake : snakes) {
				// System.out.println("Printing snake: " + k);
				String color = colors[k++];
				for (Coordinate c : snake.getBody()) {
					/*
					 * if (!view.isColoredAt(c.x, c.y)) { view.colorTileAt(c.x,
					 * c.y, color); } else { String msg = String.format(
					 * "Tile skipped: (%d, %d)", c.x, c.y);
					 * System.out.println(msg); }
					 */
					view.colorTileAt(c.x, c.y, color);
				}
			}
			if (!deathReported && !state.getPlayerSnakes().get(monitor.getPlayerIdentity().ordinal()).isAlive()) {
				view.displayDeadSnakeStatus();
				view.println("Your snake died :(");
				deathReported = true;
			}
			if (state.isGameOver()) {
				// System.out.println("Game is over, goodbye!");
				view.println("Game is over, goodbye!");
				break;
			}
			previousGameState = state;
		}
		//System.err.println("SnakePainterThread is dead");
		try {
			System.out.println("Game over. Client will close in 10 seconds.");
			Thread.sleep(10000);
			System.exit(0);
		} catch (InterruptedException e) {
		}
	}
}
