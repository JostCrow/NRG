package asa.client;
import org.lwjgl.util.Dimension;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import asa.client.resources.Resource;

public class InfoState extends BasicGameState {

	int stateID = -1;
	Image tandwiel1;
	Image tandwiel2;
	Image background;
	Image spinner;
	Image background_spinner;
	
	int targetrotation = 0;
	int tandwielOffset = 30;
	
	float rotation = 0;
	double rotationEase = 5.0;
	
	Dimension screenSize;
	
	public InfoState(int stateID) {
		this.stateID = stateID;
	}

	@Override
	public int getID() {
		return stateID;
	}

	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
		Arduino arduino = Arduino.getInstance();
		arduino.addListener(new ArduinoAdapter() {
			@Override
			public void wheelEvent(int direction, int speed) {
				if(direction == 1){
					targetrotation += 3*speed;
				} else {
					targetrotation -= 3*speed;
				}
			}
		});
		
		tandwiel1 = new Image(Resource.getPath(Resource.TANDWIEL5));
		tandwiel2 = new Image(Resource.getPath(Resource.TANDWIEL6));
		spinner = new Image(Resource.getPath(Resource.SPINNER));
		background_spinner = new Image(Resource.getPath(Resource.BACKGROUND_SPINNER));
		background = new Image(Resource.getPath(Resource.BACKGROUND_KOFFIE));
		
	}

	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		background.draw(0,0);
		tandwiel1.draw(-tandwiel1.getWidth() / 2, gameContainer.getHeight() / 2 - tandwiel1.getHeight() / 2);
		tandwiel2.draw(tandwiel1.getWidth()/2-tandwielOffset-40, gameContainer.getHeight() / 2 - tandwiel2.getHeight());
		spinner.draw(gameContainer.getWidth() / 2 - spinner.getWidth() / 2 - 100, gameContainer.getHeight() / 2 - spinner.getHeight() / 2);
		background_spinner.draw(gameContainer.getWidth() / 2 - background_spinner.getWidth() / 2 - 100, gameContainer.getHeight() / 2 - background_spinner.getHeight() / 2);
	}

	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
		rotation += (targetrotation - rotation) / rotationEase;
		tandwiel1.setRotation(rotation);
		tandwiel2.setRotation((float) ((float) -(rotation*1.818181818181818)+16.36363636363636));
		spinner.setRotation((float) (rotation*1.5));
	}

}