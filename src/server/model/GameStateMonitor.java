package server.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
		
		gameState = new GameState();
		gameState.playerSnakes = new ArrayList<Snake>(playerCount);
		for(int i = 0; i < playerCount; i++) {
			//TODO: Initialize player initial snake positions (whole body!) and directions(point away from body!)
		}
		
		//TODO: set initial JSON state string
		
	}
	
	/**
	 * Updates the game board positions and other game state variables based on current player input and previous state.
	 * This corresponds to a game logic "tick".
	 */
	public synchronized void updateGameState() {		
		for(int i = 0; i < gameState.playerSnakes.size(); i++) {
			gameState.playerSnakes.get(i).move(playerDirections.get(i));
		}
		
		//TODO: check collisions and kill snakes accordingly		
		//TODO: Save state as json string
		this.notifyAll();
	}
	
	/**
	 * Serializes the player snakes (the game state) as a JSON string
	 * @return the JSON string for the snakes of the players
	 */
	public synchronized String getStateAsJson(PlayerIdentity playerIdentity) {
		while(gameState.tickCounter <= lastStateSent.get(playerIdentity)) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return jsonState;
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
