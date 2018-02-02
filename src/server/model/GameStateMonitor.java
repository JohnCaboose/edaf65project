package server.model;

import java.util.ArrayList;
import java.util.List;

import common.model.Direction;
import common.model.PlayerIdentity;

/**
 * The servers game state, implemented as a monitor
 *
 */
public class GameStateMonitor {
	
	private List<Direction> playerDirections;
	
	/**
	 * Creates a new monitor that handles the game state-machine
	 * @param playerCount the amount of players for the game
	 * @param boardWidth width of the game board
	 * @param boardHeight height of the game board
	 */
	public GameStateMonitor(int playerCount, int boardWidth, int boardHeight) {
		playerDirections = new ArrayList<Direction>(playerCount);
	}
	
	/**
	 * Updates the game board positions and other game state variables based on current player input and previous state.
	 * This corresponds to a game logic "tick".
	 */
	public synchronized void updateGameState() {
		
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
