package asa.client;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import asa.client.resources.Resource;

public class WheelOption {
	
	private Image background, icon;
	private String description;
	
	public WheelOption(String description, String icon, String background){
		this.description = description;
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
}