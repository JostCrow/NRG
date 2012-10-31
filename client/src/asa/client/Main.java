package asa.client;

import asa.client.frames.FullScreenJFrame;
import gnu.io.CommPortIdentifier;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		System.out.println("ASA client application");
		
		List<CommPortIdentifier> availablePorts = SimpleRead.getComPorts();
		new SimpleRead(availablePorts.get(0));
		
		FullScreenJFrame frame = new FullScreenJFrame("PSA");
		frame.setVisible(true);
	}
}