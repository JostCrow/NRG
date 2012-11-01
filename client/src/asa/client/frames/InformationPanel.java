package asa.client.frames;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class InformationPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JLabel testLabel = new JLabel("InformationPanel");
	
	public InformationPanel(){
		this.setLayout(new BorderLayout());
		this.add(testLabel, BorderLayout.NORTH);
	}
	
}
