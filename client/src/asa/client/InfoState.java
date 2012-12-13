package asa.client;

import asa.client.DTO.GameData;
import asa.client.resources.Resource;
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
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.state.transition.Transition;
import service.Device;

public class InfoState extends ArduinoGameState {

	ServerAdapter server;
	GameData gameData;
	
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
	
	int selectionDegrees = 270;
	int selectionScaleDistance = 30;
	int selectedOption = 0;
	float selectedScale = 1.5f;
	int oldSelectedOption = 0;
	
	public InfoState(int stateID, ServerAdapter server, GameData gameData) {
		super(stateID);
		this.server = server;
		this.gameData = gameData;
		loadWheelOptions();
	}

	@Override
	public void init(GameContainer gameContainer, final StateBasedGame stateBasedGame) throws SlickException {		
		center = new Dimension(AsaGame.SOURCE_RESOLUTION.width / 2 - 100, AsaGame.SOURCE_RESOLUTION.height / 2);
		selectionDegrees = 360/wheelOptions.size();
		tandwiel1 = new Image(Resource.getPath(Resource.TANDWIEL5));
		tandwiel2 = new Image(Resource.getPath(Resource.TANDWIEL6));
		spinner = new Image(Resource.getPath(Resource.SPINNER));
		spinneroverlay = new Image(Resource.getPath(Resource.SPINNER_OVERLAY));
		background_spinner = new Image(Resource.getPath(Resource.BACKGROUND_SPINNER));
		background = new Image(Resource.getPath(Resource.BACKGROUND_KOFFIE));
		icon_background = new Image(Resource.getPath(Resource.ICON_BACKGROUND));
		
	}

	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		background.draw(0,0);
		tandwiel1.draw(-tandwiel1.getWidth()/2, AsaGame.SOURCE_RESOLUTION.height/2-tandwiel1.getHeight()/2);
		tandwiel2.draw(tandwiel1.getWidth()/2-tandwielOffset-40, AsaGame.SOURCE_RESOLUTION.height / 2 - tandwiel2.getHeight());
		spinner.draw(center.getWidth() - spinner.getWidth() / 2, center.getHeight() - spinner.getHeight() / 2);
		spinneroverlay.draw(center.getWidth() - spinner.getWidth() / 2, center.getHeight() - spinner.getHeight() / 2);
		background_spinner.draw(center.getWidth() - background_spinner.getWidth() / 2, center.getHeight() - background_spinner.getHeight() / 2);
		graphics.setFont(new AngelCodeFont(Resource.getPath("OnzeFont2.fnt"), Resource.getPath("OnzeFont2_0.tga")));
		
		for(int i = 0; i < wheelOptions.size(); i++){
			float offsetDegree = 360/wheelOptions.size();
			float degrees = (270 + ((rotation + offsetDegree*i) % 360))%360;
			if(degrees < 0){
				degrees = degrees + 360;
			}
			float rad = (float) (degrees * (Math.PI / 180));
			float radius = 313;
			
			float x = (float) (center.getWidth() + radius * Math.cos(rad));
			float y = (float) (center.getHeight() + radius * Math.sin(rad));
			
			
			
			
			WheelOption option = wheelOptions.get(i);
			Image optionIcon = option.getIcon();
			
			float biggerThanDegrees = 270 + (offsetDegree / 2);
			if (biggerThanDegrees > 360) {
				biggerThanDegrees = biggerThanDegrees - 360;
			}
			
			if (degrees >= 270 - (offsetDegree / 2) && degrees < biggerThanDegrees) {
				x = x - (float) (optionIcon.getWidth() * 1.2 / 2);
				y = y - (float) (optionIcon.getHeight() * 1.2 / 2);
				icon_background.draw(x, y, (float) 1.2);
				option.getIcon().draw(x, y, (float) 1.2);
			} else{
				x = x - (float) (optionIcon.getWidth() * 1 / 2);
				y = y - (float) (optionIcon.getHeight() * 1 / 2);
				icon_background.draw(x, y);
				option.getIcon().draw(x, y);
			}
			
			if(degrees >= 270 - (offsetDegree / 2) && degrees < biggerThanDegrees) {
				oldSelectedOption = selectedOption;
				background = option.background();
				int length = decimalFormat.format(option.getAverage()).length();
				graphics.drawString(decimalFormat.format(option.getAverage()), (center.getWidth()-((length)*13)), center.getHeight());
				selectedOption = i;
				gameData.setDeviceId(option.getDeviceId());
			}
		}
		
		WheelOption option = wheelOptions.get(selectedOption);
	}
	
	
	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
		super.update(gameContainer, stateBasedGame, delta);
		rotation += (targetrotation - rotation) / rotationEase;
		tandwiel1.setRotation(rotation);
		tandwiel2.setRotation((float) ((float) -(rotation*1.818181818181818)+16.36363636363636));
		spinner.setRotation(rotation);
	}
	
	@Override
	public void enter(GameContainer gameContainer, final StateBasedGame stateBasedGame){
		arduino.addListener(new ArduinoAdapter() {
			@Override
			public void wheelEvent(int direction, int speed) {
				if(direction == 1){
					targetrotation += 3*speed;
				} else {
					targetrotation -= 3*speed;
				}
			}
			
			@Override
			public void buttonEvent(){
				Transition fadeIn = new FadeInTransition();
				Transition fadeOut = new FadeOutTransition();
				stateBasedGame.enterState(AsaGame.GAMESTATE, fadeOut, fadeIn);
			}
		});
	}
	
	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException {
		arduino.removeAllListeners();
	}

	private void loadWheelOptions() {
		List<Device> deviceList = server.getAllDevices();
		for(Device device : deviceList){
			wheelOptions.add(new WheelOption(device.getId(), device.getName(), device.getLogoUrl(), device.getPhotoUrl(), ( device.getWattTotal()/device.getDivideBy() ) ));
		}
	}

}