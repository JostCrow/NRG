package asa.client.frames;

import com.google.gson.Gson;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import org.netbeans.lib.awtextra.AbsoluteConstraints;

public class FullScreenJFrame extends JFrame implements Observer{

	JPanel rootPanel;
	JButton closeButton;
	JProgressBar progres;
	JLabel direction;
	
	int topSpeed = 25;

	public FullScreenJFrame(String title) {
		super(title);

		rootPanel = new JPanel();
		progres = new JProgressBar();
		direction = new JLabel();
		closeButton = new JButton("Topspeed = " + topSpeed);
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				System.out.println("Close button Pressed");
				dispose();
				System.exit(0);
			}
		});
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        getContentPane().add(progres, new AbsoluteConstraints(0, 0, screenSize.width, 50));
		getContentPane().add(direction, new AbsoluteConstraints(0, 50, screenSize.width, 50));
		getContentPane().add(closeButton, new AbsoluteConstraints(0, 100, screenSize.width, 50));

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		
		setBounds(0, 0, screenSize.width, screenSize.height);
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
		progres.setMaximum(topSpeed);
		progres.setValue(speed);
		progres.repaint();
		if(topSpeed < speed){
			topSpeed = speed;
			closeButton.setText("Topspeed = " + topSpeed);
		}
	}
}