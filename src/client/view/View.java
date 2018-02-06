package client.view;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class View {
	private final static String WINDOW_TITLE = "MultiSnake";
	private MainFrame frame;

	public View() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frame = new MainFrame(WINDOW_TITLE);
				frame.pack();
				frame.setResizable(false);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}
}
