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
	

	public FromServerReceiver(ClientGameStateMonitor stateMonitor, Socket socket) {
		this.stateMonitor = stateMonitor;
		this.socket = socket;
	}

	public void run() {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
			StringBuilder sb = new StringBuilder();
			while (true) {
				int c;
				while ((c = reader.read()) != '>') {
					if(c == -1){
						break;
					}
					sb.append((char) c);
					//System.err.println("Read: " + sb.toString());
				}
				if(c == -1){
					//TODO disconnect ? 
					System.err.println("FromServerReceiver got end of stream, is killing itself.");
					break;
				}
				sb.append('>');
				System.out.println("Client read end of tag: " + sb.toString());
				if (PacketHandler.stringContainsProtocolPacket(sb.toString())) {
					String packet = PacketHandler.extractFirstPacketFromMultiPacketStringBuilder(sb);
					PacketType type = PacketHandler.getProtocolPacketType(packet);
					
					switch (type) {
						case GAMESTATE:
							GameState gameState = PacketHandler.getStateFromProtocolPacket(packet);
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
