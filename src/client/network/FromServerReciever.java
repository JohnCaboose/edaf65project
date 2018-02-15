package client.network;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import client.model.DirectionMonitor;
import common.model.GameState;
import common.model.PacketHandler;
import common.model.PacketType;

public class FromServerReciever extends Thread {
	private final DirectionMonitor directionMonitor;
	private Socket socket;
	private InputStream inputStream;
	private GameState gameState;

	public FromServerReciever(DirectionMonitor directionMonitor, Socket socket) {
		this.directionMonitor = directionMonitor;
		this.socket = socket;
	}

	public void run() {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
			while (true) {
				StringBuilder sb = new StringBuilder();
				int c;
				while ((c = reader.read()) != -1) {
					sb.append((char) c);
				}
				
				//TODO: Analyze data from server to identify packets;
				
				String packet = "";
				
				if(PacketHandler.stringContainsProtocolPacket(packet)){
					if(PacketHandler.getProtocolPacketType(packet) == PacketType.GAMESTATE){
						if(gameState != null){
							gameState = PacketHandler.getStateFromProtocolPacket(packet);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
