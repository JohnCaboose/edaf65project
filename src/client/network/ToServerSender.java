package client.network;

import client.model.DirectionMonitor;

public class ToServerSender implements Runnable {
	private final DirectionMonitor directionMonitor;
	
	public ToServerSender(DirectionMonitor directionMonitor){
		this.directionMonitor = directionMonitor;
	}
	
	@Override
	public void run() {
		do{
		directionMonitor.broadcastDirection();
		}while(directionMonitor.directionExists());
	}
}
