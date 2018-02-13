package client.view;

import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class BottomPanel extends JPanel {
	private JTextArea textArea;
	private boolean autoscroll;
	private boolean firstLine;

	public BottomPanel() {
		setLayout(new GridLayout(1, 1));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		textArea = new JTextArea(10, 1);
		textArea.setEditable(false);
		add(new JScrollPane(textArea));
		autoscroll = true;
		firstLine = true;
	}
	
	public void println(String content) {
		String systemTime = getTime();
		String line;
		if (firstLine) {
			line = String.format("[%s]: %s", systemTime, content);
			firstLine = false;
		} else {
			line = String.format("\n[%s]: %s", systemTime, content);
		}
		textArea.append(line);
		if (autoscroll) {
			textArea.setCaretPosition(textArea.getDocument().getLength());
		}
	}
	
	private String getTime() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat hhmmss = new SimpleDateFormat("hh:mm:ss");
		return hhmmss.format(cal.getTime());
	}
}
