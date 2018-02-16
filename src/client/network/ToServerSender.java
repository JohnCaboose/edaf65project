package client.network;
import java.io.IOException;
import java.net.Socket;

import com.google.gson.Gson;

import client.model.DirectionMonitor;
import common.model.Direction;
import common.model.PacketHandler;
import common.model.PacketType;

public class ToServerSender implements Runnable {
	private final DirectionMonitor directionMonitor;
	private final Gson gson = new Gson();
	private Socket socket = null;
	
	public ToServerSender(DirectionMonitor directionMonitor, Socket socket) {
		this.directionMonitor = directionMonitor;
		this.socket = socket;
	}
	
	@Override
	public void run() {
		while (directionMonitor.directionExists()) {
			while(!directionMonitor.hasNewDirection()) {
				try {
					wait(); 
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			String message = PacketHandler.createProtocolPacket(PacketType.DIRECTION,
					gson.toJson(directionMonitor.getDirection(), Direction.class));
			try {
				if (socket != null) {
					try {
						socket.getOutputStream().write(message.getBytes());
						socket.getOutputStream().flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				directionMonitor.directionSent();
			} catch (Exception e) {
				System.out.println("Error in DirectionMonitor");
			}
		}
		System.err.println("ToServerSender just died.");
		//TODO: fix bug so server sender doesn't immediately die.....
	}
}
