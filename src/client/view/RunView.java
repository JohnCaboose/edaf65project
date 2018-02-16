package client.view;

import client.model.MockSnakeThread;

public class RunView {
	/** This run-method is used as a debugging tool for the view. */
	public static void main(String[] args) {
		View v = new View(null, "blue");
		Thread t = new MockSnakeThread(v);
		t.start();
	}
}
