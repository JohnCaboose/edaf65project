package server.network;

import common.model.PlayerIdentity;
import server.model.ServerGameStateMonitor;

public class ToClientsSender implements Runnable {
	private final ServerGameStateMonitor gameStateMonitor;

	public ToClientsSender(ServerGameStateMonitor gameStateMonitor) {
		this.gameStateMonitor = gameStateMonitor;
	}

	@Override
	public void run() {
		do {
			gameStateMonitor.broadcastState();
		} while (!gameStateMonitor.isGameOver());
		//Kill all connections as they are no longer needed
		for(PlayerIdentity pi : PlayerIdentity.values()) {
			gameStateMonitor.removePlayer(pi);
		}
		System.out.println("ToClientsSender shutting down.");
	}

}
