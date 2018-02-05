package server.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.gson.Gson;

import common.model.Coordinate;
import common.model.Direction;
import common.model.PlayerIdentity;
import common.model.Snake;
import common.model.GameState;

/**
 * The servers game state, implemented as a monitor
 */
public class GameStateMonitor {
	
	private final List<Direction> playerDirections;
	private final GameState gameState;
	private String jsonState;
	private final Map<PlayerIdentity,Integer> lastStateSent;
	private final Gson gson = new Gson();
	
	/**
	 * Creates a new monitor that handles the game state-machine
	 * @param playerCount the amount of players for the game
	 * @param boardWidth width of the game board
	 * @param boardHeight height of the game board
	 */
	public GameStateMonitor(int playerCount, int boardWidth, int boardHeight) {
		playerDirections = new ArrayList<Direction>(playerCount);
		Coordinate.width = boardWidth;
		Coordinate.height = boardHeight;
		
		lastStateSent = new TreeMap<PlayerIdentity,Integer>();
		switch(playerCount) {
			case 4:
				lastStateSent.put(PlayerIdentity.FOUR, -2);
			case 3:
				lastStateSent.put(PlayerIdentity.THREE, -2);
			case 2:
				lastStateSent.put(PlayerIdentity.TWO, -2);
			case 1:
				lastStateSent.put(PlayerIdentity.ONE, -2);
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
		
		ArrayList<Snake> playerSnakes = new ArrayList<Snake>(playerCount);
		for(int i = 0; i < playerCount; i++) {
			playerSnakes.add(new Snake(initialCorners.get(i), initialDirections.get(i), 3));
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
		
		//TODO: check collisions and kill snakes accordingly		
		updateJSONState();
		this.notifyAll();
	}
	
	/**
	 * Serializes the player snakes (the game state) as a JSON string
	 * @return the JSON string for the snakes of the players
	 */
	public synchronized String getStateAsJson(PlayerIdentity playerIdentity) {
		while(gameState.getTickCounter() <= lastStateSent.get(playerIdentity)) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		lastStateSent.put(playerIdentity, gameState.getTickCounter());
		return jsonState;
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
