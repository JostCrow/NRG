package asa.client;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.lwjgl.util.Dimension;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.state.transition.Transition;

import service.Device;
import asa.client.DTO.GameData;
import asa.client.resources.Resource;
import java.util.logging.Level;

public class InfoState extends ArduinoGameState {

	ServerAdapter server;
	GameData gameData;

	Image background;
	Image background2;
	Image tandwiel1;
	Image tandwiel2;
	Image spinner;
	Image background_spinner;
	Image spinneroverlay;
	Image icon_background_easy;
	Image icon_background_medium;
	Image icon_background_hard;
	Image label_easy;
	Image label_medium;
	Image label_hard;
	Image icons_details;

	UnicodeFont font_label;
	UnicodeFont font_details;

	Logger logger = Logger.getLogger(this.getClass());

	List<WheelOption> wheelOptions = new ArrayList<WheelOption>();
	ArrayList<BackgroundImage> backgrounds = new ArrayList<BackgroundImage>();

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
	String lastLoaded = "";

	float position = 1920;
	boolean slide = false;

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
		background = new Image(Resource.getPath(Resource.GAME_BACKGROUND));
		background2 = new Image(Resource.getPath(Resource.BACKGROUND_KOFFIE));
		tandwiel1 = new Image(Resource.getPath(Resource.TANDWIEL5));
		tandwiel2 = new Image(Resource.getPath(Resource.TANDWIEL6));
		background_spinner = new Image(Resource.getPath(Resource.BACKGROUND_SPINNER));
		spinner = new Image(Resource.getPath(Resource.SPINNER));
		spinneroverlay = new Image(Resource.getPath(Resource.SPINNER_OVERLAY));
		icon_background_easy = new Image(Resource.getPath(Resource.ICON_BACKGROUND_EASY));
		icon_background_medium = new Image(Resource.getPath(Resource.ICON_BACKGROUND_MEDIUM));
		icon_background_hard = new Image(Resource.getPath(Resource.ICON_BACKGROUND_HARD));
		label_easy = new Image(Resource.getPath(Resource.LABEL_EASY));
		label_medium = new Image(Resource.getPath(Resource.LABEL_MEDIUM));
		label_hard = new Image(Resource.getPath(Resource.LABEL_HARD));
		icons_details = new Image(Resource.getPath(Resource.ICONS_DETAILS));
		font_label = Resource.getFont(Resource.FONT_SANCHEZ, 35, Color.BLACK);
		font_details = Resource.getFont(Resource.FONT_SANCHEZ, 25, Color.WHITE);
	}

	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		background2.draw(0, 0);

		if(slide){
			background.draw(position, 0);
		}

		tandwiel1.draw(-tandwiel1.getWidth()/2, AsaGame.SOURCE_RESOLUTION.height/2-tandwiel1.getHeight()/2);
		tandwiel2.draw(tandwiel1.getWidth()/2-tandwielOffset-40, AsaGame.SOURCE_RESOLUTION.height / 2 - tandwiel2.getHeight());
		background_spinner.draw(center.getWidth() - background_spinner.getWidth() / 2, center.getHeight() - background_spinner.getHeight() / 2);
		spinner.draw(center.getWidth() - spinner.getWidth() / 2, center.getHeight() - spinner.getHeight() / 2);
		spinneroverlay.draw(center.getWidth() - spinner.getWidth() / 2, center.getHeight() - spinner.getHeight() / 2);

		float offsetDegree = 360/wheelOptions.size();
		float radius = 313;
		float targetScale = 1;
		// Render icons
		for(int i = 0; i < wheelOptions.size(); i++){
			float degrees = (360+((rotation + offsetDegree*i) % 360))%360;
			float rad = (float) (degrees * (Math.PI / 180));

			float x = (float) (center.getWidth() + radius * Math.cos(rad));
			float y = (float) (center.getHeight() + radius * Math.sin(rad));

			WheelOption option = wheelOptions.get(i);

			if(degrees > 270-(selectionDegrees/2) && degrees <= 270+(selectionDegrees/2)){
				selectedOption = i;
				background = option.getBackground();
				if(selectedOption != oldSelectedOption){
					slide = true;
				}
				oldSelectedOption = selectedOption;
				gameData.setDeviceId(option.getDevice().getId());
				targetScale = 1.5f;
			}
			option.setScale(option.getScale() + (targetScale - option.getScale())/5);
			x = x - option.getIcon().getWidth()*option.getScale()/2;
			y = y - option.getIcon().getHeight()*option.getScale()/2;

			Image icon_background;
			switch(option.getDifficulty()){
				case WheelOption.EASY:
					icon_background = icon_background_easy;
					break;
				case WheelOption.MEDIUM:
					icon_background = icon_background_medium;
					break;
				case WheelOption.HARD:
					icon_background = icon_background_hard;
					break;
				default: icon_background = icon_background_medium;
			}

			icon_background.draw(x, y, option.getScale());
			option.getIcon().draw(x, y, option.getScale());
		}

		// Draw selected option in center;
		WheelOption option = wheelOptions.get(selectedOption);

		Image selectedIcon = option.getIcon();
		selectedIcon.draw(center.getWidth() - selectedIcon.getWidth(), -120 + center.getHeight() - selectedIcon.getHeight(), 2);

		Image label_difficulty;
		switch(option.getDifficulty()){
			case WheelOption.EASY:
				label_difficulty = label_easy;
				break;
			case WheelOption.MEDIUM:
				label_difficulty = label_medium;
				break;
			case WheelOption.HARD:
				label_difficulty = label_hard;
				break;
				default: label_difficulty = label_medium;
		}

		label_difficulty.draw(center.getWidth()-label_difficulty.getWidth()/2, center.getHeight()+1);

		graphics.setFont(font_label);
		graphics.drawString(option.getDevice().getName(), center.getWidth()-font_label.getWidth(option.getDevice().getName())/2, center.getHeight() - 50);

		icons_details.draw(center.getWidth()-125, center.getHeight()+50);

		graphics.setFont(font_details);
		graphics.drawString(option.getDevice().getLocation(), center.getWidth()-75, 60+center.getHeight());
		graphics.drawString("30 sec.", center.getWidth()-75, 60+center.getHeight()+47);
		graphics.drawString(decimalFormat.format(option.getAverage()) + " Kw.", center.getWidth()-75, 60+center.getHeight()+47*2);
	}


	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
		super.update(gameContainer, stateBasedGame, delta);
		rotation += (targetrotation - rotation) / rotationEase;
		tandwiel1.setRotation(rotation);
		tandwiel2.setRotation((float) ((float) -(rotation*1.818181818181818)+16.36363636363636));
		spinner.setRotation(rotation);
		if(slide){
			position = position - 76.8f;
		}
		if(position <= 0){
			background2 = background;
			slide = false;
			position = 1920;
		}
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
				stateBasedGame.enterState(AsaGame.GAMESTATE, AsaGame.FADEOUT, AsaGame.FADEIN);
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
			wheelOptions.add(new WheelOption(device));
		}
	}

}