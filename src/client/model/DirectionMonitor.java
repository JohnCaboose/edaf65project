package client.model;

import java.io.IOException;
import java.net.Socket;

import com.google.gson.Gson;

import common.model.Direction;
import common.model.PacketHandler;
import common.model.PacketType;

public class DirectionMonitor {
	private Direction direction = null;
	private Socket socket;
	private final Gson gson = new Gson();
	private boolean hasNewDirection = false;

	public DirectionMonitor(Socket socket) {
		this.socket = socket;
	}

	public synchronized void broadcastDirection() {
		String message = PacketHandler.createProtocolPacket(PacketType.DIRECTION,
				gson.toJson(direction, Direction.class));
		try {
			if (socket != null) {
				try {
					socket.getOutputStream().write(message.getBytes());
					socket.getOutputStream().flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			hasNewDirection = false;
		} catch (Exception e) {
			System.out.println("Error in DirectionMonitor");
		}
	}

	public synchronized boolean directionExists() {
		return direction != null;
	}

	public synchronized boolean hasNewDirection() {
		return hasNewDirection;
	}

	/**
	 * Submits a new direction to this monitor. The new direction will then be
	 * collected by another thread from this monitor.
	 */
	public synchronized void submit(Direction newInput) {
		direction = newInput;
		hasNewDirection = true;
		notifyAll();
	}
}
