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

	public BottomPanel() {
		setLayout(new GridLayout(1, 1));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		textArea = new JTextArea(10, 1);
		textArea.setEditable(false);
		add(new JScrollPane(textArea));
	}
	
	public void println(String content) {
		String systemTime = getTime();
		String line = String.format("[%s]: %s\n", systemTime, content);
		textArea.append(line);
	}
	
	private String getTime() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat hhmmss = new SimpleDateFormat("hh:mm:ss");
		return hhmmss.format(cal.getTime());
	}
}
