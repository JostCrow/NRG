package asa.client.frames;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePanel extends JPanel{
	private static final long serialVersionUID = 1L;

	private JLabel testLabel = new JLabel("GamePanel");
	
	private int speed = 0;
	private int direction = 0;
	
	public GamePanel(){
		this.setLayout(new BorderLayout());
		this.add(testLabel, BorderLayout.NORTH);
	}
	
	public void setSpeed(int speed){
		this.speed = speed;
	}
	
	public void setDirection(int direction){
		this.direction = direction;
	}
}
