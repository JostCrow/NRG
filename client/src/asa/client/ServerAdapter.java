/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package asa.client;

import service.Device;
import service.DeviceService;
import service.Device_Service;
import service.Highscore;
import service.HighscoreService;
import service.Highscore_Service;

/**
 *
 * @author Corneel
 */
public class ServerAdapter {
	
	Device_Service deviceService = new Device_Service();
	DeviceService devicePort = deviceService.getDeviceServicePort();
	Highscore_Service highscoreService = new Highscore_Service();
	HighscoreService highscorePort = highscoreService.getHighscoreServicePort();
	
	public ServerAdapter()
	{
		
	}
	
	// Device functions
	public java.util.List<Device> getAllDevices() {
		System.out.println(deviceService.toString());
		System.out.println(devicePort.toString());
		return devicePort.getAllDevices();
	}

	public Device getDeviceById(int deviceId) {
		return devicePort.getDeviceById(deviceId);
	}	

	public boolean updateWattTotal(int deviceId, double watt) {
		return devicePort.updateWattTotal(deviceId, watt);
	}

	public void addDevice(Device device) {
		devicePort.addDevice(device);
	}

	public boolean getLiveData(int deviceId) {
		return devicePort.getLiveData(deviceId);
	}

	public void removeDevice(Device device) {
		devicePort.removeDevice(device);
	}

	public void updateDevice(Device device) {
		devicePort.updateDevice(device);
	}
	// Highscore functions	
	public java.util.List<Highscore> getAllHighscores() {
		return highscorePort.getAllHighscores();
	}
	
	public void addHighscore(Highscore highscore) {
		highscorePort.addHighscore(highscore);
	}
}
