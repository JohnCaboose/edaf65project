package client.model;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import com.google.gson.Gson;

import client.controller.Input;
import client.controller.UserInputInterpreter.Key;
import common.model.Direction;
import common.model.GameState;
import common.model.PacketHandler;
import common.model.PacketType;
import common.model.Snake;

public class DirectionMonitor {
	private Direction direction;
	private Input input;
	private Socket socket;
	private final Gson gson = new Gson();

	public DirectionMonitor(Socket socket) {
		this.socket = socket;
	}

	public synchronized void broadcastDirection() {
		String message = PacketHandler.createProtocolPacket(PacketType.DIRECTION, direction.toString());
		try {
			if(socket != null) {
				try {
					socket.getOutputStream().write(gson.toJson(direction, Direction.class).getBytes());
					socket.getOutputStream().flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			System.out.println("Error in DirectionMonitor");
		}
	}

	public synchronized boolean directionExists() {
		return direction != null;
	}

	/**
	 * Store the parameter in this monitor in some way. TODO: implement this
	 */
	public synchronized void send(Key newInput) {
		// TODO Auto-generated method stub

	}
}
