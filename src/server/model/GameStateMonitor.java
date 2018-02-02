package server.model;

import java.util.ArrayList;
import java.util.List;

import common.model.Coordinate;
import common.model.Direction;
import common.model.PlayerIdentity;
import common.model.Snake;

/**
 * The servers game state, implemented as a monitor
 *
 */
public class GameStateMonitor {
	
	private List<Direction> playerDirections;
	private List<Snake> playerSnakes;
	private String jsonState;
	
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
		this.playerSnakes = new ArrayList<Snake>(playerCount);
		for(int i = 0; i < playerCount; i++) {
			//TODO: Initialize player initial snake positions (whole body!) and directions(point away from body!)
		}
	}
	
	/**
	 * Updates the game board positions and other game state variables based on current player input and previous state.
	 * This corresponds to a game logic "tick".
	 */
	public synchronized void updateGameState() {
		
		for(int i = 0; i < playerSnakes.size(); i++) {
			playerSnakes.get(i).move(playerDirections.get(i));
		}
		
		//TODO: check collisions and kill snakes accordingly		
		//TODO: Save state as json string
	}
	
	/**
	 * Serializes the player snakes (the game state) as a json string
	 * @return the json string for the snakes of the players
	 */
	public synchronized String getStateAsJson() {
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
