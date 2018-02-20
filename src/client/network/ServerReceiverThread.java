package client.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import client.ClientGameStateMonitor;
import common.model.GameState;
import common.model.PacketHandler;
import common.model.PacketType;

public class ServerReceiverThread extends Thread {
	private final ClientGameStateMonitor stateMonitor;
	private Socket socket;
	

	public ServerReceiverThread(ClientGameStateMonitor stateMonitor, Socket socket) {
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
						throw new IOException("FromServerReceiver got end of stream.");
					}
					sb.append((char) c);
					//System.err.println("Read: " + sb.toString());
				}
				sb.append('>');
				//System.out.println("Client read end of tag: " + sb.toString());
				if (PacketHandler.stringContainsProtocolPacket(sb.toString())) {
					String packet = PacketHandler.extractFirstPacketFromMultiPacketStringBuilder(sb);
					PacketType type = PacketHandler.getProtocolPacketType(packet);
					
					switch (type) {
						case GAMESTATE:
							GameState gameState = PacketHandler.getStateFromProtocolPacket(packet);
							stateMonitor.setGameState(gameState);
							break;
						case PLAYERIDENTITY:
							stateMonitor.setPlayerIdentity(PacketHandler.getPlayerIdentityFromProtocolPacket(packet));
							break;
						default:
							System.err.println("Packet type " + type + " not supported by client.");
							break;
					}
				}
			}
		} catch (IOException e) {
			if(e.getMessage().equals("FromServerReceiver got end of stream.")) {
				System.err.println("FromServerReceiver got end of stream.");
			}else {
				e.printStackTrace();
			}
			
		}
		System.err.println("FromServerReceiver shutting down.");
	}
}
