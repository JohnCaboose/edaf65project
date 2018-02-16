package server.model;

import java.io.IOException;

import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;

import com.google.gson.Gson;


import common.model.Direction;
import common.model.PlayerIdentity;
import common.model.Snake;
import common.model.GameState;
import common.model.PacketHandler;
import common.model.PacketType;
import server.exceptions.GameIsFullException;
import server.network.FromClientReceiver;

/**
 * The servers game state, implemented as a monitor
 */
public class ServerGameStateMonitor {
	private final GameState gameState;
	private String jsonState;
	private long lastStateSent;
	private final Map<PlayerIdentity,Socket> playerSockets;
	private final Gson gson = new Gson();
	
	/**
	 * Creates a new monitor that handles the game state-machine
	 * @param playerCount the amount of players for the game
	 * @param boardWidth width of the game board
	 * @param boardHeight height of the game board
	 */
	public ServerGameStateMonitor(int playerCount, int boardWidth, int boardHeight) {
		
		//Coordinate.width = boardWidth;
		//Coordinate.height = boardHeight;
		
		lastStateSent = -1;
		playerSockets = new TreeMap<PlayerIdentity, Socket>();
		
		switch(playerCount) {
			case 4:
				playerSockets.put(PlayerIdentity.FOUR, null);
			case 3:
				playerSockets.put(PlayerIdentity.THREE, null);
			case 2:
				playerSockets.put(PlayerIdentity.TWO, null);
			case 1:
				playerSockets.put(PlayerIdentity.ONE, null);
			default:
				break;
		}
		
		
		
		gameState = new GameState(playerCount,boardWidth,boardHeight);
		updateJSONState();
	}
	
	/**
	 * Updates the game board positions and other game state variables based on current player input and previous state.
	 * This corresponds to a game logic "tick".
	 */
	public synchronized void updateGameState() {
		System.out.println(String.format("#%d Game State being calculated.", (gameState.getTickCounter()+1)));
		gameState.performGameTick();
		updateJSONState();
		this.notifyAll();
	}
	
	/**
	 * Adds a new player to the game, sends the playerIdentity to the player
	 * @param socket the socket of the player
	 * @return the identity of the player added
	 */
	public synchronized PlayerIdentity addPlayer(Socket socket) throws GameIsFullException {	
		for(PlayerIdentity playerIdentity : playerSockets.keySet()) {
			if(playerSockets.get(playerIdentity) == null) {
				System.out.println("Adding new player " + playerIdentity);
				playerSockets.put(playerIdentity, socket);
				new Thread(new FromClientReceiver(playerIdentity, this, socket)).start();
				return playerIdentity;
			}
		}
		throw new GameIsFullException();
	}
	
	/**
	 * Removes player from game, killing their snake if the game is running or otherwise waiting for more players
	 * @param playerIdentity the identity of the player
	 */
	public synchronized void removePlayer(PlayerIdentity playerIdentity) {
		Socket socket = playerSockets.get(playerIdentity);
		if(socket != null) {
			System.out.println("Removing player " + playerIdentity.name());
			try {
				socket.shutdownInput();
				socket.close();
			} catch (IOException e) {
				//Doesn't matter
			}
			playerSockets.put(playerIdentity, null);
			gameState.getPlayerSnakes().get(playerIdentity.ordinal()).kill();
		}
	}
	/**
	 * Removes player from game, killing their snake if the game is running or otherwise waiting for more players
	 * @param socket the socket of the player
	 */
	public synchronized void removePlayer(Socket socket) {
		for(PlayerIdentity pi : playerSockets.keySet()) {
			if(socket.equals(playerSockets.get(pi))) {
				removePlayer(pi);
				return;
			}
		}
	}
	
	
	/**
	 * Check if all players have connected to the session
	 * @return true if all players are connected, false otherwise
	 */
	public synchronized boolean allPlayersConnected() {
		return !playerSockets.containsValue(null);
	}
	
	/**
	 * Serializes the player snakes (the game state) as a JSON string
	 * @return the JSON string for the snakes of the players
	 */
	public synchronized void broadcastState() {
		while(gameState.getTickCounter() <= lastStateSent) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		lastStateSent = gameState.getTickCounter();
		System.out.println(String.format("#%d Game State broadcasting. " + (gameState.isGameOver() ? "Game is over." : ""), gameState.getTickCounter()));
		//TODO consider moving outside synchronized zone for performance, this should include making a new connectionmonitor
		byte[] packet = PacketHandler.createProtocolPacket(PacketType.GAMESTATE, jsonState).getBytes();
		for(Socket socket : playerSockets.values()) {
			if(socket != null) {
				try {
					socket.getOutputStream().write(packet);
					socket.getOutputStream().flush();
				} catch (IOException e) {
					this.removePlayer(socket);
					e.printStackTrace();
				}
			}
		}
	}
	
	private synchronized void updateJSONState() {
		jsonState = gson.toJson(gameState, GameState.class);
	}
	
	/**
	 * Changes the direction of one of the players
	 * @param player the PlayerIdentity of the player to change direction for 
	 * @param direction the direction to change to
	 */
	public synchronized void changePlayerDirection(PlayerIdentity player, Direction direction) {
		if(player.ordinal() < gameState.getPlayerSnakes().size()) {
			Snake snake = gameState.getPlayerSnakes().get(player.ordinal());
			Direction oldDirection = snake.getDirection();
			snake.setDirection(direction);
			Direction newDirection = snake.getDirection();
			System.out.println("Changed direction of Snake " + player.name() +" from " + oldDirection + " to " + newDirection);
		}
	}

	/**
	 * Uses isGameOver() from GameState
	 * @return true if game is over, false if not
	 */
	public synchronized boolean isGameOver() {
		return this.gameState.isGameOver();
	}
	/**
	 * Get the current state of the game as a JSON object string
	 * @return JSON string of the current game state
	 */
	public synchronized String getJsonState() {
		return jsonState;
	}

}
