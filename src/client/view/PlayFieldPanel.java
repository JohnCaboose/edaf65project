package client.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PlayFieldPanel extends JPanel {
	private Tile[][] playField;

	public PlayFieldPanel(int rows, int cols) {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		playField = new Tile[rows][cols];
		for (int y = 0; y < cols; y++) {
			for (int x = 0; x < rows; x++) {
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
