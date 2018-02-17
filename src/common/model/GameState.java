package common.model;

import java.util.ArrayList;
import java.util.List;

public class GameState {
	//This class is the jsonable object that will represent the entire state of the game
	
	//tickCounter is -1 when game is not started yet
	//It counts from 0 to LONG_MAX and then the game behavior is undefined :) 
	private long tickCounter = -1;
	private final List<Snake> playerSnakes;
	private final int ORIGINAL_SNAKE_LENGTH = 5;
	
	public GameState(int playerCount, int boardWidth, int boardHeight) {
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
		
		playerSnakes = new ArrayList<Snake>(playerCount);
		for(int i = 0; i < playerCount; i++) {
			playerSnakes.add(new Snake(initialCorners.get(i), initialDirections.get(i), ORIGINAL_SNAKE_LENGTH));
		}
	}
	
	public void checkSnakeCollisions() {
		//check "head into body" collisions
		for(Snake s1 : playerSnakes) {
			for(Snake s2 : playerSnakes) {
				if(s1.isCrashingIntoBodyOf(s2)) {
					s1.kill();
					System.out.println("Killing snake because of collision.");
				}
			}
		}
		
		//check "head into head" collisions
		for(int i = 0; i < playerSnakes.size(); i++) {
			for(int k = 1; k < playerSnakes.size(); k++) {
				if( i != k ) {
					Snake s1 = playerSnakes.get(i);
					Snake s2 = playerSnakes.get(k);
					Coordinate s1Head = s1.getBody().getFirst();
					Coordinate s2Head = s2.getBody().getFirst();
					if(s1Head.equals(s2Head)) {
						s1.kill();
						s2.kill();
						System.out.println("Killing two snakes because of head on collision.");
					}
				}
			}
		}
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
	 * Performs a tick of the game logic
	 */
	public void performGameTick() {
		for(int i = 0; i < playerSnakes.size(); i++) {
			playerSnakes.get(i).moveForward();
		}
		//TODO: add spawning of fruit and that whole thing, if time allows and stuff 
		checkSnakeCollisions();
		tickCounter++;
	}
	
	//TODO implement comparison functions for purpose of effectively syncing predictions with server (optional)
}
