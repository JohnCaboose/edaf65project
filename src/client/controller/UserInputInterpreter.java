package client.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import client.model.DirectionMonitor;
import client.view.View;
import common.model.Direction;

public class UserInputInterpreter implements KeyListener {
	private DirectionMonitor monitor;
	private Direction previousLocalInput;

	public UserInputInterpreter(View view, DirectionMonitor monitor) {
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
			if (monitor != null)
			monitor.submit(newInput);
			previousLocalInput = newInput;
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
	
	private boolean illegalInput(Direction newInput) {
		boolean b1 = newInput == Direction.UP && previousLocalInput == Direction.DOWN;
		boolean b2 = newInput == Direction.DOWN && previousLocalInput == Direction.UP;
		boolean b3 = newInput == Direction.LEFT && previousLocalInput == Direction.RIGHT;
		boolean b4 = newInput == Direction.RIGHT && previousLocalInput == Direction.LEFT;
		return b1 || b2 || b3 || b4;
	}
}
