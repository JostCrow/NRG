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
import org.newdawn.slick.util.BufferedImageUtil;

public class PhotoState extends ArduinoGameState implements ImageObserver{
	private StateBasedGame stateBasedGame;
	private ServerAdapter server;
	private GameData gameData;
	private Dimension center;
	private Logger logger = Logger.getLogger(this.getClass());
	private List<WheelOptionYesNo> wheelOptions = new ArrayList<WheelOptionYesNo>();
	private UnicodeFont fontBlack;
	private Timer liveFeed;

	private boolean waitingForButton;
	private boolean webcamAvailable = false;
	private boolean updateCamera = false;
	private boolean makePhoto = false;
	private boolean drawCountdown = false;

	private Image tandwiel1;
	private Image tandwiel2;
	private Image background;
	private Image spinner;
	private Image background_spinner;
	private Image background_spinner_half;
	private Image spinneroverlay;
	private Image webcamFeed;
	private Image countdown;
	private Image selectImage;
	private Image choise;

	private java.awt.Image awtFrame;
	private BufferedImage baseImage;
	private Animation lens;
	private Texture texture = null;

	private int mode = 1;
	private int targetrotation = 0;
	private int selectedOption = 0;
	private int tandwielOffset = 30;
	private int lastHighscoreId;

	private float rotation = 0;
	private float rotationDelta = 0;

	private double rotationEase = 5.0;

	private CaptureDeviceInfo webcam;
	private Player webcamPlayer;
	private FrameGrabbingControl frameGrabber;
	private Buffer buffer;

	public PhotoState(int stateID, ServerAdapter server, GameData gameData) {
		super(stateID);
		this.server = server;
		this.gameData = gameData;

		wheelOptions.add(new WheelOptionYesNo("Ja", Resource.getPath(Resource.ICON_YES), true));
		wheelOptions.add(new WheelOptionYesNo("Nee", Resource.getPath(Resource.ICON_NO), false));

		initWebcam();
	}

	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
		this.stateBasedGame = stateBasedGame;

		center = new Dimension(AsaGame.SOURCE_RESOLUTION.width / 2 - 100, AsaGame.SOURCE_RESOLUTION.height / 2);

		resetGame();

		tandwiel1 = new Image(Resource.getPath(Resource.TANDWIEL5));
		tandwiel2 = new Image(Resource.getPath(Resource.TANDWIEL6));
		spinner = new Image(Resource.getPath(Resource.SPINNER));
		spinneroverlay = new Image(Resource.getPath(Resource.SPINNER_OVERLAY));
		background_spinner = new Image(Resource.getPath(Resource.BACKGROUND_SPINNER));
		background_spinner_half = new Image(Resource.getPath(Resource.BACKGROUND_SPINNER_HALF));
		background_spinner_half.setAlpha(0.7f);
		background = new Image(Resource.getPath(Resource.GAME_BACKGROUND));
		selectImage = new Image(Resource.getPath(Resource.SAVE_SCORE));
		choise = new Image(Resource.getPath(Resource.MAKE_YOUR_CHOISE));

		fontBlack = Resource.getFont(Resource.FONT_SANCHEZ, 30, Color.BLACK);

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
		checkWebcam();
		calculateSelected();
		addListeners(stateBasedGame);
		ActivateButton();
		liveFeed = new Timer();
		liveFeed.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				updateCamera = true;
			}
		}, 0, 250);
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException {
		resetGame();
	}

	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		background.draw(0, 0);
		tandwiel1.draw(-tandwiel1.getWidth() / 2, AsaGame.SOURCE_RESOLUTION.height / 2 - tandwiel1.getHeight() / 2);
		tandwiel2.draw(tandwiel1.getWidth() / 2 - tandwielOffset - 40, AsaGame.SOURCE_RESOLUTION.height / 2 - tandwiel2.getHeight());

		graphics.setFont(fontBlack);

		if (baseImage != null) {
			webcamFeed.getSubImage(80, 0, 480, 480).draw(center.getWidth()-((500)/2), center.getHeight()-(500/2), 500, 500);
			background_spinner_half.draw(center.getWidth() - background_spinner.getWidth() / 2, center.getHeight() + 45);
		}
		else {
			background_spinner.draw(center.getWidth() - background_spinner.getWidth() / 2, center.getHeight() - background_spinner.getHeight() / 2);
		}

		if (mode == 1) {
			spinner.draw(center.getWidth() - spinner.getWidth() / 2, center.getHeight() - spinner.getHeight() / 2);
			spinneroverlay.draw(center.getWidth() - spinner.getWidth() / 2, center.getHeight() - spinner.getHeight() / 2);
			selectImage.draw(center.getWidth()/2-20, 60);
			choise.draw(choise.getWidth()*0.15f, center.getHeight()*2 - choise.getHeight()*1.6f);
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
					selectedOption = i;
				}
			}
		} else {
			lens.draw(center.getWidth() - (550 / 2), center.getHeight() - (550 / 2));
			spinner.draw(center.getWidth() - spinner.getWidth() / 2, center.getHeight() - spinner.getHeight() / 2);
			spinneroverlay.draw(center.getWidth() - spinner.getWidth() / 2, center.getHeight() - spinner.getHeight() / 2);
			if (drawCountdown) {
				countdown.draw(center.getWidth()-75, center.getHeight() + 75, 150, 150);
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

		if (frameGrabber != null && updateCamera) {
			updateCamera = false;
			buffer = frameGrabber.grabFrame();
			awtFrame = new BufferToImage((VideoFormat) buffer.getFormat()).createImage(buffer);
			BufferedImage bufferedImage = new BufferedImage(awtFrame.getWidth(null), awtFrame.getHeight(null), BufferedImage.TYPE_INT_RGB);
			bufferedImage.createGraphics().drawImage(awtFrame, 0, 0, this);
			baseImage = bufferedImage;
			try{
				texture = BufferedImageUtil.getTexture("", baseImage);
				webcamFeed.setTexture(texture);
			} catch (IOException e){
				logger.error(e);
			}
		}
	}

	private void MakePhoto() {
		makePhoto = true;
		mode = 2;
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				drawCountdown = true;
				setCountdownImage(3);
			}
		}, 1000);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				setCountdownImage(2);
			}
		}, 2000);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				setCountdownImage(1);
			}
		}, 3000);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				liveFeed.cancel();
				updateCamera = true;
				drawCountdown = false;
				lens.restart();
			}
		}, 4000);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				SaveImage();
				stateBasedGame.enterState(AsaGame.HIGHSCORESTATE, AsaGame.FADEOUT, AsaGame.FADEIN);
			}
		}, 7000);
	}

	public void SaveImage() {
		if (makePhoto) {
			lastHighscoreId = server.addHighscore(gameData.getPlayerScore(), "yes");
			try {
				File f = new File(lastHighscoreId + ".png");
				ImageIO.write(baseImage, "png", f);
			} catch (IOException ex) {
				System.out.println("fail to save image:" + ex.getMessage());
			}
		}
		else{
			liveFeed.cancel();
			lastHighscoreId = server.addHighscore(gameData.getPlayerScore(), "no");
		}

		gameData.setLastHighscoreId(lastHighscoreId);
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

	private void calculateSelected() {
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
					targetrotation += speed * 1.145;
				} else {
					targetrotation -= speed * 1.145;
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
							SaveImage();
							stateBasedGame.enterState(AsaGame.HIGHSCORESTATE, AsaGame.FADEOUT, AsaGame.FADEIN);
						}
					}
				}
			}
		});
	}

	private void resetGame() {
		mode = 1;
		makePhoto = false;
		arduino.removeAllListeners();
		baseImage = null;
		texture = null;
		drawCountdown = false;
		liveFeed = null;
		webcamFeed = new Image(new EmptyImageData(1, 1));
		countdown = new Image(new EmptyImageData(1,1));
	}

	private void initWebcam() {
		Vector videoDevices = CaptureDeviceManager.getDeviceList(new VideoFormat(null));
		if (videoDevices.size()>0){
			webcam = (CaptureDeviceInfo) videoDevices.get(0);
			try {
				webcamPlayer = Manager.createRealizedPlayer(webcam.getLocator());
				webcamPlayer.start();

				webcamAvailable = true;

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

	private void checkWebcam() {
		Vector videoDevices = CaptureDeviceManager.getDeviceList(new VideoFormat(null));
		if (videoDevices.size()>0){
			if (webcamAvailable == false){
				initWebcam();
			}
		} else {
			webcamAvailable = false;
		}

		if (!webcamAvailable){
			SaveImage();
			stateBasedGame.enterState(AsaGame.HIGHSCORESTATE, AsaGame.FADEOUT, AsaGame.FADEIN);
		}
	}

	private void setCountdownImage(int count)
	{
		try {
			countdown = new Image(Resource.getPath(count + ".png"));
		} catch (SlickException ex) {
			logger.error("Could not get countdownImage " + count + ".png: " + ex.getMessage());
		}
	}
}