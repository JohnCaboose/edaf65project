package client.model;

import common.model.GameState;
import common.model.PlayerIdentity;

public class ClientGameStateMonitor {

	private GameState state;
	private boolean hasNewGameState;
	private PlayerIdentity playerIdentity;
	
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
	//TODO: make client interpret game being over and then shutting everything down typ

	public synchronized void setPlayerIdentity(PlayerIdentity playerIdentityFromProtocolPacket) {
		this.playerIdentity = playerIdentityFromProtocolPacket;
	}
	
	public synchronized PlayerIdentity getPlayerIdentity(){
		return this.playerIdentity;
	}

}
