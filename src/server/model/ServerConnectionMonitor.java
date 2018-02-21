package server.model;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;

import common.model.PlayerIdentity;
import server.exceptions.GameIsFullException;

public class ServerConnectionMonitor {
	private final Map<PlayerIdentity,Socket> playerSockets;
	private final ServerGameStateMonitor gameStateMonitor;
	private boolean allPlayersConnected = false;
	
	public ServerConnectionMonitor (int playerCount, ServerGameStateMonitor gameStateMonitor) {
		playerSockets = new TreeMap<PlayerIdentity, Socket>();
		this.gameStateMonitor = gameStateMonitor;
		switch(playerCount) {
			case 4:
				playerSockets.put(PlayerIdentity.FOUR, null);
			case 3:
				playerSockets.put(PlayerIdentity.THREE, null);
			case 2:
				playerSockets.put(PlayerIdentity.TWO, null);
			case 1:
				playerSockets.put(PlayerIdentity.ONE, null);
			default:
				break;
		}
	}
	
	
	/**
	 * Adds a new player to the game, sends the playerIdentity to the player
	 * @param socket the socket of the player
	 * @return the identity of the player added
	 */
	public synchronized PlayerIdentity addPlayer(Socket socket) throws GameIsFullException {	
		for(PlayerIdentity playerIdentity : playerSockets.keySet()) {
			if(playerSockets.get(playerIdentity) == null) {
				System.out.println("Adding new player " + playerIdentity);
				playerSockets.put(playerIdentity, socket);
				return playerIdentity;
			}
		}
		throw new GameIsFullException();
	}
	/**
	 * Sends the packet in the string to all connected players
	 * @param packet the packet to send
	 */
	public synchronized void sendPacketToAllPlayers(String packet) {
		for(Socket socket : playerSockets.values()) {
			if(socket != null) {
				try {
					socket.getOutputStream().write(packet.getBytes());
					socket.getOutputStream().flush();
				} catch (IOException e) {
					gameStateMonitor.killSnake(getIdentityOfSocket(socket));
					this.removePlayer(socket);
				}
			}
		}
	}
	
	/**
	 * Removes player from game, killing their snake if the game is running or otherwise waiting for more players
	 * @param playerIdentity the identity of the player
	 */
	public synchronized void removePlayer(PlayerIdentity playerIdentity) {		
		Socket socket = playerSockets.get(playerIdentity);
		if(socket != null) {
			if(this.allPlayersConnected()) {
				gameStateMonitor.killSnake(playerIdentity);
			}
			System.out.println("Removing player " + playerIdentity.name());
			try {
				socket.shutdownInput();
				socket.close();
			} catch (IOException e) {
				//Doesn't matter
			}
			playerSockets.put(playerIdentity, null);
			
				
		}
	}
	
	/**
	 * Removes player from game, killing their snake if the game is running or otherwise waiting for more players
	 * @param socket the socket of the player
	 */
	public synchronized void removePlayer(Socket socket) {
		removePlayer(getIdentityOfSocket(socket));
	}
	
	private synchronized PlayerIdentity getIdentityOfSocket(Socket socket) {
		for(PlayerIdentity pi : playerSockets.keySet()) {
			if(socket.equals(playerSockets.get(pi))) {
				return pi;
			}
		}
		return null;
	}
	
	
	/**
	 * Check if all players have connected to the session
	 * @return true if all players are connected, false otherwise
	 */
	public synchronized boolean allPlayersConnected() {
		if(!allPlayersConnected) {
			allPlayersConnected = !playerSockets.containsValue(null);
		}
		return allPlayersConnected;
	}

}
