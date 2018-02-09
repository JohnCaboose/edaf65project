package server.model;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.gson.Gson;

import common.model.Coordinate;
import common.model.Direction;
import common.model.PlayerIdentity;
import common.model.Snake;
import server.network.FromClientReceiver;
import common.model.GameState;
import common.model.PacketHandler;
import common.model.PacketType;

/**
 * The servers game state, implemented as a monitor
 */
public class GameStateMonitor {
	//TODO: use snakes directions instead of this variable
	private final List<Direction> playerDirections;
	private final GameState gameState;
	private String jsonState;
	private int lastStateSent;
	private final Map<PlayerIdentity,Socket> playerSockets;
	private final Gson gson = new Gson();
	
	/**
	 * Creates a new monitor that handles the game state-machine
	 * @param playerCount the amount of players for the game
	 * @param boardWidth width of the game board
	 * @param boardHeight height of the game board
	 */
	public GameStateMonitor(int playerCount, int boardWidth, int boardHeight) {
		
		Coordinate.width = boardWidth;
		Coordinate.height = boardHeight;
		
		lastStateSent = -2;
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
		
		//Define the initial spawn corners
		ArrayList<Coordinate> initialCorners = new ArrayList<Coordinate>();
		ArrayList<Direction> initialDirections = new ArrayList<Direction>();
		initialCorners.add(new Coordinate(0,boardHeight-1));
		initialCorners.add(new Coordinate(boardWidth-1, 0));
		initialCorners.add(new Coordinate(0, 0));
		initialCorners.add(new Coordinate(boardWidth-1, boardHeight-1));
		initialDirections.add(Direction.UP);
		initialDirections.add(Direction.DOWN);
		initialDirections.add(Direction.RIGHT);
		initialDirections.add(Direction.LEFT);
		
		playerDirections = new ArrayList<Direction>(playerCount);
		ArrayList<Snake> playerSnakes = new ArrayList<Snake>(playerCount);
		for(int i = 0; i < playerCount; i++) {
			playerSnakes.add(new Snake(initialCorners.get(i), initialDirections.get(i), 3));
			playerDirections.add(initialDirections.get(i));
		}
		
		gameState = new GameState(playerSnakes);
		updateJSONState();
	}
	
	/**
	 * Updates the game board positions and other game state variables based on current player input and previous state.
	 * This corresponds to a game logic "tick".
	 */
	public synchronized void updateGameState() {		
		for(int i = 0; i < gameState.getPlayerSnakes().size(); i++) {
			gameState.getPlayerSnakes().get(i).move(playerDirections.get(i));
		}
		gameState.incrementTickCounter();
		//TODO: check collisions and kill snakes accordingly		
		updateJSONState();
		this.notifyAll();
	}
	
	/**
	 * Adds a new player to the game, sends the playerIdentity to the player
	 * @param socket the socket of the player
	 */
	public synchronized void addPlayer(Socket socket) {	
		for(PlayerIdentity identity : playerSockets.keySet()) {
			if(playerSockets.get(identity) == null) {
				playerSockets.put(identity, socket);
				String message = PacketHandler.createProtocolPacket(PacketType.PLAYERIDENTITY, gson.toJson(identity, PlayerIdentity.class));
				try {
					socket.getOutputStream().write(message.getBytes());
					socket.getOutputStream().flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				new Thread(new FromClientReceiver(identity, this, socket)).start();
				break;
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
		for(Socket s : playerSockets.values()) {
			try {
				s.getOutputStream().write(jsonState.getBytes());
				s.getOutputStream().flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	private synchronized void updateJSONState() {
		jsonState = gson.toJson(gameState, GameState.class);
	}
	
	/**
	 * Changes the direction of one of the players
	 * @param PlayerID the PlayerIdentity of the player to change direction for 
	 * @param direction the direction to change to
	 */
	public synchronized void changePlayerDirection(PlayerIdentity player, Direction direction) {
		if(player.ordinal() < playerDirections.size()) {
			playerDirections.set(player.ordinal(),direction);
		}
	}

}
