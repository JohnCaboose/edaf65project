package client.controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import client.model.Snake;
import client.model.SnakeToViewThread;
import client.view.View;
import common.model.Direction;

/* In this package we can have classes that deal with user input */
/* Interpreted (parsed) input should affect the state of the model */
public class Input implements java.awt.event.ActionListener {
	private Snake modelSnake;
	private SnakeToViewThread snakeToView;
	private View view; //needed?
	private int key;
	private boolean W = false;
    private boolean A = true;
    private boolean S = false;
    private boolean D = false;
    
	public Input(Snake model, View view) {
		this.modelSnake = model;
		this.view = view;
		snakeToView = new SnakeToViewThread(view);
	}
	
	public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			//call model's method/s
	}
    
    public void keyPressed(KeyEvent ke) {
    	key = ke.getKeyCode();
    	//snakeToView.paintSnake();
    	
        if(key == KeyEvent.VK_W && (!S)) {
        	modelSnake.setDirection(Direction.UP);
        	W = true;
            D = false;
            A = false;
        }

        if(key == KeyEvent.VK_A && (!D)) {
        	modelSnake.setDirection(Direction.LEFT);
        	A = true;
            S = false;
            W = false;
        }

        if(key == KeyEvent.VK_S && (!W)) {
        	modelSnake.setDirection(Direction.DOWN);
        	S = true;
            A = false;
            D = false;
        }

        if(key == KeyEvent.VK_D && (!A)) {
        	modelSnake.setDirection(Direction.RIGHT);
        	D = true;
            W = false;
            S = false;
        }
    }
    
    public void keyReleased(KeyEvent ke) {
    	W = false;
    	A = false;
        S = false;
        D = false;
    }
}
