package server.network;

import server.model.GameStateMonitor;

public class ToClientsSender implements Runnable {
	private final GameStateMonitor gameStateMonitor;

	public ToClientsSender(GameStateMonitor gameStateMonitor) {
		this.gameStateMonitor = gameStateMonitor;
	}

	@Override
	public void run() {
		while (!gameStateMonitor.isGameOver()) {
			gameStateMonitor.broadcastState();
		}
	}

}
