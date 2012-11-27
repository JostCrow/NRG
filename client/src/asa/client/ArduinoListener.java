package asa.client;

public interface ArduinoListener {

	public void WheelEvent(int direction, int speed);
	public void ButtonEvent();
	
}
