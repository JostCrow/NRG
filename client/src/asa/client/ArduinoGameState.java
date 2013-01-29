package asa.client;

import asa.client.resources.Resource;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public abstract class ArduinoGameState extends BasicGameState {

	protected int stateID = -1;
	protected int speed = 1;
	protected float pulseScale = 1;
	protected boolean pulseInhance = false;
	protected DecimalFormat decimalFormat = new DecimalFormat("###,###,##0.00", new DecimalFormatSymbols(Locale.GERMAN));
	protected DecimalFormat specialFormat = new DecimalFormat("00.00000", new DecimalFormatSymbols(Locale.GERMAN));

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

	protected void setFont(Graphics graphics) throws SlickException{
		graphics.setFont(new AngelCodeFont(Resource.getPath("OnzeFont2.fnt"), Resource.getPath("OnzeFont2_0.tga")));
	}

	protected void calculatePulse(){
		if(pulseScale >= 1.05){
			pulseInhance = true;
		} else if (pulseScale <= 1) {
			pulseInhance = false;
		}

		if(pulseInhance){
			pulseScale -= 0.001f;
		} else {
			pulseScale += 0.001f;
		}
	}
}
