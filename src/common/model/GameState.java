package common.model;

import java.util.List;

public class GameState {
	//This class is the jsonable object that will represent the entire state of the game
	
	//tickCounter is -1 when game is not started yet
	//It counts from 0 to LONG_MAX and then the game behavior is undefined :) 
	private long tickCounter = -1;
	private final List<Snake> playerSnakes;
	
	public GameState(List<Snake> playerSnakes) {
		this.playerSnakes = playerSnakes;
	}
	
	/**
	 * The Snake objects for the game is returned as a list
	 * @return the list of snakes in the game
	 */
	public List<Snake> getPlayerSnakes() {
		return playerSnakes;
	}
	
	/**
	 * Returns the current value of the tick counter
	 * @return the current value of the tick counter
	 */
	public long getTickCounter() {
		return tickCounter;
	}
	
	/**
	 * Calculates whether the game is over or not by seeing how many snakes are alive
	 * @return true if the game is over
	 */
	public boolean isGameOver() {
		int snakesAlive = 0;
		for(Snake s : playerSnakes) {
			if(s.isAlive()) {
				snakesAlive++;
			}
		}
		int mimimumAmountOfSnakesThatMustBeAliveForGameToNotBeOver = playerSnakes.size() > 1 ? 2 : 1; 
		return snakesAlive < mimimumAmountOfSnakesThatMustBeAliveForGameToNotBeOver;
	}
	
	/**
	 * Increments the tick counter by one
	 */
	public void incrementTickCounter() {
		tickCounter++;
	}
	
	//TODO implement comparison functions for purpose of effectively syncing predictions with server (optional)
}
