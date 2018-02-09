package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

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
			System.out.println("Not enough parameters: " + args.length);
			System.exit(1);
		}
		hostname = args[0];
		try {
			port = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			System.out.println("Second parameter is not a number.");
			System.exit(1);
		}
		/* End of argument parse. */
		
		try {
			Socket example = new Socket(hostname, port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
