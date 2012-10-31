package asa.client;

import asa.client.frames.FullScreenJFrame;
import asa.client.frames.NewJFrame;
import gnu.io.CommPortIdentifier;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("GTK+".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		System.out.println("ASA client application");
		
		List<CommPortIdentifier> availablePorts = ComPortReader.getComPorts();
		ComPortReader simpleRead = new ComPortReader(availablePorts.get(0));
		
		FullScreenJFrame frame = new FullScreenJFrame("PSA");
		frame.setVisible(true);
		simpleRead.addObserver(frame);
		
	}
}