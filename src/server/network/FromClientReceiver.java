package server.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import common.model.Direction;
import common.model.PacketHandler;
import common.model.PacketType;
import common.model.PlayerIdentity;
import server.model.ServerConnectionMonitor;
import server.model.ServerGameStateMonitor;

public class FromClientReceiver implements Runnable {

	private final PlayerIdentity playerIdentity;
	private final ServerGameStateMonitor gameStateMonitor;
	private final Socket socket;
	private final ServerConnectionMonitor connectionMonitor;

	public FromClientReceiver(PlayerIdentity playerIdentity, ServerGameStateMonitor gameStateMonitor, Socket socket, ServerConnectionMonitor connectionMonitor) {
		this.playerIdentity = playerIdentity;
		this.gameStateMonitor = gameStateMonitor;
		this.socket = socket;
		this.connectionMonitor = connectionMonitor;
	}

	@Override
	public void run() {
		try (InputStream is = socket.getInputStream()){
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			
			StringBuilder sb = new StringBuilder();
			while(true) {
				int c;
				while ((c = reader.read()) != '>') {
					if(c == -1){
						throw new IOException("Player " + playerIdentity +  " disconnected.");
					}
					sb.append((char) c);
				}
				/*if(c == -1){
					break;
				}*/
				sb.append('>');
				
				if(PacketHandler.stringContainsProtocolPacket(sb.toString())) {
					String packet = PacketHandler.extractFirstPacketFromMultiPacketStringBuilder(sb);
					PacketType type = PacketHandler.getProtocolPacketType(packet);
					
					if(type == PacketType.DIRECTION) {
						Direction direction = PacketHandler.getDirectionFromProtocolPacket(packet);
						if(direction != null) {
							gameStateMonitor.changePlayerDirection(playerIdentity, direction);
						}
					}
				}
			}
		} catch (IOException e) {
			connectionMonitor.removePlayer(playerIdentity);
		}
		System.out.println(String.format("FromClientReceiver %s shutting down.", playerIdentity.name()));
	}
	
}
