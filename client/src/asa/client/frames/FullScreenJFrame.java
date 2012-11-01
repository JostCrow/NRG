package asa.client.frames;

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

	private static int GAMEMODE = 0;
	private static int INFORMATIONMODE = 1;
	private static int SPEEDTRIGGER = 1;
	
	JPanel rootPanel;
	JButton closeButton;
	JProgressBar progres;
	JLabel direction;
	
	int topSpeed = 25;
	int MODE =  INFORMATIONMODE;
	
	InformationPanel informationPanel = new InformationPanel();
	GamePanel gamePanel = new GamePanel();
	
	public FullScreenJFrame(String title) {
		super(title);
		this.getContentPane().add(informationPanel);
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
		int speed = Integer.valueOf(test.get("speed"));
		int direction = Integer.valueOf(test.get("direction"));
		gamePanel.updateImage(speed, direction);
		if(speed > SPEEDTRIGGER && MODE == INFORMATIONMODE) {
			this.getContentPane().remove(informationPanel);
			this.getContentPane().add(gamePanel);
			this.revalidate();
			this.repaint();
			MODE = GAMEMODE;
		} else if(speed <= SPEEDTRIGGER && MODE == GAMEMODE){
			this.getContentPane().remove(gamePanel);
			this.getContentPane().add(informationPanel);
			this.revalidate();
			this.repaint();
			MODE = INFORMATIONMODE;
		}
	}
}