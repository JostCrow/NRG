package asa.client;

import asa.client.DTO.GameData;
import asa.client.resources.Resource;
import java.awt.Color;
import java.io.File;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.media.Buffer;
import javax.media.CaptureDeviceInfo;
import javax.media.Player;
import javax.media.control.FrameGrabbingControl;
import org.apache.log4j.Logger;
import org.lwjgl.util.Dimension;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.opengl.EmptyImageData;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.state.transition.Transition;
import service.Highscore;

public class HighscoreState extends ArduinoGameState {
	List<Highscore> highscores;
	ServerAdapter server;
	GameData gameData;
	Dimension center;
	Logger logger = Logger.getLogger(this.getClass());
	UnicodeFont fontBlack;
	UnicodeFont fontWhite;

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
	Image background_highscore;
	Image background_item_highscore;
	Image background_item_highscore_own;
	Image overlay_selected;
	Image tandwiel_vertical;
	Image highscore;
	Image centerImage;

	int targetrotation = 0;
	int selectionDegrees = 180;
	int selectionScaleDistance = 30;
	int selectedOption = 0;
	int oldSelectedOption = 0;
	int tandwielOffset = 30;
	int appResWidth = AsaGame.SOURCE_RESOLUTION.width;
	int appResHeight = AsaGame.SOURCE_RESOLUTION.height;
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

	public HighscoreState(int stateID, ServerAdapter server, GameData gameData) {
		super(stateID);
		this.server = server;
		this.gameData = gameData;
	}

	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
		center = new Dimension(AsaGame.SOURCE_RESOLUTION.width / 2 - 100, AsaGame.SOURCE_RESOLUTION.height / 2);

		resetGame();

		tandwiel1 = new Image(Resource.getPath(Resource.TANDWIEL5));
		tandwiel2 = new Image(Resource.getPath(Resource.TANDWIEL6));
		tandwiel3 = new Image(Resource.getPath(Resource.TANDWIEL7));
		spinner = new Image(Resource.getPath(Resource.SPINNER));
		spinneroverlay = new Image(Resource.getPath(Resource.SPINNER_OVERLAY));
		background_spinner = new Image(Resource.getPath(Resource.BACKGROUND_SPINNER));
		background = new Image(Resource.getPath(Resource.GAME_BACKGROUND));

		background_highscore = new Image(Resource.getPath(Resource.background_highscore));
		background_item_highscore = new Image(Resource.getPath(Resource.background_item_highscore));
		background_item_highscore_own = new Image(Resource.getPath(Resource.BACKGROUND_ITEM_HIGHSCORE_OWN));
		overlay_selected = new Image(Resource.getPath(Resource.overlay_selected));
		tandwiel_vertical = new Image(Resource.getPath(Resource.tandwiel_vertical));

		centerImage = new Image(new EmptyImageData(97, 97));
		highscore = new Image(new EmptyImageData(97, 97));

		fontBlack = Resource.getFont(Resource.FONT_SANCHEZ, 30, Color.BLACK);
		fontWhite = Resource.getFont(Resource.FONT_SANCHEZ, 30, Color.WHITE);
	}

	@Override
	public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) {
		addListeners(stateBasedGame);
		ActivateButton();
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

		tandwiel3.draw(AsaGame.SOURCE_RESOLUTION.width - background_highscore.getWidth() - tandwiel2.getWidth() - tandwiel_vertical.getWidth() / 2 - 38, AsaGame.SOURCE_RESOLUTION.height - tandwiel2.getHeight());
		background_highscore.draw(appResWidth - background_highscore.getWidth(), highscoreBackgroundHeight);
		tandwiel_vertical.draw(appResWidth - background_highscore.getWidth() - tandwiel_vertical.getWidth(), highscoreBackgroundHeight);
		background_highscore.draw(appResWidth - background_highscore.getWidth(), highscoreBackgroundHeight + background_highscore.getHeight());
		tandwiel_vertical.draw(appResWidth - background_highscore.getWidth() - tandwiel_vertical.getWidth(), highscoreBackgroundHeight + tandwiel_vertical.getHeight());
		background_highscore.draw(appResWidth - background_highscore.getWidth(), highscoreBackgroundHeight - background_highscore.getHeight());
		tandwiel_vertical.draw(appResWidth - background_highscore.getWidth() - tandwiel_vertical.getWidth(), highscoreBackgroundHeight - tandwiel_vertical.getHeight());
		graphics.setFont(fontWhite);
		background_spinner.draw(center.getWidth() - background_spinner.getWidth() / 2, center.getHeight() - background_spinner.getHeight() / 2);

		for (int i = 0; i < 11; i++) {
			if (topDraw + i >= highscores.size()) {
				break;
			}

			int topLeftX = (appResWidth - appResWidth / 4 - appResWidth / 200);
			int topLeftY = (scoreHeight * i) + scrollDelta;
			int rank = topDraw + i + 1;

			if (rank == lastHighscoreRank) {
				background_item_highscore_own.draw(topLeftX, topLeftY);
			} else {
				background_item_highscore.draw(topLeftX, topLeftY);
			}

			File f = new File(highscores.get(topDraw + i).getId() + ".png");
			if(f.exists()){
				highscore = new Image(highscores.get(topDraw + i).getId() + ".png");
			} else {
				highscore = new Image(Resource.getPath("avatar.png"));
			}
			highscore.getSubImage(((highscore.getWidth()-highscore.getHeight())/2), 0, highscore.getHeight(), highscore.getHeight()).draw(topLeftX + 12, topLeftY + 5, 97, 97);

			graphics.drawString(rank + "", topLeftX + 12, topLeftY + 68);
			String pnumber = specialFormat.format(highscores.get(topDraw + i).getScore());
			pnumber = pnumber.replace(",", "");
			for (int j = 0; j < pnumber.length(); j++) {
				String singleNumber = pnumber.substring(j, j + 1);
				graphics.drawString(singleNumber, topLeftX + 96 + (44 * (j + 1)), topLeftY + 37);
			}
			overlay_selected.draw(appResWidth - appResWidth / 4 - appResWidth / 8, appResHeight / 2 - overlay_selected.getHeight() / 2);

			getCenterImage();
			graphics.drawString(decimalFormat.format(highscores.get(selected).getScore()), center.getWidth(), center.getHeight());
			graphics.drawString(decimalFormat.format(gameData.getDeviceScore()), center.getWidth(), center.getHeight() + 100);

			spinner.draw(center.getWidth() - spinner.getWidth() / 2, center.getHeight() - spinner.getHeight() / 2);
			spinneroverlay.draw(center.getWidth() - spinner.getWidth() / 2, center.getHeight() - spinner.getHeight() / 2);
		}
	}

	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
		super.update(gameContainer, stateBasedGame, delta);
		rotation += (targetrotation - rotation) / rotationEase;
		tandwiel1.setRotation(rotation);
		tandwiel2.setRotation((float) ((float) -(rotation * 1.818181818181818) + 16.36363636363636));
		spinner.setRotation(rotation);

		float listRotation = (float) (((-rotation - rotationDelta) * listSpeedFactor) - lastHighscoreDelta);
		int possibleTopDraw = (int) ((listRotation * -1) / scoreHeight);
		selected = possibleTopDraw + 5;
		if (selected > highscores.size() - 6) {
			selected = highscores.size() - 6;
		}else if (selected < 0) {
			selected = 0;
		}
		if (possibleTopDraw >= 0) {
			scrollDelta = (int) (listRotation % scoreHeight);
			topDraw = possibleTopDraw;
		} else {
			scrollDelta = (int) (((possibleTopDraw * -1) * scoreHeight) + (listRotation % scoreHeight));
		}
		tandwiel3.setRotation((float) ((float) listRotation * 0.31255 - 6.7));
		highscoreBackgroundHeight = (int) ((listRotation) % background_highscore.getHeight());
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
					Transition fadeIn = new FadeInTransition();
					Transition fadeOut = new FadeOutTransition();
					stateBasedGame.enterState(AsaGame.INFOSTATE, fadeOut, fadeIn);
				}
			}
		});
	}

	private void resetGame() {
		arduino.removeAllListeners();
		spinnerSouth = decimalFormat.format(gameData.getPlayerScore()) + " kWh, ";
		underSpinner = "";
	}

	private void getCenterImage() throws SlickException {
		if (highscores.get(selected).getFoto().equals("yes")) {
			File f = new File(highscores.get(selected).getId() + ".png");
			if(f.exists()){
				centerImage = new Image(highscores.get(selected).getId() + ".png");
				centerImage.getSubImage(50, 0, 540, 480).draw(center.getWidth()-((640-50)/2)+20, center.getHeight()-(480/2)-20);
			} else {
				centerImage = new Image(Resource.getPath("avatar.png"));
				centerImage.draw(center.getWidth() - centerImage.getWidth() / 2, center.getHeight() - centerImage.getHeight());
			}
		}
		else{
			centerImage = new Image(Resource.getPath("avatar.png"));
			centerImage.draw(center.getWidth() - centerImage.getWidth() / 2, center.getHeight() - centerImage.getHeight());
		}
	}
}