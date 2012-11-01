package asa.client.frames;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import asa.client.resources.Resource;

public class GamePanel extends JPanel{
	private static final long serialVersionUID = 1L;

	private JLabel testLabel = new JLabel("GamePanel");
	
	private int speed = 0;
	private int direction = 0;
	private int wheelposition = 0;
	List<Image> images = getImages();
	
	public GamePanel(){
		this.setLayout(new BorderLayout());
		this.add(testLabel, BorderLayout.NORTH);
		JLabel imageLabel = new JLabel(new ImageIcon(images.get(0)));
		this.add(imageLabel, BorderLayout.CENTER);
		
	}
	
	public void updateImage(int speed, int direction){
		calculateWheelPosition(speed, direction);
		Image image = images.get(Math.abs(this.wheelposition % images.size()));
		JLabel imageLabel = new JLabel(new ImageIcon(image));
		this.removeAll();
		this.add(imageLabel);
		this.revalidate();
		this.repaint();
	}
	
	private void calculateWheelPosition(int speed, int direction){
		System.out.println(speed + " => " + (speed / 2));
		this.speed = speed / 2;
		this.direction = direction;
		if(this.direction == 0) {
			this.wheelposition += this.speed;
		} else {
			this.wheelposition -= this.speed;
		}
		System.out.println(this.wheelposition);
	}
	
	private List<Image> getImages(){
		List<Image> wheelImages = new ArrayList<Image>();
		for(String url : Resource.GEARS_SEQUENCE){
			wheelImages.add(Toolkit.getDefaultToolkit().getImage(Resource.get(url)));
		}
		return wheelImages;
	}
	
}
