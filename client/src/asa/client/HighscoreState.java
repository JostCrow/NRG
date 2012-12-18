package asa.client;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.lwjgl.util.Dimension;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.state.StateBasedGame;

import service.Device;
import service.Highscore;
import asa.client.DTO.GameData;
import asa.client.resources.Resource;

public class HighscoreState extends ArduinoGameState {
	
	ServerAdapter server;
	GameData gameData;
	double playerScore;
	double deviceScore;
	Device device;
	Logger logger = Logger.getLogger(this.getClass());
	List<WheelOptionYesNo> wheelOptions = new ArrayList<WheelOptionYesNo>();
	UnicodeFont font;
	/**
	 * mode 1: Able to choose yes or no
	 * mode 2: Automaticaly making picture (can be skipped)
	 * mode 3: Able to scroll through highscorelist
	 */
	int mode;
	boolean waitingForButton;
	String underSpinner = "Foto maken bij behaalde score?";
	String spinnerSouth = "";
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
	StateBasedGame basedGame;
	
	int appResWidth = AsaGame.SOURCE_RESOLUTION.width;
	int appResHeight = AsaGame.SOURCE_RESOLUTION.height;
	List<Highscore> highscores;
	boolean longlist;
	int topDraw;
	int scrollDelta;
	int scoreHeight = (appResHeight/8);

	public HighscoreState(int stateID, ServerAdapter server, GameData gameData) {
		super(stateID);
		this.server = server;
		this.gameData = gameData;
	}

	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
		wheelOptions.add(new WheelOptionYesNo("Ja", "icon_beamer.png", true));
		wheelOptions.add(new WheelOptionYesNo("Nee", "icon_automaat.png", false));
		
		center = new Dimension(AsaGame.SOURCE_RESOLUTION.width / 2 - 100, AsaGame.SOURCE_RESOLUTION.height / 2);
		selectionDegrees = 360 / wheelOptions.size();
		tandwiel1 = new Image(Resource.getPath(Resource.TANDWIEL5));
		tandwiel2 = new Image(Resource.getPath(Resource.TANDWIEL6));
		spinner = new Image(Resource.getPath(Resource.SPINNER));
		spinneroverlay = new Image(Resource.getPath(Resource.SPINNER_OVERLAY));
		background_spinner = new Image(Resource.getPath(Resource.BACKGROUND_SPINNER));
		background = new Image(Resource.getPath(Resource.GAME_BACKGROUND));
		icon_background = new Image(Resource.getPath(Resource.ICON_BACKGROUND_EASY));
		font = Resource.getFont(Resource.FONT_SANCHEZ, 10, Color.WHITE);
	}

	@Override
	public void enter(GameContainer gameContainer, final StateBasedGame stateBasedGame) {
		
		mode = 1;
		
		playerScore = gameData.getPlayerScore();
		deviceScore = gameData.getDeviceScore();
		
		spinnerSouth = decimalFormat.format(playerScore) + " kWh, " + decimalFormat.format(deviceScore);
		
		basedGame = stateBasedGame;
		this.playerScore = gameData.getPlayerScore();
		this.deviceScore = gameData.getDeviceScore();
		this.device = server.getDeviceById(gameData.getDeviceId());
		try {
			background = new Image(Resource.getPath(device.getPhotoUrl()));
		} catch (SlickException ex) {
		}
		
		caclulateSelected();

		arduino.addListener(new ArduinoAdapter() {
			@Override
			public void wheelEvent(int direction, int speed) {
				if (direction == 1) {
					targetrotation += 3 * speed;
				} else {
					targetrotation -= 3 * speed;
				}
			}

			@Override
			public void buttonEvent() {
				if (waitingForButton) {
					waitingForButton = !waitingForButton;
					if (mode == 1){
						WheelOptionYesNo selected = wheelOptions.get(selectedOption);
						if (selected.getValue()) {
							MakePhoto();
						} else if (!selected.getValue()) {
							ActivateHighscoreList();
						}
					} else if (mode == 3){
						stateBasedGame.enterState(AsaGame.INFOSTATE);
					}
				}
			}
		});
		ActivateButton();
	}
	
	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException {
		arduino.removeAllListeners();
	}

	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		background.draw(0, 0);
		tandwiel1.draw(-tandwiel1.getWidth() / 2, AsaGame.SOURCE_RESOLUTION.height / 2 - tandwiel1.getHeight() / 2);
		tandwiel2.draw(tandwiel1.getWidth() / 2 - tandwielOffset - 40, AsaGame.SOURCE_RESOLUTION.height / 2 - tandwiel2.getHeight());
		spinner.draw(center.getWidth() - spinner.getWidth() / 2, center.getHeight() - spinner.getHeight() / 2);
		spinneroverlay.draw(center.getWidth() - spinner.getWidth() / 2, center.getHeight() - spinner.getHeight() / 2);
		background_spinner.draw(center.getWidth() - background_spinner.getWidth() / 2, center.getHeight() - background_spinner.getHeight() / 2);
		graphics.setFont(font);

		if (mode == 1) {

			for (int i = 0; i < wheelOptions.size(); i++) {
				float offsetDegree = 360 / wheelOptions.size();
				float degrees = (270 + ((rotation) % 360 + offsetDegree * i) % 360) % 360;
				if(degrees < 0){
					degrees = degrees + 360;
				}
				float rad = (float) (degrees * (Math.PI / 180));
				float radius = 313;

				float x = (float) (center.getWidth() + radius * Math.cos(rad));
				float y = (float) (center.getHeight() + radius * Math.sin(rad));

				WheelOptionYesNo option = wheelOptions.get(i);
				Image optionIcon = option.getIcon();

				float biggerThanDegrees = 270 + (offsetDegree / 2);
				if (biggerThanDegrees > 360) {
					biggerThanDegrees = biggerThanDegrees - 360;
				}
				
				if (degrees >= 270 - (offsetDegree / 2) && degrees < biggerThanDegrees) {
					x = x - (float) (optionIcon.getWidth() * 1.3 / 2);
					y = y - (float) (optionIcon.getHeight() * 1.3 / 2);
					icon_background.draw(x, y, (float) 1.3);
					graphics.drawString(option.getDescription(), x, y);
				} else{
					x = x - (float) (optionIcon.getWidth() * 1 / 2);
					y = y - (float) (optionIcon.getHeight() * 1 / 2);
					icon_background.draw(x, y);
					graphics.drawString(option.getDescription(), x, y);
				}

				if (degrees >= 270 - (offsetDegree / 2) && degrees < biggerThanDegrees) {
					if (selectedOption != oldSelectedOption) {
						logger.debug(option.getDescription());
					}
					oldSelectedOption = selectedOption;
					selectedOption = i;
				}
				
				int scoreLength = spinnerSouth.length();
				int questionLength = underSpinner.length();
				graphics.drawString(spinnerSouth, (center.getWidth() - ((scoreLength) * 13)), center.getHeight() + 160);
				graphics.drawString(underSpinner, (center.getWidth() - ((questionLength) * 13)), center.getHeight() + 400);
			}
		}
		else if (mode == 2)
		{
			graphics.drawString(spinnerSouth, (center.getWidth() - 13), center.getHeight() + 160);				
		}
		else if (mode == 3)
		{
			graphics.drawString(spinnerSouth, (center.getWidth() - 13), center.getHeight() + 160);
			underSpinner = highscores.size() + ", " + topDraw + ", " + rotation + ", " + scoreHeight + ", " + scrollDelta;
			graphics.drawString(underSpinner, center.getWidth(), center.getHeight() + 400);
			for (int i = 0 ; i < 9 ; i++)
			{
				if(topDraw+i >= highscores.size())
				{
					break;
				}				
				
				int topLeftX = (appResWidth - appResWidth/4);
				int topLeftY = (scoreHeight*i) + scrollDelta;
				Highscore score = highscores.get(topDraw+i);
				int rank = topDraw+i+1;
				graphics.drawLine(topLeftX, topLeftY, appResWidth, topLeftY);
				graphics.setLineWidth(3.0f);
				graphics.drawString(rank + ": " + score.getScore() + ", " + topLeftY+ ", " + i, topLeftX, topLeftY + (scoreHeight/2));				
			}
		}
	}

	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
		super.update(gameContainer, stateBasedGame, delta);
		rotation += (targetrotation - rotation) / rotationEase;
		tandwiel1.setRotation(rotation);
		tandwiel2.setRotation((float) ((float) -(rotation * 1.818181818181818) + 16.36363636363636));
		spinner.setRotation(rotation);
		
		if (mode == 3)
		{
			int possibleTopDraw = (int) ((rotation*-1)/scoreHeight);
			if (possibleTopDraw>=0)
			{
				scrollDelta = (int)((rotation) % scoreHeight);
				topDraw = possibleTopDraw;
			}
			else{
				scrollDelta = (int)(((possibleTopDraw*-1)*scoreHeight) + (rotation%scoreHeight));
			}
		}
	}
	
	public void MakePhoto()
	{
		mode = 2;
		spinnerSouth = "";
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				spinnerSouth = "3";
			}
		}, 1000);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				spinnerSouth = "2";
			}
		}, 2000);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				spinnerSouth = "1";
			}
		}, 3000);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				spinnerSouth = "Foto maken";
			}
		}, 4000);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				spinnerSouth = "Foto laten zien";
			}
		}, 5500);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				ActivateHighscoreList();
				
			}
		}, 9000);
	}
	
	public void ActivateHighscoreList()
	{
		spinnerSouth = "Scrolllist";
		server.addHighscore(playerScore, "new Photo");
		
		highscores = server.getAllHighscores();
		if (highscores.size() > 9)
		{
			longlist = true;
		}
		topDraw = 0;
		rotation = 0;
		mode = 3;
		ActivateButton();
	}
	
	public void ActivateButton()
	{
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				waitingForButton = true;
			}
		}, 750);
	}

	private void caclulateSelected() {
		int selectionAreaSize = 360 / wheelOptions.size();
		for (int i = 0; i < wheelOptions.size(); i++) {
			int min = i * selectionAreaSize;
			int max = (i + 1) * selectionAreaSize;
//			int degrees = min + (selectionAreaSize/2);
			System.out.println(i + ": " + min + ", " + max);
			if (min <= 0 && max > 0) {
				selectedOption = i;
				System.out.println("Initial selected option: " + i);
				break;
			}
		}
	}
}