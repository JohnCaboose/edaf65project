package client.network;

import java.net.Socket;

import client.model.DirectionMonitor;

public class ToServerSender implements Runnable {
	private final DirectionMonitor directionMonitor;
	
	public ToServerSender(DirectionMonitor directionMonitor) {
		this.directionMonitor = directionMonitor;
	}
	
	@Override
	public void run() {
		while (directionMonitor.directionExists()) {
			while(!directionMonitor.newDirection()) {
				try {
					wait(); 
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			directionMonitor.broadcastDirection();
		}
	}
}
