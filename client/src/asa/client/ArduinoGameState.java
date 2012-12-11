package asa.client;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import service.Device;

public abstract class ArduinoGameState extends BasicGameState {

	int stateID = -1;
	int speed = 1;
	int selectedDeviceId = 0;
	
	Arduino arduino = Arduino.getInstance();
	
	public ArduinoGameState(int stateID){
		this.stateID = stateID;
	}
	
	@Override
	public int getID() {
		return stateID;
	}

	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
		Input input = gameContainer.getInput();
		if(input.isKeyDown(Input.KEY_LEFT)){
			arduino.dispatchWheelEvent(0, speed);
		}
		if(input.isKeyDown(Input.KEY_RIGHT)){
			arduino.dispatchWheelEvent(1, speed);
		}
	}
}
