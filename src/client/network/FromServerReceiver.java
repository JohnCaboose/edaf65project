package client.network;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import client.ClientGameStateMonitor;
import common.model.GameState;
import common.model.PacketHandler;
import common.model.PacketType;

public class FromServerReceiver extends Thread {
	private final ClientGameStateMonitor stateMonitor;
	private Socket socket;
	private GameState gameState = null;

	public FromServerReceiver(ClientGameStateMonitor stateMonitor, Socket socket) {
		this.stateMonitor = stateMonitor;
		this.socket = socket;
	}

	public void run() {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
			while (!isInterrupted()) {
				StringBuilder sb = new StringBuilder();
				String packet;

				int c;
				while ((c = reader.read()) != -1) {
					sb.append((char) c);
				}

				if (PacketHandler.stringContainsProtocolPacket(sb.toString())) {
					packet = PacketHandler.extractFirstPacketFromMultiPacketStringBuilder(sb);
					PacketType type = PacketHandler.getProtocolPacketType(packet);

					switch (type) {
					case GAMESTATE:
						gameState = PacketHandler.getStateFromProtocolPacket(packet);
						stateMonitor.setGameState(gameState);
						break;
					case PLAYERIDENTITY:
						// TODO: give the player identity to the classes which
						// need it
						break;
					default:
						System.err.println("Packet type " + type + " not supported by client.");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
