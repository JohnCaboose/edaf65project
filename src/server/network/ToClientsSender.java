package server.network;

import java.net.Socket;

import common.model.PlayerIdentity;
import server.model.GameStateMonitor;

public class ToClientsSender implements Runnable {
	private final GameStateMonitor gameStateMonitor;
	//TODO: how should we handle the socket closing stuff in this and the other client-interacting class?
	
	
	public ToClientsSender(GameStateMonitor gameStateMonitor) {
		this.gameStateMonitor = gameStateMonitor;
	}

	@Override
	public void run() {
		while(true) {
			gameStateMonitor.broadcastState();
		}
	}
	
}
