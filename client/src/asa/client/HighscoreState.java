package asa.client;
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
import service.Device;

public class HighscoreState extends ArduinoGameState{

	int stateID = -1;

	ServerAdapter server;
	double playerScore;
	double deviceScore;
	Device device;
	
	Logger logger = Logger.getLogger(this.getClass());
	List<WheelOption> wheelOptions = new ArrayList<WheelOption>();
	AngelCodeFont font;
	
	Image tandwiel1;
	Image tandwiel2;
	Image background;
	Image spinner;
	Image background_spinner;
	Image spinneroverlay;
	Image icon_background;
	
	Dimension center;
	
	int targetrotation = 0;
	int selectionDegrees = 180;
	int selectionScaleDistance = 30;
	int selectedOption = 0;
	float selectedScale = 1.5f;
	int oldSelectedOption = 0;
	int tandwielOffset = 30;
	float rotation = 0;
	double rotationEase = 5.0;

	public HighscoreState(int stateID, ServerAdapter server, double playerScore, double deviceScore, Device device) {
		super(stateID);
		this.server = server;
		this.playerScore = playerScore;
		this.deviceScore = deviceScore;
		this.device = device;
	}

	@Override
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
		
		wheelOptions.add(new WheelOption("Ja", device.getLogoUrl(), device.getPhotoUrl(), 0));
		wheelOptions.add(new WheelOption("Nee", device.getLogoUrl(), device.getPhotoUrl(), 0));
		
		center = new Dimension(AsaGame.SOURCE_RESOLUTION.width / 2 - 100, AsaGame.SOURCE_RESOLUTION.height / 2);
		selectionDegrees = 360/wheelOptions.size();
		tandwiel1 = new Image(Resource.getPath(Resource.TANDWIEL5));
		tandwiel2 = new Image(Resource.getPath(Resource.TANDWIEL6));
		spinner = new Image(Resource.getPath(Resource.SPINNER));
		spinneroverlay = new Image(Resource.getPath(Resource.SPINNER_OVERLAY));
		background_spinner = new Image(Resource.getPath(Resource.BACKGROUND_SPINNER));
		background = new Image(Resource.getPath(Resource.GAME_BACKGROUND));
		icon_background = new Image(Resource.getPath(Resource.ICON_BACKGROUND));
		font = new AngelCodeFont(Resource.getPath("OnzeFont.fnt"), Resource.getPath("OnzeFont_1.tga"));

	}

	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		background.draw(0,0);
		tandwiel1.draw(-tandwiel1.getWidth()/2, AsaGame.SOURCE_RESOLUTION.height/2-tandwiel1.getHeight()/2);
		tandwiel2.draw(tandwiel1.getWidth()/2-tandwielOffset-40, AsaGame.SOURCE_RESOLUTION.height / 2 - tandwiel2.getHeight());
		spinner.draw(center.getWidth() - spinner.getWidth() / 2, center.getHeight() - spinner.getHeight() / 2);
		spinneroverlay.draw(center.getWidth() - spinner.getWidth() / 2, center.getHeight() - spinner.getHeight() / 2);
		background_spinner.draw(center.getWidth() - background_spinner.getWidth() / 2, center.getHeight() - background_spinner.getHeight() / 2);
		graphics.setFont(font);
		
		for(int i = 0; i < wheelOptions.size(); i++){
			float offsetDegree = 360/wheelOptions.size();
			float degrees = (270 + ((rotation + offsetDegree*i) % 360))%360;
			float rad = (float) (degrees * (Math.PI / 180));
			float radius = 310;
			
			float x = (float) (center.getWidth() + radius * Math.cos(rad));
			float y = (float) (center.getHeight() + radius * Math.sin(rad));			
			
			WheelOption option = wheelOptions.get(i);
			Image optionIcon = option.getIcon();
			
			float distance = Math.abs(degrees - selectionDegrees);
			float scale = selectionScaleDistance / distance;
			if(scale > selectedScale){
				scale = selectedScale;
			} else if (scale < 1){
				scale = 1;
			}
			
			x = x - optionIcon.getWidth()*scale/2;
			y = y - optionIcon.getHeight()*scale/2;			
			icon_background.draw(x, y, scale);
			option.getIcon().draw(x, y, scale);
			
			if(degrees > 270-(selectionDegrees/2) && degrees < 270+(selectionDegrees/2)){
				if (selectedOption != oldSelectedOption)
				{
					logger.debug(option.getDescription());
				}
				oldSelectedOption = selectedOption;
				background = option.background();
				int length = String.valueOf(option.getDescription()).length();
				graphics.drawString(option.getDescription(), (center.getWidth()-((length)*13)), center.getHeight());
				selectedOption = i;				
			}
		}
	}

	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
		super.update(gameContainer, stateBasedGame, delta);
		rotation += (targetrotation - rotation) / rotationEase;
		tandwiel1.setRotation(rotation);
		tandwiel2.setRotation((float) ((float) -(rotation*1.818181818181818)+16.36363636363636));
		spinner.setRotation(rotation);
	}

}