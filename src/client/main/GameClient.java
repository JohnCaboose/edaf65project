package client.main;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import client.model.ClientGameStateMonitor;
import client.model.DirectionMonitor;
import client.model.PlayerIdentityColorConverter;
import client.network.ClientReceiverThread;
import client.network.ClientSenderThread;
import client.view.SnakePainterThread;
import client.view.View;

/**
 * Run this class to start a client. Takes ip and port of server as input.
 */
public class GameClient {
	public static void main(String[] args) throws InterruptedException {
		String hostname = null;
		int port = -1;

		/*
		 * Parses the two arguments and stores them in hostname and port
		 * variables.
		 */
		if (args.length < 2) {
			System.err.println("Not enough parameters: " + args.length);
			System.exit(1);
		}
		hostname = args[0];
		try {
			port = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			System.err.println("Second parameter is not a number.");
			System.exit(1);
		}

		/* Starts communication with the game server. */
		boolean reconnect = false;
		do {
			try {
				Socket socket = new Socket(hostname, port);
				reconnect = false;
				DirectionMonitor directionMonitor = new DirectionMonitor();
				ClientGameStateMonitor stateMonitor = new ClientGameStateMonitor();
				new ClientReceiverThread(stateMonitor, socket).start();
				new ClientSenderThread(directionMonitor, socket).start();
				String color = PlayerIdentityColorConverter.getColor(stateMonitor.getPlayerIdentity());
				View view = new View(directionMonitor, color);
				new SnakePainterThread(stateMonitor, view).start();
				System.out.println("Connected successfully to " + hostname + " using port " + port + ".");
			} catch (UnknownHostException e) {
				System.err.println("Host not found:" + hostname);
				System.exit(1);
			} catch (IOException e) {
				System.err.println("Error: connection refused.");
				System.err.println("Retrying in 5 seconds...");
				reconnect = true;
				Thread.sleep(5000);
			}
		} while (reconnect);
	}
}
