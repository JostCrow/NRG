package asa.client;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import asa.client.resources.Resource;

public class WheelOption {
	
	private Image background, icon;
	private String description;
	private double average;
	private float scale = 1;
	
	public WheelOption(String description, String icon, String background, double average){
		this.description = description;
		this.average = average;
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
	
	public Image background(){
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
}
