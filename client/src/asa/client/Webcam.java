///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package asa.client;
//
//import java.awt.Component;
//import java.awt.Frame;
//import java.awt.Label;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//import java.util.Vector;
//import javax.media.Buffer;
//import javax.media.CaptureDeviceInfo;
//import javax.media.CaptureDeviceManager;
//import javax.media.Manager;
//import javax.media.Player;
//import javax.media.control.FrameGrabbingControl;
//import javax.media.format.VideoFormat;
//
///**
// *
// * @author Corneel
// */
//public class Webcam {
//
//	public static void main(String[] args) throws Exception {
//		System.out.println("1");
//		Vector devices = CaptureDeviceManager.getDeviceList(new VideoFormat(null));
//		CaptureDeviceInfo captureDevice = (CaptureDeviceInfo) devices.get(0);
//		CaptureDeviceInfo deviceInfo = CaptureDeviceManager.getDevice(captureDevice.getName());
//		System.out.println("3");
//		final Player player = Manager.createRealizedPlayer(deviceInfo.getLocator());
//		System.out.println("4");
//		player.start();
//		System.out.println("5");
//		Component video = player.getVisualComponent();
//        //Thread.sleep(2500);
//        FrameGrabbingControl frameGrabber = (FrameGrabbingControl)player.getControl("javax.media.control.FrameGrabbingControl");
//        Buffer buf = frameGrabber.grabFrame();
//
//		Frame frm = new Frame("Java AWT Frame");
//		Component lbl = new Label("Hello");
//		frm.setSize(600, 480);
//		frm.setVisible(true);
//		frm.add(video);
//		//frm.add(lbl);
//
////        Image img = (new BufferToImage((VideoFormat)buf.getFormat()).createImage(buf));
////        BufferedImage buffImg = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
////        Graphics2D g = buffImg.createGraphics();
////        g.drawImage(img, null, null);
//
////        g.setColor(Color.RED);
////        g.setFont(new Font("Verdana", Font.BOLD, 16));
////        g.drawString((new Date()).toString(), 10, 25);
//// 
////        ImageIO.write(buffImg, "png", new File("c:\\webcam.png"));
//
//		frm.addWindowListener(new WindowAdapter() {
//			public void windowClosing(WindowEvent we) {
//				player.close();
//				player.deallocate();
//				System.exit(0);
//			}
//		});
//
//	}
//}
