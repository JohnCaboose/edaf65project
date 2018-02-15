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
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			
			while(true) {
				StringBuilder sb = new StringBuilder();
				String packet ="";
				
				int c;
				while ((c = reader.read()) != -1) {
					sb.append((char) c);
				}
				
				if(PacketHandler.stringContainsProtocolPacket(sb.toString())) {
					packet = PacketHandler.extractFirstPacketFromMultiPacketStringBuilder(sb);
					PacketType type = PacketHandler.getProtocolPacketType(packet);
					
					if(type == PacketType.DIRECTION) {
						Direction direction = PacketHandler.getDirectionFromProtocolPacket(packet);
						if(direction != null) {
							gameStateMonitor.changePlayerDirection(playerIdentity, direction);
						}
					}
				}
				
				//TODO remove the sleeping here once actual reading is taking place
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			gameStateMonitor.removePlayer(playerIdentity);
			//e.printStackTrace();
		}
	}

}
