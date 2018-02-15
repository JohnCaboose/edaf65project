package client.model;

import java.io.IOException;
import java.net.Socket;
import com.google.gson.Gson;

import client.controller.Input;
import client.controller.UserInputInterpreter.Key;
import common.model.Direction;
import common.model.PacketHandler;
import common.model.PacketType;

//TODO: When user inputs new direction newDirection in directionMonitor should be set to true and notifyAll() run

public class DirectionMonitor {
	private Direction direction = null;
	private Input input;
	private Socket socket;
	private final Gson gson = new Gson();
	private boolean hasNewDirection = false;

	public DirectionMonitor(Socket socket) {
		this.socket = socket;
	}

	public synchronized void broadcastDirection() {
		String message = PacketHandler.createProtocolPacket(PacketType.DIRECTION, gson.toJson(direction, Direction.class));
		try {
			if(socket != null) {
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
	 * Store the parameter in this monitor in some way. TODO: implement this
	 */
	public synchronized void send(Key newInput) {
		// TODO Auto-generated method stub

	}
}
