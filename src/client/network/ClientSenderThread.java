package client.network;

import java.io.IOException;
import java.net.Socket;

import com.google.gson.Gson;

import client.model.DirectionMonitor;
import common.model.Direction;
import common.model.PacketHandler;
import common.model.PacketType;

public class ClientSenderThread extends Thread {
	private final DirectionMonitor directionMonitor;
	private final Gson gson = new Gson();
	private Socket socket = null;

	public ClientSenderThread(DirectionMonitor directionMonitor, Socket socket) {
		this.directionMonitor = directionMonitor;
		this.socket = socket;
	}

	public void run() {
		while (true) {
			// TODO: Make it so that you don't have to press a button after game
			// is over for this thread to die
			Direction direction = directionMonitor.getDirection();
			String message = PacketHandler.createProtocolPacket(PacketType.DIRECTION,
					gson.toJson(direction, Direction.class));

			if (socket != null) {
				if (socket.isClosed()) {
					System.err.println("ToServerSender dying");
					break;
				}
				try {
					socket.getOutputStream().write(message.getBytes());
					socket.getOutputStream().flush();
				} catch (IOException e) {
					e.printStackTrace();
					System.err.println("ToServerSender dying");
					break;
				}
			}
			directionMonitor.directionSent();

		}
		System.err.println("ToServerSender shutting down.");
	}
}
