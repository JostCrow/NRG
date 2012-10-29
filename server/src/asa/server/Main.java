package asa.server;

import asa.frames.FullScreenJFrame;

public class Main {

    public static void main(String[] args) {
	System.out.println("ASA server application");
	FullScreenJFrame frame = new FullScreenJFrame("PSA");
	frame.setVisible(true);
    }
}