package asa.client;

import asa.client.frames.FullScreenJFrame;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import java.awt.Color;
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