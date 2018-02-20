package client.model;

import common.model.Direction;

public class DirectionMonitor {
	private Direction directionToSend;
	private Direction previouslySentDirection;
	private boolean hasNewDirection;

	public DirectionMonitor() {
		directionToSend = null;
		previouslySentDirection = null;
		hasNewDirection = false;
	}

	public synchronized boolean directionExists() {
		return directionToSend != null;
	}

	public synchronized boolean hasNewDirection() {
		return hasNewDirection;
	}

	public synchronized void directionSent() {
		hasNewDirection = false;
	}

	/**
	 * Returns the next direction to send to the server.
	 * 
	 * @return the next direction to send to the server
	 */
	public synchronized Direction getDirection() {
		while (!directionExists()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		while (!hasNewDirection()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return directionToSend;
	}

	/**
	 * Submits a new direction to this monitor. The new direction will then be
	 * collected by another thread from this monitor, which will send it to the
	 * game server.
	 */
	public synchronized void submit(Direction newInput) {
		directionToSend = newInput;
		hasNewDirection = true;
		notifyAll();
	}

	/**
	 * Called by the sender thread to notify what direction was just recently
	 * sent to the game server. Used by the controller to determine if recent
	 * input is legal or not.
	 */
	public synchronized void setAsSent(Direction recentlySentDirection) {
		previouslySentDirection = recentlySentDirection;
		notifyAll();
	}

	/**
	 * Returns the direction that was just recently sent to the game server.
	 * This method will return <code>null</code> if no inputs have been sent to
	 * the server yet.
	 * 
	 * @return the previously sent direction
	 */
	public synchronized Direction getPreviouslySentDirection() {
		return previouslySentDirection;
	}
}
