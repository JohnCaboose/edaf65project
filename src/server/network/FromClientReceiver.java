package server.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import common.model.PlayerIdentity;
import server.model.GameStateMonitor;

public class FromClientReceiver implements Runnable {

	private final PlayerIdentity playerIdentity;
	private final GameStateMonitor gameStateMonitor;
	private final Socket socket;

	public FromClientReceiver(PlayerIdentity playerIdentity, GameStateMonitor gameStateMonitor, Socket socket) {
		this.playerIdentity = playerIdentity;
		this.gameStateMonitor = gameStateMonitor;
		this.socket = socket;
	}

	@Override
	public void run() {
		try (InputStream is = socket.getInputStream()){
			//TODO: Use a good buffered stream instead so that json objects can be read as easily as possible using gson?
			while(true) {
				//TODO: Read direction from stream
				//TODO: Update gamestatemonitor with new direction, when updating do no allow snake to walk "backwards" into itself, ignore the command if so
			}
		} catch (IOException e) {
			gameStateMonitor.removePlayer(playerIdentity);
			//e.printStackTrace();
		}
	}

}
