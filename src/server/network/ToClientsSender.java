package server.network;

import com.google.gson.Gson;

import common.model.GameState;
import common.model.PacketHandler;
import common.model.PacketType;
import common.model.PlayerIdentity;
import server.model.ServerConnectionMonitor;
import server.model.ServerGameStateMonitor;

public class ToClientsSender implements Runnable {
	private final ServerGameStateMonitor gameStateMonitor;
	private final ServerConnectionMonitor connectionMonitor;
	private final Gson gson = new Gson();

	public ToClientsSender(ServerGameStateMonitor gameStateMonitor, ServerConnectionMonitor connectionMonitor) {
		this.gameStateMonitor = gameStateMonitor;
		this.connectionMonitor = connectionMonitor;
	}

	@Override
	public void run() {
		do {
			String stateToSend = gameStateMonitor.getNewJsonState();
			//This outprint could be removed for increased performance.
			GameState gameState =  gson.fromJson(stateToSend, GameState.class);
			System.out.println(String.format("#%d Game State broadcasting. " + (gameState.isGameOver() ? "Game is over." : ""), gameState.getTickCounter()));
			String packet = PacketHandler.createProtocolPacket(PacketType.GAMESTATE, stateToSend);
			connectionMonitor.sendPacketToAllPlayers(packet);
		} while (!gameStateMonitor.isGameOver());
		//Kill all connections as they are no longer needed
		for(PlayerIdentity pi : PlayerIdentity.values()) {
			connectionMonitor.removePlayer(pi);
			//gameStateMonitor.killSnake(pi);
		}
		System.out.println("ToClientsSender shutting down.");
	}

}
