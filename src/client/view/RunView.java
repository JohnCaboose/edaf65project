package client.view;

import client.model.SnakeToViewThread;

public class RunView {
	public static void main(String[] args) {
		View v = new View(null);
		
		// TODO: remove debugging
		Thread t = new SnakeToViewThread(v);
		t.start();
	}
}
