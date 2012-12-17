package asa.client;

import org.newdawn.slick.Image;

public class BackgroundImage{
	
	private float x, y;
	private Image backgroundImage;
	
	public BackgroundImage(Image backgroundImage){
		this.backgroundImage = backgroundImage;
	}
	
	public float getX(){
		return this.x;
	}
	
	public float getY(){
		return this.y;
	}
	
	public void setX(float x){
		this.x = x;
	}
	
	public void setY(float y){
		this.x = y;
	}
	
	public Image getImage(){
		return this.backgroundImage;
	}
}
