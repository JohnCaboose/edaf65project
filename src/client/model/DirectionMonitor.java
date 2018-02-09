package client.model;

import client.controller.Input;
import common.model.Direction;
import common.model.Snake;

public class DirectionMonitor {
		private Direction direction;
		private Input input;
	
	public DirectionMonitor(Snake snake){
		input = new Input(snake);
	}
	
	public synchronized Direction getDirection() {
		try {
			if(direction == null) {
				wait();
			}
			//direction = input.keyPressed();
		}catch(Exception e) {
			System.out.println("Error in DirectionMonitor");
		}
		notifyAll();
		return direction;
	}
	
	public synchronized void setDirection() {
		
	}
}
