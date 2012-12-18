package asa.client;

import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import asa.client.resources.Resource;

public class WheelOption {
	
	public static final int EASY = 1;
	public static final int MEDIUM = 2;
	public static final int HARD = 3;
	
	private Image background, icon;
	private String description;
	private double average;
	private float scale = 1;
	private int deviceId;
	private int difficulty;
	
	public WheelOption(int id, String description, String icon, String background, double average){
		this.description = description;
		this.deviceId = id;
		this.average = average;
		
		// TODO: calculate proper difficulty;
		Random random = new Random();
		this.difficulty = random.nextInt(3) + 1;
		
		try {
			this.background = new Image(Resource.getPath(background));
			this.icon = new Image(Resource.getPath(icon));
		} catch (SlickException e) {
			
		}
	}

	public String getDescription(){
		return this.description;
	}
	
	public Image getIcon(){
		return this.icon;
	}
	
	public Image getBackground(){
		return this.background;
	}
	
	public double getAverage(){
		return this.average;
	}
	
	public float getScale(){
		return this.scale;
	}
	
	public void setScale(float scale){
		this.scale = scale;
	}
	
	public int getDeviceId(){
		return deviceId;
	}
	
	public int getDifficulty(){
		return this.difficulty;
	}
}
