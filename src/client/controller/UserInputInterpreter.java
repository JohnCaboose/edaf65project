package client.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import client.model.DirectionMonitor;
import client.view.View;
import common.model.Direction;

public class UserInputInterpreter implements KeyListener {
	private DirectionMonitor monitor;
	private Direction previousLocalInput;

	public UserInputInterpreter(DirectionMonitor monitor) {
		this.monitor = monitor;
		previousLocalInput = Direction.NONE;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_W:
			sendInput(Direction.UP);
			break;
		case KeyEvent.VK_A:
			sendInput(Direction.LEFT);
			break;
		case KeyEvent.VK_S:
			sendInput(Direction.DOWN);
			break;
		case KeyEvent.VK_D:
			sendInput(Direction.RIGHT);
			break;
		}

	}

	private void sendInput(Direction newInput) {
		if (illegalInput(newInput)) {
			return;
		} else {
			if (monitor != null) {
				monitor.submit(newInput);
			}
			previousLocalInput = newInput;
		}
	}

	private boolean illegalInput(Direction newInput) {
		boolean localLegalCheck = legalCheckWith(newInput, previousLocalInput);
		Direction previousPublicInput = monitor.getPreviouslySentDirection();
		if (previousPublicInput == null) {
			return localLegalCheck;
		} else {
			boolean publicLegalCheck = legalCheckWith(newInput, previousPublicInput);
			return localLegalCheck && publicLegalCheck;
		}
	}

	/**
	 * Helper method. Checks if the two given directions are opposite of one
	 * another.
	 * 
	 * @param newInput
	 *            the input you want to validate
	 * @param previous
	 *            the input you want to compare with
	 * @return <code>true</code> if they are opposite of one another, otherwise
	 *         <code>false</code>
	 */
	private boolean legalCheckWith(Direction newInput, Direction previous) {
		boolean downThenUp = newInput == Direction.UP && previous == Direction.DOWN;
		boolean upThenDown = newInput == Direction.DOWN && previous == Direction.UP;
		boolean rightThenLeft = newInput == Direction.LEFT && previous == Direction.RIGHT;
		boolean leftThenRight = newInput == Direction.RIGHT && previous == Direction.LEFT;
		return downThenUp || upThenDown || rightThenLeft || leftThenRight;
	}

	/**
	 * Required to exist due to the KeyListener interface. Please ignore.
	 */
	public void keyReleased(KeyEvent e) {
		return;
	}

	/**
	 * Required to exist due to the KeyListener interface. Please ignore.
	 */
	public void keyTyped(KeyEvent e) {
		return;
	}
}
