package client.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Tile extends JLabel {
	private static int TILE_SIZE = 30;

	public Tile() {
		setOpaque(true);
		setPreferredSize(new Dimension(TILE_SIZE, TILE_SIZE));
		/* TODO: remove border */
		setBorder(BorderFactory.createLineBorder(Color.white));
		setColor("black");
	}

	public void setColor(String color) {
		String arg = color.toLowerCase();
		Color c;
		if (arg.equals("red")) {
			c = Color.red;
		} else if (arg.equals("green")) {
			c = Color.green;
		} else if (arg.equals("blue")) {
			c = Color.blue;
		} else if (arg.equals("orange")) {
			c = Color.orange;
		} else {
			c = Color.black; /* Default color */
		}
		setBackground(c);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
}