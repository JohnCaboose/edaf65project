package client.view;

import java.awt.GridLayout;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MiddlePanel extends JPanel {
	private Tile[][] playField;

	public MiddlePanel(int rows, int cols) {
		setLayout(new GridLayout(rows, cols));
		playField = new Tile[rows][cols];
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < cols; x++) {
				Tile t = new Tile();
				playField[x][y] = t;
				add(t);
			}
		}
	}

	public void colorTileAt(int x, int y, String color) {
		Tile t = playField[x][y];
		t.setColor(color);
	}
}
