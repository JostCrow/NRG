package asa.client;

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
import org.newdawn.slick.font.effects.Effect;
import org.newdawn.slick.font.effects.ShadowEffect;
import java.awt.Color;
import java.awt.Component;
import java.awt.Label;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Vector;
import java.util.logging.Level;
import javax.media.Buffer;
import javax.media.CannotRealizeException;
import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.Manager;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.media.control.FrameGrabbingControl;
import javax.media.format.VideoFormat;
import javax.media.util.BufferToImage;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.BufferedImageUtil;


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
	String underSpinner;
	String spinnerSouth = "";
	Image tandwiel1;
	Image tandwiel2;
	Image tandwiel3;
	Image background;
	Image spinner;
	Image background_spinner;
	Image spinneroverlay;
	Image icon_background;
	Image background_highscore;
	Image background_item_highscore;
	Image overlay_selected;
	Image tandwiel_vertical;
	Dimension center;
	int targetrotation = 0;
	int selectionDegrees = 180;
	int selectionScaleDistance = 30;
	int selectedOption = 0;
	float selectedScale = 1.5f;
	int oldSelectedOption = 0;
	int tandwielOffset = 30;
	float rotation = 0;
	float rotationDelta = 0;
	double rotationEase = 5.0;
	StateBasedGame basedGame;
	Component lbl = new Label("hallo");
	
	int appResWidth = AsaGame.SOURCE_RESOLUTION.width;
	int appResHeight = AsaGame.SOURCE_RESOLUTION.height;
	List<Highscore> highscores;
	boolean longlist;
	int topDraw;
	int scrollDelta;
	int scoreHeight = (appResHeight/8);
	double listSpeedfactor = 3.20;
	int highscoreBackgroundHeight = 0;
	
	CaptureDeviceInfo deviceInfo;
	Player player;
	Component video;
	Graphics liveVideo;
	FrameGrabbingControl frameGrabber;
    Buffer buffer;
	java.awt.Image awtFrame;

	ShadowEffect effect;

	public HighscoreState(int stateID, ServerAdapter server, GameData gameData) {
		super(stateID);
		this.server = server;
		this.gameData = gameData;
//		Vector devices = CaptureDeviceManager.getDeviceList(new VideoFormat(null));
//		CaptureDeviceInfo captureDevice = (CaptureDeviceInfo) devices.get(0);
//		deviceInfo = CaptureDeviceManager.getDevice(captureDevice.getName());
	}

	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
		
		wheelOptions.add(new WheelOptionYesNo("Ja", "icon_beamer.png", true));
		wheelOptions.add(new WheelOptionYesNo("Nee", "icon_automaat.png", false));	
		
		center = new Dimension(AsaGame.SOURCE_RESOLUTION.width / 2 - 100, AsaGame.SOURCE_RESOLUTION.height / 2);
		selectionDegrees = 360 / wheelOptions.size();
		tandwiel1 = new Image(Resource.getPath(Resource.TANDWIEL5));
		tandwiel2 = new Image(Resource.getPath(Resource.TANDWIEL6));
		tandwiel3 = new Image(Resource.getPath(Resource.TANDWIEL7));
		spinner = new Image(Resource.getPath(Resource.SPINNER));
		spinneroverlay = new Image(Resource.getPath(Resource.SPINNER_OVERLAY));
		background_spinner = new Image(Resource.getPath(Resource.BACKGROUND_SPINNER));
		background = new Image(Resource.getPath(Resource.GAME_BACKGROUND));
		icon_background = new Image(Resource.getPath(Resource.ICON_BACKGROUND_EASY));
		
		background_highscore = new Image(Resource.getPath(Resource.background_highscore));
		background_item_highscore = new Image(Resource.getPath(Resource.background_item_highscore));
		overlay_selected = new Image(Resource.getPath(Resource.overlay_selected));
		tandwiel_vertical = new Image(Resource.getPath(Resource.tandwiel_vertical));
		
		font = Resource.getFont(Resource.FONT_SANCHEZ, 30, Color.BLACK);
		effect = new ShadowEffect();
		effect.setColor(Color.yellow);
		effect.setXDistance(5);
		effect.setYDistance(5);
	}

	@Override
	public void enter(GameContainer gameContainer, final StateBasedGame stateBasedGame) {
		
		mode = 1;		
		basedGame = stateBasedGame;
		rotationDelta = (rotation*-1);
		this.playerScore = gameData.getPlayerScore();
		this.deviceScore = gameData.getDeviceScore();
		this.device = server.getDeviceById(gameData.getDeviceId());
		
		spinnerSouth = decimalFormat.format(playerScore) + " kWh, ";
		underSpinner = "Foto maken bij behaalde score?";
		try {
			System.out.println("get Player: " + deviceInfo.getLocator().toString());
			player = Manager.createRealizedPlayer(deviceInfo.getLocator());
			System.out.println("start Player");
			player.start();
			System.out.println("get Video");
			video = player.getVisualComponent();
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					System.out.println("Getting Framegrabber");
					//frameGrabber = (FrameGrabbingControl)player.getControl("javax.media.control.FrameGrabbingControl");
				}
			}, 2500);					
		} catch(Exception e)
		{
			System.out.println("Failed to get webcam feed: " + e.getMessage());			 
		}
//		} catch (IOException ex) {
//			java.util.logging.Logger.getLogger(HighscoreState.class.getName()).log(Level.SEVERE, null, ex);
//		} catch (NoPlayerException ex) {
//			java.util.logging.Logger.getLogger(HighscoreState.class.getName()).log(Level.SEVERE, null, ex);
//		} catch (CannotRealizeException ex) {
//			java.util.logging.Logger.getLogger(HighscoreState.class.getName()).log(Level.SEVERE, null, ex);
//		}
		
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
		mode = 1;
		arduino.removeAllListeners();
		if(player != null){
			player.close();
			player.deallocate();
		}
	}

	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		background.draw(0, 0);
		tandwiel1.draw(-tandwiel1.getWidth() / 2, AsaGame.SOURCE_RESOLUTION.height / 2 - tandwiel1.getHeight() / 2);
		tandwiel2.draw(tandwiel1.getWidth() / 2 - tandwielOffset - 40, AsaGame.SOURCE_RESOLUTION.height / 2 - tandwiel2.getHeight());
		
		if (frameGrabber!=null)
		{
//			System.out.println("GrabFrame");
			buffer = frameGrabber.grabFrame();
			awtFrame = (java.awt.Image) new BufferToImage((VideoFormat)buffer.getFormat()).createImage(buffer);
			BufferedImage awtBuffImg = new BufferedImage(awtFrame.getWidth(null), awtFrame.getHeight(null), BufferedImage.TYPE_INT_RGB);
			
			Texture texture = null;
			try{
				texture = BufferedImageUtil.getTexture("", awtBuffImg);
			} catch (Exception e){
				logger.debug(e);
			}
			Image slickImage = new Image(texture.getImageWidth(), texture.getImageHeight() );
			slickImage.setTexture(texture) ;
			slickImage.draw(center.getHeight(), center.getWidth());
					
//			videoFrame = new BufferToImage((VideoFormat)buffer.getFormat()).createImage(buffer));
		}
		graphics.setFont(font);
		

		if (mode == 1) {
			spinner.draw(center.getWidth() - spinner.getWidth() / 2, center.getHeight() - spinner.getHeight() / 2);
			spinneroverlay.draw(center.getWidth() - spinner.getWidth() / 2, center.getHeight() - spinner.getHeight() / 2);
			background_spinner.draw(center.getWidth() - background_spinner.getWidth() / 2, center.getHeight() - background_spinner.getHeight() / 2);
			for (int i = 0; i < wheelOptions.size(); i++) {
				float offsetDegree = 360 / wheelOptions.size();
				float degrees = (270 + ((rotation+rotationDelta) % 360 + offsetDegree * i) % 360) % 360;
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
					graphics.drawString(option.getDescription(), x+icon_background.getWidth()/2 -15*(option.getDescription().length()/2), y+icon_background.getHeight()/2);
//					effect.draw(null, null, font, null);
				} else{
					x = x - (float) (optionIcon.getWidth() * 1 / 2);
					y = y - (float) (optionIcon.getHeight() * 1 / 2);
					icon_background.draw(x, y);
					graphics.drawString(option.getDescription(), x+icon_background.getWidth()/3 -15*(option.getDescription().length()/2), y+icon_background.getHeight()/3);
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
			spinner.draw(center.getWidth() - spinner.getWidth() / 2, center.getHeight() - spinner.getHeight() / 2);
			spinneroverlay.draw(center.getWidth() - spinner.getWidth() / 2, center.getHeight() - spinner.getHeight() / 2);
			background_spinner.draw(center.getWidth() - background_spinner.getWidth() / 2, center.getHeight() - background_spinner.getHeight() / 2);
			graphics.drawString(spinnerSouth, (center.getWidth() - 13), center.getHeight() + 160);				
		}
		else if (mode == 3)
		{
			tandwiel3.draw(AsaGame.SOURCE_RESOLUTION.width - background_highscore.getWidth() - tandwiel2.getWidth()- tandwiel_vertical.getWidth()/2-42, AsaGame.SOURCE_RESOLUTION.height - tandwiel2.getHeight());
			spinner.draw(center.getWidth() - spinner.getWidth() / 2, center.getHeight() - spinner.getHeight() / 2);
			spinneroverlay.draw(center.getWidth() - spinner.getWidth() / 2, center.getHeight() - spinner.getHeight() / 2);
			background_spinner.draw(center.getWidth() - background_spinner.getWidth() / 2, center.getHeight() - background_spinner.getHeight() / 2);
			graphics.drawString(spinnerSouth, (center.getWidth() - 13), center.getHeight() + 160);
			//underSpinner = highscores.size() + ", " + topDraw + ", " + rotation + ", " + scoreHeight + ", " + scrollDelta;
			//graphics.drawString(underSpinner, center.getWidth(), center.getHeight() + 400);
			background_highscore.draw(appResWidth-background_highscore.getWidth(), highscoreBackgroundHeight);
			tandwiel_vertical.draw(appResWidth-background_highscore.getWidth()-tandwiel_vertical.getWidth(), highscoreBackgroundHeight);
			background_highscore.draw(appResWidth-background_highscore.getWidth(), highscoreBackgroundHeight+background_highscore.getHeight());
			tandwiel_vertical.draw(appResWidth-background_highscore.getWidth()-tandwiel_vertical.getWidth(), highscoreBackgroundHeight+tandwiel_vertical.getHeight());
			background_highscore.draw(appResWidth-background_highscore.getWidth(), highscoreBackgroundHeight-background_highscore.getHeight());
			tandwiel_vertical.draw(appResWidth-background_highscore.getWidth()-tandwiel_vertical.getWidth(), highscoreBackgroundHeight-tandwiel_vertical.getHeight());
			
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
				background_item_highscore.draw(topLeftX, topLeftY);
//				graphics.drawLine(topLeftX, topLeftY, appResWidth, topLeftY);
				graphics.setLineWidth(3.0f);
				graphics.drawString(rank + ": " + decimalFormat.format(score.getScore()), topLeftX, topLeftY + (scoreHeight/2));
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
			float listRotation = ((-rotation+rotationDelta)* (float)listSpeedfactor);
			int possibleTopDraw = (int) ((listRotation*-1)/scoreHeight);
			if (possibleTopDraw>=0)
			{
				scrollDelta = (int)(listRotation % scoreHeight);
				topDraw = possibleTopDraw;
			}
			else{
				scrollDelta = (int)(((possibleTopDraw*-1)*scoreHeight) + (listRotation%scoreHeight));
			}
			tandwiel3.setRotation((float) ((float) listRotation * 0.31255 - 7));
			highscoreBackgroundHeight = (int) ((listRotation)%background_highscore.getHeight());
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
		rotationDelta = rotation*-1;
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