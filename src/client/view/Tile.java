package client.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Tile extends JLabel {
	private static int TILE_SIZE = 15;
	private String color;

	public Tile() {
		setOpaque(true);
		setPreferredSize(new Dimension(TILE_SIZE, TILE_SIZE));
		/* TODO: remove border */
		//setBorder(BorderFactory.createLineBorder(Color.white));
		color = "black";
		setColor(color);
	}

	public void setColor(String color) {
		String arg = color.toLowerCase();
		Color c = stringToColorConverter(arg);
		setBackground(c);
		this.color = arg;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	
	public String getColor() {
		return color;
	}
	
	private Color stringToColorConverter(String arg) {
		Color c;
		if (arg.equals("pink")) {
			c = Color.pink;
		} else if (arg.equals("green")) {
			c = Color.green;
		} else if (arg.equals("blue")) {
			c = Color.blue;
		} else if (arg.equals("orange")) {
			c = Color.orange;
		} else if (arg.equals("red")) {
			c = Color.red;
		} else {
			c = Color.black; /* Default color */
		}
		return c;
	}
}