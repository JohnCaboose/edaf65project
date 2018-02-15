package client.view;

import client.model.SnakeToViewThread;

public class RunView {
	/** This run-method is used as a debugging tool for the view. */
	public static void main(String[] args) {
		View v = new View(null);
		Thread t = new SnakeToViewThread(v);
		t.start();
	}
}
