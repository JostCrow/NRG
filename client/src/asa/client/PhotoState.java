package asa.client;

import asa.client.DTO.GameData;
import asa.client.resources.Resource;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.media.Buffer;
import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.Manager;
import javax.media.Player;
import javax.media.control.FrameGrabbingControl;
import javax.media.format.VideoFormat;
import javax.media.util.BufferToImage;
import org.apache.log4j.Logger;
import org.lwjgl.util.Dimension;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.opengl.EmptyImageData;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.state.transition.Transition;
import org.newdawn.slick.util.BufferedImageUtil;
import service.Highscore;

public class PhotoState extends ArduinoGameState implements ImageObserver{
	StateBasedGame stateBasedGame;
	List<Highscore> highscores;
	ServerAdapter server;
	GameData gameData;
	Dimension center;
	Logger logger = Logger.getLogger(this.getClass());
	List<WheelOptionYesNo> wheelOptions = new ArrayList<WheelOptionYesNo>();
	UnicodeFont fontBlack;
	UnicodeFont fontWhite;

	boolean waitingForButton;
	boolean webcamAvailable = false;

	String spinnerSouth = "";
	String underSpinner = "";

	Image tandwiel1;
	Image tandwiel2;
	Image tandwiel3;
	Image background;
	Image spinner;
	Image background_spinner;
	Image spinneroverlay;
	Image webcamFeed;

	java.awt.Image awtFrame;
	BufferedImage baseImage;
	Animation lens;
	Texture texture = null;

	/**
	 * mode 1: Able to choose yes or no mode 2: Automaticaly making picture (can
	 * be skipped) mode 3: Able to scroll through highscorelist
	 */
	int mode = 1;
	int targetrotation = 0;
	int selectionDegrees = 180;
	int selectionScaleDistance = 30;
	int selectedOption = 0;
	int oldSelectedOption = 0;
	int tandwielOffset = 30;
	int appResWidth = AsaGame.SOURCE_RESOLUTION.width;
	int appResHeight = AsaGame.SOURCE_RESOLUTION.height;
	int lastHighscoreId;
	int lastHighscoreRank;
	int topDraw;
	int selected;
	int scrollDelta;
	int lastHighscoreDelta;
	int scoreHeight = (appResHeight / 10);
	int highscoreBackgroundHeight = 0;


	float selectedScale = 1.5f;
	float rotation = 0;
	float rotationDelta = 0;

	double rotationEase = 5.0;
	double listSpeedFactor = 3.20;

	CaptureDeviceInfo webcam;
	Player webcamPlayer;
	FrameGrabbingControl frameGrabber;
	Buffer buffer;

	public PhotoState(int stateID, ServerAdapter server, GameData gameData) {
		super(stateID);
		this.server = server;
		this.gameData = gameData;
		wheelOptions.add(new WheelOptionYesNo("Ja", Resource.getPath(Resource.ICON_YES), true));
		wheelOptions.add(new WheelOptionYesNo("Nee", Resource.getPath(Resource.ICON_NO), false));

		Vector videoDevices = CaptureDeviceManager.getDeviceList(new VideoFormat(null));
		if (videoDevices.size()>0)
		{
			webcam = (CaptureDeviceInfo) videoDevices.get(0);
			webcamAvailable = true;
		}
	}

	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
		this.stateBasedGame = stateBasedGame;

		center = new Dimension(AsaGame.SOURCE_RESOLUTION.width / 2 - 100, AsaGame.SOURCE_RESOLUTION.height / 2);

		resetGame();

		selectionDegrees = 360 / wheelOptions.size();

		tandwiel1 = new Image(Resource.getPath(Resource.TANDWIEL5));
		tandwiel2 = new Image(Resource.getPath(Resource.TANDWIEL6));
		tandwiel3 = new Image(Resource.getPath(Resource.TANDWIEL7));
		spinner = new Image(Resource.getPath(Resource.SPINNER));
		spinneroverlay = new Image(Resource.getPath(Resource.SPINNER_OVERLAY));
		background_spinner = new Image(Resource.getPath(Resource.BACKGROUND_SPINNER));
		background = new Image(Resource.getPath(Resource.GAME_BACKGROUND));

		webcamFeed = new Image(new EmptyImageData(640, 480));

		fontBlack = Resource.getFont(Resource.FONT_SANCHEZ, 30, Color.BLACK);
		fontWhite = Resource.getFont(Resource.FONT_SANCHEZ, 30, Color.WHITE);

		lens = new Animation();
		lens.setLooping(false);
		for (int i = 0; i < 33; i++) {
			if ((i + "").length() == 1) {
				lens.addFrame(new Image(Resource.getPath("LENS/lens1_0000" + i + ".png")), 550 / 33);
			} else if ((i + "").length() == 2) {
				lens.addFrame(new Image(Resource.getPath("LENS/lens1_000" + i + ".png")), 550 / 33);
			}
		}
		lens.stop();
	}

	@Override
	public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) {
		initWebcam();
		caclulateSelected();
		addListeners(stateBasedGame);
		ActivateButton();
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException {
		resetGame();
		if (webcamPlayer != null) {
			webcamPlayer.close();
			webcamPlayer.deallocate();
		}
	}

	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		background.draw(0, 0);
		tandwiel1.draw(-tandwiel1.getWidth() / 2, AsaGame.SOURCE_RESOLUTION.height / 2 - tandwiel1.getHeight() / 2);
		tandwiel2.draw(tandwiel1.getWidth() / 2 - tandwielOffset - 40, AsaGame.SOURCE_RESOLUTION.height / 2 - tandwiel2.getHeight());

//		if (frameGrabber!=null)
//		{
//			// trying to get live feed from camera here. Buffering images in render will lead to OutOfMemoryException so far.
////		System.out.println("GrabFrame");
//			buffer = frameGrabber.grabFrame();
//			awtFrame = new BufferToImage((VideoFormat)buffer.getFormat()).createImage(buffer);
//			// tot hier moet werken
//			BufferedImage awtBuffImg = new BufferedImage(awtFrame.getWidth(null), awtFrame.getHeight(null), BufferedImage.TYPE_INT_RGB);
//			Texture texture = null;
//			try{
//				texture = BufferedImageUtil.getTexture("", awtBuffImg);
//			} catch (Exception e){
//				logger.debug(e);
//			}
//			Image slickImage = new Image(texture.getImageWidth(), texture.getImageHeight() );
//			slickImage.setTexture(texture) ;
//			slickImage.draw(center.getHeight(), center.getWidth());
////		videoFrame = new BufferToImage((VideoFormat)buffer.getFormat()).createImage(buffer));
//		}

		graphics.setFont(fontBlack);

		if (mode == 1) {
			background_spinner.draw(center.getWidth() - background_spinner.getWidth() / 2, center.getHeight() - background_spinner.getHeight() / 2);
			spinner.draw(center.getWidth() - spinner.getWidth() / 2, center.getHeight() - spinner.getHeight() / 2);
			spinneroverlay.draw(center.getWidth() - spinner.getWidth() / 2, center.getHeight() - spinner.getHeight() / 2);
			for (int i = 0; i < wheelOptions.size(); i++) {
				float offsetDegree = 360 / wheelOptions.size();
				float degrees = (270 + ((rotation + rotationDelta) % 360 + offsetDegree * i) % 360) % 360;
				if (degrees < 0) {
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
					option.getIcon().draw(x, y, (float) 1.3);
				} else {
					x = x - (float) (optionIcon.getWidth() * 1 / 2);
					y = y - (float) (optionIcon.getHeight() * 1 / 2);
					option.getIcon().draw(x, y);
				}

				if (degrees >= 270 - (offsetDegree / 2) && degrees < biggerThanDegrees) {
					oldSelectedOption = selectedOption;
					selectedOption = i;
				}

				int scoreLength = spinnerSouth.length();
				int questionLength = underSpinner.length();
				graphics.drawString(spinnerSouth, (center.getWidth() - ((scoreLength) * 13)), center.getHeight() + 160);
				graphics.drawString(underSpinner, (center.getWidth() - ((questionLength) * 13)), center.getHeight() + 400);
			}
		} else {
			background_spinner.draw(center.getWidth() - background_spinner.getWidth() / 2, center.getHeight() - background_spinner.getHeight() / 2);
			if (baseImage != null) {

				try{
					if(texture == null){
						texture = BufferedImageUtil.getTexture("", baseImage);
						webcamFeed.setTexture(texture);
					}
				} catch (IOException e){
					logger.error(e);
				}
				webcamFeed.getSubImage(50, 0, 540, 480).draw(center.getWidth()-((640-50)/2)+20, center.getHeight()-(480/2)-20);
			}
			lens.draw(center.getWidth() - (550 / 2), center.getHeight() - (550 / 2));
			spinner.draw(center.getWidth() - spinner.getWidth() / 2, center.getHeight() - spinner.getHeight() / 2);
			spinneroverlay.draw(center.getWidth() - spinner.getWidth() / 2, center.getHeight() - spinner.getHeight() / 2);
			graphics.drawString(spinnerSouth, (center.getWidth() - 13), center.getHeight() + 160);
		}
	}

	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
		super.update(gameContainer, stateBasedGame, delta);
		rotation += (targetrotation - rotation) / rotationEase;
		tandwiel1.setRotation(rotation);
		tandwiel2.setRotation((float) ((float) -(rotation * 1.818181818181818) + 16.36363636363636));
		spinner.setRotation(rotation);
	}

	private void MakePhoto() {
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
				spinnerSouth = "";
				lens.restart();
				CaptureImage();
			}
		}, 4000);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				ActivateHighscoreList();
			}
		}, 9000);
	}

	public void ActivateHighscoreList() {
		if (baseImage != null) {
			lastHighscoreId = server.addHighscore(gameData.getPlayerScore(), "yes");
			try {
				File f = new File(lastHighscoreId + ".png");
				ImageIO.write(baseImage, "png", f);
			} catch (IOException ex) {
				System.out.println("fail to save image:" + ex.getMessage());
			}
		}
		else{
			lastHighscoreId = server.addHighscore(gameData.getPlayerScore(), "no");
		}
		//Make availeble for highscore
		highscores = server.getAllHighscores();
		lastHighscoreRank = 0;

		for (Highscore hs : highscores) {
			lastHighscoreRank++;
			if (hs.getId() == lastHighscoreId) {
				System.out.println("lastHighscoreRank:" + lastHighscoreRank);
				break;
			}
		}
		topDraw = 0;
		lastHighscoreDelta = (scoreHeight * lastHighscoreRank) - (scoreHeight / 2) - (appResHeight / 2);
		rotationDelta = (float) (rotation * -1);
		Transition fadeIn = new FadeInTransition();
		Transition fadeOut = new FadeOutTransition();
		stateBasedGame.enterState(AsaGame.HIGHSCORESTATE, fadeOut, fadeIn);
	}

	public void CaptureImage() {
		if (frameGrabber != null) {

			buffer = frameGrabber.grabFrame();
			awtFrame = new BufferToImage((VideoFormat) buffer.getFormat()).createImage(buffer);
			BufferedImage bufferedImage = new BufferedImage(awtFrame.getWidth(null), awtFrame.getHeight(null), BufferedImage.TYPE_INT_RGB);
			bufferedImage.createGraphics().drawImage(awtFrame, 0, 0, this);
			baseImage = bufferedImage;
		}
	}

	public void ActivateButton() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				waitingForButton = true;
			}
		}, 500);
	}

	private void caclulateSelected() {
		int selectionAreaSize = 360 / wheelOptions.size();
		for (int i = 0; i < wheelOptions.size(); i++) {
			int min = i * selectionAreaSize;
			int max = (i + 1) * selectionAreaSize;
			System.out.println(i + ": " + min + ", " + max);
			if (min <= 0 && max > 0) {
				selectedOption = i;
				System.out.println("Initial selected option: " + i);
				break;
			}
		}
	}

	@Override
	public boolean imageUpdate(java.awt.Image img, int infoflags, int x, int y, int width, int height) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	private void addListeners(final StateBasedGame stateBasedGame) {
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
					if (mode == 1) {
						WheelOptionYesNo selected = wheelOptions.get(selectedOption);
						if (selected.getValue()) {
							MakePhoto();
						} else {
							Transition fadeIn = new FadeInTransition();
							Transition fadeOut = new FadeOutTransition();
							stateBasedGame.enterState(AsaGame.HIGHSCORESTATE, fadeOut, fadeIn);
						}
					}
				}
			}
		});
	}

	private void resetGame() {
		arduino.removeAllListeners();
		spinnerSouth = "";
		baseImage = null;
		texture = null;
	}

	private void initWebcam() {
		if (webcamAvailable) {
			try {
				webcamPlayer = Manager.createRealizedPlayer(webcam.getLocator());
				webcamPlayer.start();

				Timer timer = new Timer();
				timer.schedule(new TimerTask() {
					@Override
					public void run() {
						frameGrabber = (FrameGrabbingControl) webcamPlayer.getControl("javax.media.control.FrameGrabbingControl");
					}
				}, 2500);
			} catch (Exception e) {
				logger.error("Failed to get webcam feed: " + e.getMessage());
			}
		} else {
			logger.error("No webcam available");
		}
	}
}