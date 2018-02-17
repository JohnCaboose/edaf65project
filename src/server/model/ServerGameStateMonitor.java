package server.model;



import com.google.gson.Gson;

import common.model.Direction;
import common.model.PlayerIdentity;
import common.model.Snake;
import common.model.GameState;


/**
 * The servers game state, implemented as a monitor
 */
public class ServerGameStateMonitor {
	private final GameState gameState;
	private String jsonState;
	private long lastStateSent;
	
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
	 * Kills the snake of a certain player
	 * @param playerIdentity the identity of the snake to kill
	 */
	public synchronized void killSnake(PlayerIdentity playerIdentity) {
		System.out.println("Force-killing snake: " + playerIdentity.name());
		gameState.getPlayerSnakes().get(playerIdentity.ordinal()).kill();
	}
	
	/**
	 * Returns the state of the game, but only if there is a new state since the last time this function was called. 
	 * If no new state exists, this function will block until one does.
	 * @return JSON string of the game state
	 */
	public synchronized String getNewJsonState() {
		while(gameState.getTickCounter() <= lastStateSent) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		lastStateSent = gameState.getTickCounter();
		return jsonState;	
	}
	
	/**
	 * Get the current state of the game as a JSON object string
	 * @return JSON string of the current game state
	 */
	public synchronized String getJsonState() {
		return jsonState;
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


}
