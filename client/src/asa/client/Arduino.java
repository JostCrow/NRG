package asa.client;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.TooManyListenersException;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

public class Arduino extends KeyAdapter implements Runnable, SerialPortEventListener, KeyListener{

	private Logger logger = Logger.getLogger(Arduino.class);
	
	private static Arduino _instance = null;
	private List<ArduinoListener> listeners = new ArrayList<ArduinoListener>();
	private List<CommPortIdentifier> availablePorts = this.getComPorts();
	
	private boolean simulation = false;
	private InputStream inputStream;
	private SerialPort serialPort;
	
	private Thread thread = new Thread(this);
	
	private byte buffer[] = new byte[32768];
	private int bufferLast;
	private String jsonString;
	
	private Gson gson = new Gson();
	
	private static String KEY_SPEED = "speed";
	private static String KEY_DIRECTION = "direction";
	
	public static Arduino getInstance(){
		if(Arduino._instance == null){
			Arduino._instance = new Arduino();
		}
		return Arduino._instance;
	}
	
	private Arduino(){
		if(availablePorts.size() > 0){
			CommPortIdentifier portId = availablePorts.get(0);
			try {
				serialPort = (SerialPort) portId.open("SimpleReadApp", 2000);
				inputStream = serialPort.getInputStream();
				serialPort.addEventListener(this);
				serialPort.notifyOnDataAvailable(true);
				serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			} catch (IOException e){
				logger.error("IOException: " + e);
			} catch (PortInUseException e){
				logger.error("PortInUseException: " + e);
			} catch (TooManyListenersException e){
				logger.error("TooManyListenersException: " + e);
			} catch (UnsupportedCommOperationException e){
				logger.error("UnsupportedCommOperationException: " + e);
			}
		} else {
			this.simulation = true;
		}
		this.thread.run();
	}
	
	private List<CommPortIdentifier> getComPorts() {
		List<CommPortIdentifier> portList = new ArrayList<CommPortIdentifier>();
		@SuppressWarnings("rawtypes")
		Enumeration ports = CommPortIdentifier.getPortIdentifiers();
		while (ports.hasMoreElements()) {
			CommPortIdentifier currentPort = (CommPortIdentifier) ports.nextElement();
			if (currentPort.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				portList.add(currentPort);
			}
		}
		return portList;
	}
	
	public void addListener(ArduinoListener listener){
		this.listeners.add(listener);
	}
	
	public void removeListener(ArduinoListener listener){
		this.listeners.remove(listener);
	}
	
	@Override
	synchronized public void serialEvent(SerialPortEvent serialEvent) {
		if (serialEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				while (inputStream.available() > 0) {
					synchronized (buffer) {
						if (bufferLast == buffer.length) {
							byte temp[] = new byte[bufferLast << 1];
							System.arraycopy(buffer, 0, temp, 0, bufferLast);
							buffer = temp;
						}
						char character = (char) inputStream.read();
						if (character == '{') {
							jsonString = String.valueOf(character);
						} else if (character == '}') {
							jsonString += String.valueOf(character);
							this.dispatchEvent(jsonString);
						} else {
							jsonString += String.valueOf(character);
						}
					}
				}

			} catch (IOException e) {
			} catch (Exception e) {
			}
		}
	}

	@Override
	public void run() {
		try {
			if(this.simulation) {
				logger.debug("Simulation mode activated, use keyboard to simulate arduino");
			}
			Thread.sleep(100);
		} catch (InterruptedException e) {
			System.out.println(e);
		}
	}
	
	// TODO: needs proper event dispatch and keylisteners
	public void dispatchEvent(String jsonString){
		@SuppressWarnings("unchecked")
		HashMap<String, String> hashMap = gson.fromJson(jsonString, HashMap.class);
		
		if(hashMap.containsKey(KEY_DIRECTION) && hashMap.containsKey(KEY_SPEED)){
			int direction = Integer.valueOf(hashMap.get(KEY_DIRECTION));
			int speed = Integer.valueOf(hashMap.get(KEY_SPEED));
			dispatchWheelEvent(direction, speed);
		}
	}
	
	public void dispatchWheelEvent(int direction, int speed){
		for(ArduinoListener listener : listeners){
			listener.wheelEvent(direction, speed);
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			logger.debug("Wheelevent LEFT");
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			logger.debug("Wheelevent RIGHT");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			logger.debug("ButtonEvent A");
		}
	}
}
