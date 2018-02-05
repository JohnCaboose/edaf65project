package server.network;

import java.net.Socket;

import common.model.PlayerIdentity;
import server.model.GameStateMonitor;

public class ToClientSender implements Runnable {
	
	private final PlayerIdentity playerIdentity;
	private final GameStateMonitor gameStateMonitor;
	private final Socket socket;
	//TODO: how should we handle the socket closing stuff in this and the other client-interacting class?
	
	
	public ToClientSender(PlayerIdentity playerIdentity, GameStateMonitor gameStateMonitor, Socket socket) {
		this.playerIdentity = playerIdentity;
		this.gameStateMonitor = gameStateMonitor;
		this.socket = socket;
	}

	@Override
	public void run() {
		while(true) {
			String stateAsJson = gameStateMonitor.getStateAsJson(playerIdentity);
			//TODO: Send the gamestate to outputstream, remember to flush
		}
	}
	
}
