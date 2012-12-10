package asa.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.lwjgl.util.Dimension;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import service.Device;
import asa.client.resources.Resource;

public class InfoState extends ArduinoGameState {

	ServerAdapter server;
	Image tandwiel1;
	Image tandwiel2;
	Image background;
	Image spinner;
	Image background_spinner;
	Image spinneroverlay;
	Image icon_background;
	
	Logger logger = Logger.getLogger(this.getClass());
	
	List<WheelOption> wheelOptions = new ArrayList<WheelOption>();
			
	
	int targetrotation = 0;
	int tandwielOffset = 30;
	
	float rotation = 0;
	float spinnerrotation = 0;
	double rotationEase = 5.0;
	
	Dimension screenSize;
	Dimension center;
	
	int selectionDegrees = 0;
	int selectionScaleDistance = 180;
	int selectedOption = 0;
	float selectedScale = 1.5f;
	int oldSelectedOption = 0;
	
	public InfoState(int stateID, ServerAdapter server) {
		super(stateID);
		this.server = server;
		loadWheelOptions();
	}

	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
		arduino.addListener(new ArduinoAdapter() {
			@Override
			public void wheelEvent(int direction, int speed) {
				if(direction == 1){
					targetrotation += 1*speed;
				} else {
					targetrotation -= 1*speed;
				}
			}
		});
		
		center = new Dimension(AsaGame.SOURCE_RESOLUTION.width / 2 - 100, AsaGame.SOURCE_RESOLUTION.height / 2);
		selectionDegrees = 360/wheelOptions.size();
		tandwiel1 = new Image(Resource.getPath(Resource.TANDWIEL5));
		tandwiel2 = new Image(Resource.getPath(Resource.TANDWIEL6));
		background_spinner = new Image(Resource.getPath(Resource.BACKGROUND_SPINNER));
		spinner = new Image(Resource.getPath(Resource.SPINNER));
		spinneroverlay = new Image(Resource.getPath(Resource.SPINNER_OVERLAY));
		background = new Image(Resource.getPath(Resource.BACKGROUND_KOFFIE));
		icon_background = new Image(Resource.getPath(Resource.ICON_BACKGROUND));
		
	}

	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		background.draw(0,0);
		tandwiel1.draw(-tandwiel1.getWidth()/2, AsaGame.SOURCE_RESOLUTION.height/2-tandwiel1.getHeight()/2);
		tandwiel2.draw(tandwiel1.getWidth()/2-tandwielOffset-40, AsaGame.SOURCE_RESOLUTION.height / 2 - tandwiel2.getHeight());
		background_spinner.draw(center.getWidth() - background_spinner.getWidth() / 2, center.getHeight() - background_spinner.getHeight() / 2);
		spinner.draw(center.getWidth() - spinner.getWidth() / 2, center.getHeight() - spinner.getHeight() / 2);
		spinneroverlay.draw(center.getWidth() - spinner.getWidth() / 2, center.getHeight() - spinner.getHeight() / 2);
		graphics.setFont(new AngelCodeFont(Resource.getPath("OnzeFont.fnt"), Resource.getPath("OnzeFont_1.tga")));
		
		for(int i = 0; i < wheelOptions.size(); i++){
			float offsetDegree = 360/wheelOptions.size();
			float degrees = (360+((rotation + offsetDegree*i) % 360))%360;
			float rad = (float) (degrees * (Math.PI / 180));
			float radius = 310;
			
			float x = (float) (center.getWidth() + radius * Math.cos(rad));
			float y = (float) (center.getHeight() + radius * Math.sin(rad));
			
			WheelOption option = wheelOptions.get(i);
			Image optionIcon = option.getIcon();
			
			float targetScale = 1;
			if(degrees > 270-(selectionDegrees/2) && degrees < 270+(selectionDegrees/2)){
				oldSelectedOption = selectedOption;
				background = option.background();
				int length = String.valueOf(option.getAverage()).length();
				graphics.drawString(option.getAverage() + "", (center.getWidth()-((length)*13)), center.getHeight());
				selectedOption = i;
				targetScale = 2;
			}
			option.setScale(option.getScale() + (targetScale - option.getScale())/5);
			x = x - optionIcon.getWidth()*option.getScale()/2;
			y = y - optionIcon.getHeight()*option.getScale()/2;			
			icon_background.draw(x, y, option.getScale());
			option.getIcon().draw(x, y, option.getScale());
		}
		
		WheelOption option = wheelOptions.get(selectedOption);
		logger.debug(option.getDescription());
	}

	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
		super.update(gameContainer, stateBasedGame, delta);
		rotation += (targetrotation - rotation) / rotationEase;
		tandwiel1.setRotation(rotation);
		tandwiel2.setRotation((float) ((float) -(rotation*1.818181818181818)+16.36363636363636));
		spinner.setRotation(rotation);
	}

	private void loadWheelOptions() {
		List<Device> deviceList = server.getAllDevices();
		for(Device device : deviceList){
			wheelOptions.add(new WheelOption(device.getName(), device.getLogoUrl(), device.getPhotoUrl(), ( device.getWattTotal()/device.getDivideBy() ) ));
		}
	}

}