package client.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import client.model.DirectionMonitor;

public class UserInputInterpreter implements KeyListener {
	private DirectionMonitor monitor;
	private Key previousInput;

	public UserInputInterpreter(DirectionMonitor monitor) {
		this.monitor = monitor;
		previousInput = Key.W; // TODO: better start value
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_W:
			sendInput(Key.W);
			break;
		case KeyEvent.VK_A:
			sendInput(Key.A);
			break;
		case KeyEvent.VK_S:
			sendInput(Key.S);
			break;
		case KeyEvent.VK_D:
			sendInput(Key.D);
			break;
		}

	}

	private void sendInput(Key newInput) {
		if ((newInput == Key.W && previousInput == Key.S) || (newInput == Key.S && previousInput == Key.W)
				|| (newInput == Key.A && previousInput == Key.D) || (newInput == Key.D && previousInput == Key.A)) {
			return; // movement does not make sense
		} else {
			monitor.send(newInput);
			previousInput = newInput;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		return;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		return;
	}

	public enum Key {
		W, A, S, D
	}

}
