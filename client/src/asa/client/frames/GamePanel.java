package asa.client.frames;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePanel extends JPanel{
	private static final long serialVersionUID = 1L;

	private JLabel testLabel = new JLabel("GamePanel");
	
	public GamePanel(){
		this.setLayout(new BorderLayout());
		this.add(testLabel, BorderLayout.NORTH);
		this.setPreferredSize(new Dimension(0, 0));
	}
}