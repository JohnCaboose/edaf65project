package common.model;

import java.util.ArrayList;
import java.util.List;

import common.constants.Constants;

/**
 * This class is the jsonable object that will represent the entire state of the game
 *
 */
public class GameState {
	/* TODO: implement that tickCounter starts at -5 and counts "down" to 0. 
	 * 0 would be the the first "real" tick and starting the actual game.
	 * This lets players get ready for the game to start. 
	 */
	
	// a negative tickCounter means the game has not started yet
	//It counts from 0 to LONG_MAX and then the game behavior is undefined :) 
	private long tickCounter = -1;
	private final List<Snake> playerSnakes;
	private final int ORIGINAL_SNAKE_LENGTH = 5;
	
	public GameState(int playerCount, int boardWidth, int boardHeight) {
		//Define the initial spawn corners
		Coordinate topLeft = new Coordinate(0, 0);
		Coordinate topRight = new Coordinate(boardWidth - 1, 0);
		Coordinate bottomLeft = new Coordinate(0,boardHeight - 1);
		Coordinate bottomRight = new Coordinate(boardWidth - 1, boardHeight - 1);
		
		ArrayList<Coordinate> initialCorners = new ArrayList<Coordinate>();
		ArrayList<Direction> initialDirections = new ArrayList<Direction>();
		initialCorners.add(bottomLeft);
		initialCorners.add(topRight);
		initialCorners.add(topLeft);
		initialCorners.add(bottomRight);
		initialDirections.add(Direction.UP);
		initialDirections.add(Direction.DOWN);
		initialDirections.add(Direction.RIGHT);
		initialDirections.add(Direction.LEFT);
		
		playerSnakes = new ArrayList<Snake>(playerCount);
		for(int i = 0; i < playerCount; i++) {
			playerSnakes.add(new Snake(initialCorners.get(i), initialDirections.get(i), ORIGINAL_SNAKE_LENGTH));
		}
	}
	
	public void performSnakeCollisionDetection() {
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
		//Temporary way of snakes to grow
		
		if((tickCounter*Constants.MILLIS_PER_STATE_FRAME) % Constants.MILLIS_BETWEEN_SNAKE_GROWTHS  == 0 && tickCounter > 1) {
			for(int i = 0; i < playerSnakes.size(); i++) {
				playerSnakes.get(i).grow();
			}
		}
		//move all snakes
		for(int i = 0; i < playerSnakes.size(); i++) {
			playerSnakes.get(i).moveForward();
		}
		//TODO: add spawning of fruit and that whole thing, if time allows and stuff 
		performSnakeCollisionDetection();
		tickCounter++;
	}
	
	//TODO implement comparison functions for purpose of effectively syncing predictions with server (optional)
}
