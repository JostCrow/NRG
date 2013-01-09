package asa.client;

import asa.client.resources.Resource;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class WheelOptionYesNo {
	private String description;
	private Image icon;	
	private boolean value;
	
	public WheelOptionYesNo(String description, String icon, boolean value){
		this.description = description;
		this.value = value;
		
		try
		{
			this.icon = new Image(icon);
		}
		catch (SlickException e)
		{
			
		}
	}

	public String getDescription(){
		return this.description;
	}
	
	public Image getIcon(){
		return this.icon;
	}
	
	public boolean getValue(){
		return this.value;
	}
}
