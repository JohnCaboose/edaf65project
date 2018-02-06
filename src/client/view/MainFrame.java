package client.view;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	// TODO: better names
	private TopPanel top;
	private MiddlePanel middle;
	private BottomPanel bottom;

	public MainFrame(String title, int rows, int cols) {
		super(title);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		add(top = new TopPanel());
		add(middle = new MiddlePanel(rows, cols));
		add(bottom = new BottomPanel());
	}

	/* Forwarding */
	public void colorTileAt(int x, int y, String color) {
		middle.colorTileAt(x, y, color);
	}

}
