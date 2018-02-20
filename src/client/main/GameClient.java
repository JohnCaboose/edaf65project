package client.main;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import client.model.ClientGameStateMonitor;
import client.model.DirectionMonitor;
import client.network.ServerReceiverThread;
import client.network.ServerSenderThread;
import client.view.SnakePainterThread;
import client.view.View;

/**
 * Run this class to start a client. Takes ip and port of server as input.
 */
public class GameClient {
	public static void main(String[] args) {
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

		/* Starts communication with server. */
		try {
			Socket socket = new Socket(hostname, port);
			DirectionMonitor directionMonitor = new DirectionMonitor();
			ClientGameStateMonitor stateMonitor = new ClientGameStateMonitor();
			/*
			 * TODO: send correct color to view constructor
			 */
			View view = new View(directionMonitor, "blue");
			new ServerReceiverThread(stateMonitor, socket).start();
			new ServerSenderThread(directionMonitor, socket).start();
			new SnakePainterThread(stateMonitor, view).start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.err.println("Host not found:" + hostname);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("An IO Exception occurred.");
			e.printStackTrace();
			System.exit(1);
		}
	}
}
