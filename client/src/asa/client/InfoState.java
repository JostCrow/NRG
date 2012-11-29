package asa.client;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.util.Dimension;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import asa.client.resources.Resource;

public class InfoState extends ArduinoGameState {

	Image tandwiel1;
	Image tandwiel2;
	Image background;
	Image spinner;
	Image background_spinner;
	Image spinneroverlay;
	
	List<WheelOption> wheelOptions = Arrays.asList(
			new WheelOption("Koffie", Resource.ICON_KOFFIE, Resource.BACKGROUND_KOFFIE),
			new WheelOption("Printer", Resource.ICON_PRINTER, Resource.BACKGROUND_PRINTER),
			new WheelOption("Koffie", Resource.ICON_KOFFIE, Resource.BACKGROUND_KOFFIE),
			new WheelOption("Printer", Resource.ICON_PRINTER, Resource.BACKGROUND_PRINTER),
			new WheelOption("Koffie", Resource.ICON_KOFFIE, Resource.BACKGROUND_KOFFIE),
			new WheelOption("Printer", Resource.ICON_PRINTER, Resource.BACKGROUND_PRINTER),
			new WheelOption("Koffie", Resource.ICON_KOFFIE, Resource.BACKGROUND_KOFFIE),
			new WheelOption("Printer", Resource.ICON_PRINTER, Resource.BACKGROUND_PRINTER),
			new WheelOption("Cola", Resource.ICON_AUTOMAAT, Resource.BACKGROUND_AUTOMAAT)
	);
			
	
	int targetrotation = 0;
	int tandwielOffset = 30;
	
	float rotation = 0;
	double rotationEase = 5.0;
	
	Dimension screenSize;
	
	public InfoState(int stateID) {
		super(stateID);
	}

	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
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
		spinneroverlay = new Image(Resource.getPath(Resource.SPINNER_OVERLAY));
		background_spinner = new Image(Resource.getPath(Resource.BACKGROUND_SPINNER));
		background = new Image(Resource.getPath(Resource.BACKGROUND_BEAMER));
		
	}

	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		background.draw(0,0);
		tandwiel1.draw(-tandwiel1.getWidth() / 2, gameContainer.getHeight() / 2 - tandwiel1.getHeight() / 2);
		tandwiel2.draw(tandwiel1.getWidth()/2-tandwielOffset-40, gameContainer.getHeight() / 2 - tandwiel2.getHeight());
		spinner.draw(gameContainer.getWidth() / 2 - spinner.getWidth() / 2 - 100, gameContainer.getHeight() / 2 - spinner.getHeight() / 2);
		spinneroverlay.draw(gameContainer.getWidth() / 2 - spinner.getWidth() / 2 - 100, gameContainer.getHeight() / 2 - spinner.getHeight() / 2);
		background_spinner.draw(gameContainer.getWidth() / 2 - background_spinner.getWidth() / 2 - 100, gameContainer.getHeight() / 2 - background_spinner.getHeight() / 2);
		
		Dimension center = new Dimension(gameContainer.getWidth() / 2 - 100, gameContainer.getHeight() / 2);
		
		
		for(int i = 0; i < wheelOptions.size(); i++){
			float offsetDegree = 360/wheelOptions.size()*i;
			float degrees = (rotation + offsetDegree) % 360;
			float rad = (float) (degrees * (Math.PI / 180));
			float radius = 310;
			
			float x = (float) (center.getWidth() + radius * Math.cos(rad));
			float y = (float) (center.getHeight() + radius * Math.sin(rad));
			
			WheelOption option = wheelOptions.get(i);
			Image optionIcon = option.getIcon();
			x = x - optionIcon.getWidth()/2;
			y = y - optionIcon.getHeight()/2;
			option.getIcon().draw(x, y);
		}
	}

	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
		super.update(gameContainer, stateBasedGame, delta);
		rotation += (targetrotation - rotation) / rotationEase;
		tandwiel1.setRotation(rotation);
		tandwiel2.setRotation((float) ((float) -(rotation*1.818181818181818)+16.36363636363636));
		spinner.setRotation((float) (rotation*1.5));
	}

}