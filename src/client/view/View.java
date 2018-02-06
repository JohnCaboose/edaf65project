package client.view;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/* Monitor for the GUI */
public class View {
	private final static String WINDOW_TITLE = "MultiSnake";
	private static final int FIELD_SIZE_X = 16;
	private static final int FIELD_SIZE_Y = 16;
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

	/* Called by AWT event dispatching thread. */
	private synchronized void setVisible(boolean on) {
		visible = on;
		notifyAll();
	}

	public synchronized void colorTileAt(int x, int y, String color) {
		try {
			while (!visible)
				wait();
		} catch (Exception e) {
		}
		frame.colorTileAt(x, y, color);
		notifyAll();
	}
}
