package client.model;
import common.model.Direction;

public class DirectionMonitor {
	private Direction direction = null;
	private boolean hasNewDirection = false;

	public synchronized boolean directionExists() {
		return direction != null;
	}

	public synchronized boolean hasNewDirection() {
		return hasNewDirection;
	}
	
	public synchronized void directionSent() {
		hasNewDirection = false;
	}
	
	public synchronized Direction getDirection() {
		while(!directionExists()){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		while(!hasNewDirection()){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return direction;
	}

	/**
	 * Submits a new direction to this monitor. The new direction will then be
	 * collected by another thread from this monitor.
	 */
	public synchronized void submit(Direction newInput) {
		direction = newInput;
		hasNewDirection = true;
		notifyAll();
	}
}
