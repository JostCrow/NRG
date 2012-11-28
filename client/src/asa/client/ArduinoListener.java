package asa.client;

public interface ArduinoListener {

	public void wheelEvent(int direction, int speed);
	public void buttonEvent();
	
}
