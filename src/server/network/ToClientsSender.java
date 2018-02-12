package server.network;

import common.model.PlayerIdentity;
import server.model.GameStateMonitor;

public class ToClientsSender implements Runnable {
	private final GameStateMonitor gameStateMonitor;

	public ToClientsSender(GameStateMonitor gameStateMonitor) {
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
		
	}

}
