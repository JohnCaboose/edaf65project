package client.view;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/* Monitor for the GUI */
public class View {
	private final static String WINDOW_TITLE = "MultiSnake";
	public static final int FIELD_SIZE_X = 24;
	public static final int FIELD_SIZE_Y = 24;
	private MainFrame frame;
	private boolean visible;

	public View() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frame = new MainFrame(WINDOW_TITLE, FIELD_SIZE_X, FIELD_SIZE_Y);
				frame.pack();
				frame.setResizable(false);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				setVisible(true);
			}
		});
	}

	/* Called by the AWT event dispatching thread. */
	private synchronized void setVisible(boolean on) {
		visible = on;
		notifyAll();
	}

	/**
	 * Signals to the user that their snake is dead by updating the appropriate
	 * field in the top panel.
	 */
	public synchronized void displayDeadSnakeStatus() {
		frame.displayDeadSnakeStatus();
	}

	/**
	 * Colors the specified tile with the specified color in the play field.
	 * 
	 * @param x
	 *            the x coordinate of the specified tile
	 * @param y
	 *            the y coordinate of the specified tile
	 * @param color
	 *            what color to paint on the specified tile
	 */
	public synchronized void colorTileAt(int x, int y, String color) {
		try {
			while (!visible)
				wait();
		} catch (Exception e) {
		}
		frame.colorTileAt(x, y, color);
		notifyAll();
	}

	/**
	 * Prints the provided <code>String</code> to the console window in the
	 * bottom panel. A timestamp and a new line is prepended to the
	 * <code>String</code>.
	 * 
	 * @param content
	 *            the text to display in the console window
	 */
	public synchronized void println(String content) {
		frame.println(content);
	}
}
