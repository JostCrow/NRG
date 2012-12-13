package asa.client;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public abstract class ArduinoGameState extends BasicGameState {

	int stateID = -1;
	int speed = 1;
	DecimalFormat decimalFormat = new DecimalFormat("###,###,##0.00", new DecimalFormatSymbols(Locale.GERMAN));
	
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
		if(input.isKeyPressed(Input.KEY_A)){
			arduino.dispatchButtonEvent();
		}
	}
}
