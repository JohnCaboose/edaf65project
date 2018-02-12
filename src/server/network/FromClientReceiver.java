package server.network;

import java.io.IOException;
import java.io.InputStream;
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
			while(true) {
				//TODO read an actual packet from stream here and put it in packet
				
				String packet = "<GAMEPACKET TYPE=DIRECTION>\"LEFT\"</GAMEPACKET>";
				
				if(PacketHandler.stringContainsProtocolPacket(packet)) {
					if(PacketHandler.getProtocolPacketType(packet) == PacketType.DIRECTION) {
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
