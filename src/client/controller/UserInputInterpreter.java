package client.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import client.model.DirectionMonitor;
import common.model.Direction;

public class UserInputInterpreter implements KeyListener {
	private DirectionMonitor monitor;

	public UserInputInterpreter(DirectionMonitor monitor) {
		this.monitor = monitor;
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
		if (monitor != null) {
			monitor.submit(newInput);
		}
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
