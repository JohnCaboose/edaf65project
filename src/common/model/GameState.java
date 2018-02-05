package common.model;

import java.util.List;

public class GameState {
	//This class is the jsonable object that will represent the entire state of the game
	
	//tickCounter is -1 when game is not started yet
	//It counts from 0 to INT_MAX and then the game behavior is undefined :) 
	private int tickCounter = -1;
	private final List<Snake> playerSnakes;
	
	public GameState(List<Snake> playerSnakes) {
		this.playerSnakes = playerSnakes;
	}
	
	public List<Snake> getPlayerSnakes() {
		return playerSnakes;
	}
	/**
	 * Returns the current value of the tick counter
	 * @return the current value of the tick counter
	 */
	public int getTickCounter() {
		return tickCounter;
	}
	
	/**
	 * Increments the tick counter by one
	 */
	public void incrementTickCounter() {
		tickCounter++;
	}
}
