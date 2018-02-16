package server.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import common.constants.Constants;
import server.model.ServerGameStateMonitor;

public class GameServer implements Runnable {
	private final int port;
	private final int playerCount;
	private final int width, height;

	private final int FIRST_LEGAL_PORT = 1024;
	private final int LAST_LEGAL_PORT = 65535;
	private final int LOWEST_LEGAL_PLAYERCOUNT = 1;
	private final int HIGHEST_LEGAL_PLAYERCOUNT = 4;
	private final int SMALLEST_SIZE_FIELD = 20;
	private final int MILLIS_PER_STATE_FRAME = 400;

	private final ServerGameStateMonitor gameStateMonitor;

	/**
	 * 
	 * @param port
	 *            server will listen to connections for this socket
	 * @param playerCount
	 *            the amount of players for the game
	 * @param width
	 *            size of game board width
	 * @param height
	 *            size of game board height
	 * @throws IllegalArgumentException
	 *             when any of the parameters are not in a permissable range
	 */
	public GameServer(int port, int playerCount, int width, int height) throws IllegalArgumentException {
		if (port >= FIRST_LEGAL_PORT && port <= LAST_LEGAL_PORT) {
			this.port = port;
		} else {
			throw new IllegalArgumentException(String.format("Port supplied (%d) is out of range.", port));
		}
		if (playerCount >= LOWEST_LEGAL_PLAYERCOUNT && playerCount <= HIGHEST_LEGAL_PLAYERCOUNT) {
			this.playerCount = playerCount;
		} else {
			throw new IllegalArgumentException(
					String.format("Amount of players supplied (%d) is out of range.", playerCount));
		}
		if (width >= SMALLEST_SIZE_FIELD && height >= SMALLEST_SIZE_FIELD) {
			this.width = width;
			this.height = height;
		} else {
			throw new IllegalArgumentException(
					String.format("Size of field supplied (%d,%d) must not be too small (minimum side length is %d).",
							width, height, SMALLEST_SIZE_FIELD));
		}

		gameStateMonitor = new ServerGameStateMonitor(this.playerCount, this.width, this.height);

	}

	@Override
	public void run() {
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			// Wait for all players to connect
			new Thread(new ToClientsSender(gameStateMonitor)).start();
			while (!gameStateMonitor.allPlayersConnected()) {
				Socket socket = serverSocket.accept();
				gameStateMonitor.addPlayer(socket);
			}
			// Start game
			while (true) {
				long frameStartTime = System.currentTimeMillis();
				gameStateMonitor.updateGameState();
				if(gameStateMonitor.isGameOver()) {
					break;
				}
				try {
					Thread.sleep(frameStartTime + MILLIS_PER_STATE_FRAME - System.currentTimeMillis());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		//Game is over
		System.out.println("Game over, shutting down server.");
		//TODO: this shouldn't really be needed later on as fromclientreceiver should be updated to kill itself automatically 
		System.exit(0);
	}

	/**
	 * Starts the game server, this is run in the terminal separately from the
	 * client. (The client being able to boot up a server will be added in a
	 * later step.)
	 * Note that game board size is currently fixed.
	 * 
	 * @param args
	 *            the parameters for starting the game server, defined as:<br/>
	 *            args[0] = portnumber (values from 1024 to 65535 acceptable,
	 *            inclusive) <br/>
	 *            args[1] = number of players (values from 1 to 4 acceptable,
	 *            inclusive) <br/>

	 * 
	 */
	public static void main(String[] args) {
		if (args.length == 2) {
			int port = Integer.parseInt(args[0]);
			int playerCount = Integer.parseInt(args[1]);
			int width = Constants.BOARDWIDTH;
			int height = Constants.BOARDHEIGHT;
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
