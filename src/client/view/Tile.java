package client.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.lang.reflect.Field;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Tile extends JLabel {
	private static int TILE_SIZE = 15;
	private String color;

	public Tile() {
		setOpaque(true);
		setPreferredSize(new Dimension(TILE_SIZE, TILE_SIZE));
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
		/*
		 * Inspired by
		 * https://stackoverflow.com/questions/2854043/converting-a-string-to-
		 * color-in-java
		 */
		Color c;
		try {
			Field field = Class.forName("java.awt.Color").getField(arg);
			c = (Color) field.get(null);
		} catch (Exception e) {
			System.err.println("Tile.stringToColorConverter error. Using default color.");
			c = Color.black; /* Default color */
		}
		return c;
	}
}