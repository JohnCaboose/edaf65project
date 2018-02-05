package server.network;

import java.net.Socket;

import common.model.PlayerIdentity;
import server.model.GameStateMonitor;

public class ConnectionToClient implements Runnable {
	
	private final PlayerIdentity playerIdentity;
	private final GameStateMonitor gameStateMonitor;
	private final Socket socket;
	
	
	public ConnectionToClient(PlayerIdentity playerIdentity, GameStateMonitor gameStateMonitor, Socket socket) {
		this.playerIdentity = playerIdentity;
		this.gameStateMonitor = gameStateMonitor;
		this.socket = socket;
	}

	@Override
	public void run() {
		//TODO: Read directions from inputstream
		//TODO: Send the gamestate to outputstream
		//TODO: Consider reading and sending to be implemented as different threads as they should be able to run independently of one another?
	}

}
