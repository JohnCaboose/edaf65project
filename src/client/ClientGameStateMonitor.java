package client;

import common.model.GameState;

public class ClientGameStateMonitor {

	private GameState state;
	private boolean hasNewGameState;
	
	public ClientGameStateMonitor() {
		state = null;
	}
	
	public synchronized void setGameState(GameState state) {
		this.state = state;
		hasNewGameState = true;
		notifyAll();
	}
	
	
	public synchronized GameState getGameState() {
		while (!hasNewGameState)
			try {
				wait();
			} catch (InterruptedException e) {
			}
		hasNewGameState = false;
		return state;
	}

}
