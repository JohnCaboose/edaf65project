package client.view;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import client.controller.UserInputInterpreter;
import client.model.DirectionMonitor;
import common.constants.Constants;

/* Monitor for the GUI */
public class View {
	private final static String WINDOW_TITLE = "MultiSnake";
	public static final int FIELD_SIZE_X = Constants.BOARD_WIDTH;
	public static final int FIELD_SIZE_Y = Constants.BOARD_HEIGHT;
	private MainFrame frame;
	private boolean visible;

	/**
	 * Creates a new view and displays it on the screen.
	 * 
	 * @param monitor
	 *            the <code>DirectionMonitor</code> to send user input to
	 * @param snakeColor
	 *            the color to display in the top panel for the user, that
	 *            indicates what snake belongs to the player.
	 */
	public View(DirectionMonitor monitor, String snakeColor) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frame = new MainFrame(WINDOW_TITLE, snakeColor, FIELD_SIZE_X, FIELD_SIZE_Y);
				frame.pack();
				frame.setResizable(false);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				frame.setFocusable(true);
				frame.addKeyListener(new UserInputInterpreter(monitor));
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
		blockWhileNotVisible();
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
		blockWhileNotVisible();
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
		blockWhileNotVisible();
		frame.println(content);
	}

	/**
	 * Returns true if the given tile coordinate is not black, i.e. the tile is
	 * occupied by another snake.
	 * 
	 * @param x
	 *            the x coordinate of the tile
	 * @param y
	 *            the y coordinate of the tile
	 * @return <code>true</code> if tile is colored, otherwise
	 *         <code>false</code>
	 */
	public synchronized boolean isColoredAt(int x, int y) {
		blockWhileNotVisible();
		return frame.isColoredAt(x, y);
	}

	/** Paints all tiles on the play field black. */
	public synchronized void clearPlayField() {
		for (int x = 0; x < FIELD_SIZE_X; x++) {
			for (int y = 0; y < FIELD_SIZE_Y; y++) {
				colorTileAt(x, y, "black");
			}
		}
	}

	private void blockWhileNotVisible() {
		while (!visible) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
