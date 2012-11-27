package asa.client;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import asa.client.resources.Resource;

public class InfoState extends BasicGameState {

	int stateID = -1;
	Image menuOptions;
	int x = 0;
	int y = 0;
	
	
	public InfoState(int stateID) {
		this.stateID = stateID;
	}

	@Override
	public int getID() {
		return stateID;
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		Arduino arduino = Arduino.getInstance();
		arduino.addListener(new ArduinoListener() {
			
			@Override
			public void WheelEvent(int direction, int speed) {
				
			}
			
			@Override
			public void ButtonEvent() {
				// TODO Auto-generated method stub
				
			}
		});
		
		menuOptions = new Image("C:/Users/Rene van Aerle/Git/PhysicASA/client/src/asa/client/resources/gearsanimation/00.png");
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		menuOptions.draw(x, y);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

	}

}