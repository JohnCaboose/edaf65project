package client.view;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	// TODO: better names
	private TopPanel top;
	private MiddlePanel middle;
	private BottomPanel bottom;

	public MainFrame(String title, String snakeColor, int rows, int cols) {
		super(title);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		add(top = new TopPanel(snakeColor));
		add(middle = new MiddlePanel(rows, cols));
		add(bottom = new BottomPanel());
		setFocusable(true);
	}

	/* Forwarding */
	public void displayDeadSnakeStatus() {
		top.displayDeadSnakeStatus();	
	}
	
	/* Forwarding */
	public void colorTileAt(int x, int y, String color) {
		middle.colorTileAt(x, y, color);
	}
	
	/* Forwarding */
	public void println(String content) {
		bottom.println(content);
	}

	/* Forwarding */
	public boolean isColoredAt(int x, int y) {
		return middle.isColoredAt(x, y);
	}
}
