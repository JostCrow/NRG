package asa.client.frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import com.google.gson.Gson;

public class FullScreenJFrame extends JFrame implements Observer{

	JPanel rootPanel;
	JButton closeButton;
	JProgressBar progres;
	JLabel direction;
	
	int topSpeed = 25;
	InformationPanel informationPanel;
	
	public FullScreenJFrame(String title) {
		super(title);
		
		GamePanel gamePanel = new GamePanel();
		informationPanel = new InformationPanel();
		
		this.getRootPane().add(gamePanel);
		this.getContentPane().add(informationPanel);

		this.getRootPane().setVisible(true);
		this.getContentPane().setVisible(true);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setFullscreen();
	}
	
	private void setFullscreen(){
	      setUndecorated(true);
	      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	      setBounds(0,0,screenSize.width, screenSize.height);
	}
	
	@Override
	public void update(Observable observable, Object object) {
		String jsonString = (String) object;
		Gson gson = new Gson();
		HashMap<String, String> test = gson.fromJson(jsonString, HashMap.class);
		if(Integer.valueOf(test.get("direction")) == 1){
			direction.setText("Clockwise");
		} else {
			direction.setText("Counter Clockwise");
		}
		int speed = Integer.valueOf(test.get("speed"));
		if(speed == 0 && informationPanel.isVisible()) {
			informationPanel.setVisible(false);
			informationPanel.repaint();
		}
		progres.setMaximum(topSpeed);
		progres.setValue(speed);
		progres.repaint();
		if(topSpeed < speed){
			topSpeed = speed;
			closeButton.setText("Topspeed = " + topSpeed);
		}
	}
}