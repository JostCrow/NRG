package asa.client;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Observable;
import java.util.TooManyListenersException;

public class ComPortReader extends Observable implements Runnable, SerialPortEventListener {

	private boolean simulationMode = false;
	
	InputStream inputStream;
	SerialPort serialPort;
	Thread readThread;

	byte buffer[] = new byte[32768];
	int bufferIndex;
	int bufferLast;
	String jsonString;

	public static List<CommPortIdentifier> getComPorts() {
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

	// No portId given, simulate!
	public ComPortReader(){
		this.simulationMode = true;
		readThread = new Thread(this);
		readThread.start();
	}
	
	public ComPortReader(CommPortIdentifier portId) {
		try {
			serialPort = (SerialPort) portId.open("SimpleReadApp", 2000);
			inputStream = serialPort.getInputStream();
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
			serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
		} catch (IOException e){
			System.out.println("IOException: " + e);
		} catch (PortInUseException e){
			System.out.println("PortInUseException: " + e);
		} catch (TooManyListenersException e){
			System.out.println("TooManyListenersException: " + e);
		} catch (UnsupportedCommOperationException e){
			System.out.println("UnsupportedCommOperationException: " + e);
		}
		readThread = new Thread(this);
		readThread.start();
	}

	@Override
	public void run() {
		try {
			if(simulationMode) simulationLoop();
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			System.out.println(e);
		}
	}

	private void simulationLoop(){
		boolean loop = true;
		
		int direction = 0;
		int speed = 0;
		while(loop){
			try {
				if(direction == 0){
					speed++;
				} else {
					speed--;
				}
				if(speed >= 100 || speed <= 0){
					if(direction == 0){
						direction = 1;
					} else {
						direction = 0;
					}
				}
				String jsonString = "{direction = '" + direction + "', speed = '" + speed + "'}";
				this.setChanged();
				this.notifyObservers(jsonString);
				Thread.sleep(50);
			} catch (InterruptedException e){
				loop = false;
				System.out.println(e);
			}
		}
	}
	
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
							setChanged();
							notifyObservers(jsonString);
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

}