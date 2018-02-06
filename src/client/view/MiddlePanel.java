package client.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MiddlePanel extends JPanel {
	private Tile[][] playField;
	private static int TILE_SIZE = 30;

	public MiddlePanel(int rows, int cols) {
		setLayout(new GridLayout(rows, cols));
		playField = new Tile[rows][cols];
		initAndAddPlayField();
	}

	public void colorTileAt(int x, int y, String color) {
		Tile t = playField[x][y];
		t.setColor(color);
	}

	private void initAndAddPlayField() {
		for (int y = 0; y < playField.length; y++) {
			for (int x = 0; x < playField.length; x++) {
				Tile t = new Tile();
				playField[x][y] = t;
				add(t);
			}
		}
	}

	private class Tile extends JLabel {

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
}
