package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import client.model.DirectionMonitor;
import common.model.Snake;

/**
 * Run this class to start a client. 
 * Takes ip and port of server as input.
 */
public class RunClient {
	public static void main(String[] args) {
		//TODO Connect to server, show view etc.
		String hostname = null;
		int port = -1;
		
		/* Parses the two arguments and stores them in hostname and port variables. */
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
		/* End of argument parse. */
		
		Socket socket = null;
		try {
			socket = new Socket(hostname, port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.err.println("Host not found:" + hostname);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("An IO Exception occured.");
			e.printStackTrace();
			System.exit(1);
		}
	}
}
