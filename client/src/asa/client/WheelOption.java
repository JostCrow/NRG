package asa.client;

import java.util.Random;

import org.apache.log4j.Logger;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import service.Device;

import asa.client.resources.Resource;

public class WheelOption {
	
	private Logger logger = Logger.getLogger(WheelOption.class);
	
	public static final int EASY = 1;
	public static final int MEDIUM = 2;
	public static final int HARD = 3;
	
	private Image background, icon;
	private float scale = 1;
	private int difficulty;
	
	private Device device;
	
	public WheelOption(Device device){
		this.device = device;
		
		// TODO: calculate proper difficulty;
		Random random = new Random();
		this.difficulty = random.nextInt(3) + 1;
		
		try {
			this.background = new Image(Resource.getPath(device.getBackgroundUrl()));
			this.icon = new Image(Resource.getPath(device.getLogoUrl()));
		} catch (SlickException e) {
			logger.error(e);
		}
	}

	public Device getDevice(){
		return this.device;
	}
	
	public Image getIcon(){
		return this.icon;
	}
	
	public Image getBackground(){
		return this.background;
	}
	
	public double getAverage(){
		return this.device.getWattTotal() / this.device.getDivideBy();
	}
	
	public float getScale(){
		return this.scale;
	}
	
	public void setScale(float scale){
		this.scale = scale;
	}
	
	public int getDifficulty(){
		return this.difficulty;
	}
}
