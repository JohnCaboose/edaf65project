package client.view;

import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class TopPanel extends JPanel {
	private JTextArea colorTitleText;
	private JTextArea aliveTitleText;

	public TopPanel() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(colorTitleText = new JTextArea("Your Color: "));
		add(aliveTitleText = new JTextArea("Alive: "));
		colorTitleText.setFont(new Font("arial", Font.BOLD, 24));
		colorTitleText.setEditable(false);
		aliveTitleText.setFont(new Font("arial", Font.BOLD, 24));
		aliveTitleText.setEditable(false);
	}
}
