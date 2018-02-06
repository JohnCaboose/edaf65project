package client.view;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = -7805775198833044790L;
	private static final int WIDTH = 500;
	private static final int HEIGHT = 500;

	public MainFrame(String title) {
		super(title);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		add(new TopPanel());
		add(new MiddlePanel());
		add(new BottomPanel());
	}

}
