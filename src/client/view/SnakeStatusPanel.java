package client.view;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class SnakeStatusPanel extends JPanel {
	private final Font font = new Font("arial", Font.BOLD, 24);
	private String aliveColor;
	private Tile aliveTile;
	public SnakeStatusPanel(String snakeColor) {
		aliveColor = "green";
		setLayout(new GridLayout(1, 2));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		add(new ColorInfoPanel(snakeColor));
		add(new AliveInfoPanel());
	}
	
	public void displayDeadSnakeStatus() {
		aliveTile.setColor("red");
		aliveTile.setText("DEAD");
	}
	
	private class ColorInfoPanel extends JPanel {
		public ColorInfoPanel(String snakeColor) {
			setLayout(new GridLayout(1, 2));
			setBorder(new EmptyBorder(0, 0, 0, 5));
			JLabel colorTitleText;
			add(colorTitleText = new JLabel("Your Color: "));
			colorTitleText.setFont(font);
			colorTitleText.setHorizontalAlignment(SwingConstants.RIGHT);
			
			Tile t = new Tile();
			t.setColor(snakeColor);
			add(t);
		}
	}
	
	private class AliveInfoPanel extends JPanel {
		public AliveInfoPanel() {
			setLayout(new GridLayout(1, 2));
			setBorder(new EmptyBorder(0, 5, 0, 0));
			JLabel aliveTitleText;
			add(aliveTitleText = new JLabel("Your Snake: "));
			aliveTitleText.setFont(font);
			aliveTitleText.setHorizontalAlignment(SwingConstants.RIGHT);
			
			aliveTile = new Tile();
			aliveTile.setFont(font);
			aliveTile.setColor(aliveColor);
			aliveTile.setText("ALIVE");
			aliveTile.setHorizontalAlignment(SwingConstants.CENTER);
			add(aliveTile);
		}
	}
}
