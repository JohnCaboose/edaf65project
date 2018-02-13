package client.model;

import java.io.OutputStream;
import java.net.Socket;

import client.controller.Input;
import common.model.Direction;
import common.model.PacketHandler;
import common.model.PacketType;
import common.model.Snake;

public class DirectionMonitor {
	private Direction direction;
	private Input input;
	private OutputStream outputStream;
	private Socket socket;

	public DirectionMonitor(Snake snake) {
		input = new Input(snake);
		socket = new Socket();
	}

	public synchronized void broadcastDirection() {
		String message = PacketHandler.createProtocolPacket(PacketType.DIRECTION, direction.toString());
		try {

		} catch (Exception e) {
			System.out.println("Error in DirectionMonitor");
		}
		notifyAll();
	}

	public synchronized boolean directionExists() {
		return direction != null;
	}

	/**
	 * Store the parameter in this monitor in some way. TODO: implement this
	 */
	public synchronized void send(char newInput) {
		// TODO Auto-generated method stub

	}
}
