package common.model;

import java.util.List;

public class GameState {
	//This class is the jsonable object that will represent the entire state of the game
	
	//tickCounter is -1 when game is not started yet
	//It counts from 0 to INT_MAX and then the game behavior is undefined :) 
	public int tickCounter = -1;
	public List<Snake> playerSnakes;
}
