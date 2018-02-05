package server.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;

import common.model.PlayerIdentity;
import server.model.GameStateMonitor;

public class GameServer implements Runnable {
	private final int port;
	private final int playerCount;
	private final int width, height;
	
	private final int FIRST_LEGAL_PORT = 1024;
	private final int LAST_LEGAL_PORT = 65535;
	private final int LOWEST_LEGAL_PLAYERCOUNT = 1;
	private final int HIGHEST_LEGAL_PLAYERCOUNT = 4;
	private final int SMALLEST_SIZE_FIELD = 20;
	
	private final GameStateMonitor gameStateMonitor;
	
	/**
	 * 
	 * @param port server will listen to connections for this socket
	 * @param playerCount the amount of players for the game
	 * @param width size of game board width
	 * @param height size of game board height
	 * @throws IllegalArgumentException when any of the parameters are not in a permissable range
	 */
	public GameServer(int port, int playerCount, int width, int height) throws IllegalArgumentException{
		if(port >= FIRST_LEGAL_PORT && port <= LAST_LEGAL_PORT) {
			this.port = port;
		} else {
			throw new IllegalArgumentException(String.format("Port supplied (%d) is out of range.", port));
		}
		if(playerCount >= LOWEST_LEGAL_PLAYERCOUNT && playerCount <= HIGHEST_LEGAL_PLAYERCOUNT) {
			this.playerCount = playerCount;
		} else {
			throw new IllegalArgumentException(String.format("Amount of players supplied (%d) is out of range.", playerCount));
		}
		if(width >= SMALLEST_SIZE_FIELD && height >= SMALLEST_SIZE_FIELD) {
			this.width = width;
			this.height = height;
		} else {
			throw new IllegalArgumentException(String.format("Size of field supplied (%d,%d) must not be too small (minimum side length is %d).", width,height,SMALLEST_SIZE_FIELD));
		}
		
		gameStateMonitor = new GameStateMonitor(this.playerCount, this.width, this.height);
		
	}
	

	@Override
	public void run() {
		try (ServerSocket serverSocket = new ServerSocket(port)){
			Map<PlayerIdentity,Boolean> connectedPlayers = new TreeMap<PlayerIdentity, Boolean>();
			switch(playerCount) {
				case 4:
					connectedPlayers.put(PlayerIdentity.FOUR, false);
				case 3:
					connectedPlayers.put(PlayerIdentity.THREE, false);
				case 2:
					connectedPlayers.put(PlayerIdentity.TWO, false);
				case 1:
					connectedPlayers.put(PlayerIdentity.ONE, false);
				default:
					break;
			}
			while(connectedPlayers.containsValue(false)) {
				//TODO: wait for players to connect
				Socket socket = serverSocket.accept();
				//Spin up threads for the players
								
			}
			while(true) {
				//TODO: run the game now that all players are connected
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Starts the game server, this is run in the terminal separately from the client.
	 * (The client being able to boot up a server will be added in a later step.)
	 * 
	 * @param args the parameters for starting the game server, defined as:<br/>
	 * args[0] = portnumber (values from ? to ? acceptable, inclusive) <br/>
	 * args[1] = number of players (values from 1 to 4 acceptable, inclusive) <br/>
	 * args[2] = width of game board <br/>
	 * args[3] = height of game board <br/>
	 *  
	 */
	public static void main(String[] args) {
		if(args.length == 4) {
			int port = Integer.parseInt(args[0]);
			int playerCount = Integer.parseInt(args[1]);
			int width = Integer.parseInt(args[2]);
			int height = Integer.parseInt(args[3]);
			try {
				GameServer gameServer = new GameServer(port, playerCount, width, height);
				new Thread(gameServer).start();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		} else {
			System.err.println("Wrong number of arguments. See javadoc for class GameServer for more information.");
		}
		
	}
}
