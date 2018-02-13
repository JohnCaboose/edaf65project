package client.controller;

import common.model.Snake;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import common.model.Direction;
import client.view.View;

/* In this package we can have classes that deal with user input */
/* Interpreted (parsed) input should affect the state of the model */
public class Input implements KeyListener {
	private Snake modelSnake;
	private int key;
	private boolean W = false;
	private boolean A = true;
	private boolean S = false;
	private boolean D = false;
	private View observer;

	public Input(Snake model) {
		this.modelSnake = model;
	}

	public void keyPressed(KeyEvent ke) {
		key = ke.getKeyCode();
		// snakeToView.paintSnake();

		if (key == KeyEvent.VK_W && (!S)) {
			modelSnake.setDirection(Direction.UP);
			W = true;
			A = false;
			S = false;
			D = false;
		}

		if (key == KeyEvent.VK_A && (!D)) {
			modelSnake.setDirection(Direction.LEFT);
			W = false;
			A = true;
			S = false;
			D = false;
		}

		if (key == KeyEvent.VK_S && (!W)) {
			modelSnake.setDirection(Direction.DOWN);
			W = false;
			A = false;
			S = true;
			D = false;
		}

		if (key == KeyEvent.VK_D && (!A)) {
			modelSnake.setDirection(Direction.RIGHT);
			W = false;
			A = false;
			S = false;
			D = true;
		}
	}

	public void keyReleased(KeyEvent ke) {
		W = false;
		A = false;
		S = false;
		D = false;
	}

	public void keyTyped(KeyEvent ke) {

	}
}
