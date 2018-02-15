package client.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MiddlePanel extends JPanel {
	private Tile[][] playField;

	public MiddlePanel(int rows, int cols) {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		playField = new Tile[rows][cols];
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < cols; x++) {
				c.gridx = x;
				c.gridy = y;
				Tile t = new Tile();
				playField[x][y] = t;
				add(t, c);
			}
		}
	}

	public void colorTileAt(int x, int y, String color) {
		Tile t = playField[x][y];
		t.setColor(color);
	}

	public boolean isColoredAt(int x, int y) {
		Tile t = playField[x][y];
		String color = t.getColor();
		return !color.equals("black");
	}
}
