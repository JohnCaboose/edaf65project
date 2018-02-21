package client.model;

import common.model.Direction;

public class DirectionMonitor {
	private Direction directionToSend;
	private boolean hasNewDirection;

	public DirectionMonitor() {
		directionToSend = null;
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
}
