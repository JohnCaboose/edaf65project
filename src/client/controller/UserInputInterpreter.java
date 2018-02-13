package client.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import client.model.DirectionMonitor;

public class UserInputInterpreter implements KeyListener {
	private DirectionMonitor monitor;
	private int previousInput;

	public UserInputInterpreter(DirectionMonitor monitor) {
		this.monitor = monitor;
		previousInput = -1;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_W:
			sendInput('W');
			break;
		case KeyEvent.VK_A:
			sendInput('A');
			break;
		case KeyEvent.VK_S:
			sendInput('S');
			break;
		case KeyEvent.VK_D:
			sendInput('D');
			break;
		}

	}

	private void sendInput(char newInput) {
		if ((newInput == 'W' && previousInput == 'S') || 
				(newInput == 'S' && previousInput == 'W') ||
				(newInput == 'A' && previousInput == 'D') ||
				(newInput == 'D' && previousInput == 'A')) {
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

}
