package client.network;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import client.model.DirectionMonitor;
import common.model.GameState;
import common.model.PacketHandler;
import common.model.PacketType;
import sun.misc.IOUtils;

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
		try {
			inputStream = socket.getInputStream();
			while (true) {
				BufferedReader buffererdreader = new BufferedReader(new InputStreamReader(inputStream));
				String packet = "";//Will fix it
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
