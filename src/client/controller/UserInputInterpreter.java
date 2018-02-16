package client.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import client.model.DirectionMonitor;
import common.model.Direction;

public class UserInputInterpreter implements KeyListener {
	private DirectionMonitor monitor;
	private Direction previousInput;

	public UserInputInterpreter(DirectionMonitor monitor) {
		this.monitor = monitor;
		previousInput = Direction.NONE;
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
		if ((newInput == Direction.UP && previousInput == Direction.DOWN)
				|| (newInput == Direction.DOWN && previousInput == Direction.UP)
				|| (newInput == Direction.LEFT && previousInput == Direction.RIGHT)
				|| (newInput == Direction.RIGHT && previousInput == Direction.LEFT)) {
			return; // movement does not make sense
		} else {
			if (monitor != null)
			monitor.submit(newInput);
			previousInput = newInput;
		}
		//TODO: make it so that it actually cant go into itself by fixing this to check for the last actually used input instead or both?
	}

	@Override
	public void keyReleased(KeyEvent e) {
		return;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		return;
	}
}
