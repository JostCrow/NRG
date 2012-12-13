package asa.client;

import asa.client.DTO.GameData;
import asa.client.resources.Resource;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.log4j.Logger;
import org.lwjgl.util.Dimension;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
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

public class GameState extends ArduinoGameState {

	Logger logger = Logger.getLogger(this.getClass());
	ServerAdapter server;
	Device device;
	StateBasedGame stateBasedGame;
	GameData gameData;
	
	Image tandwiel1;
	Image tandwiel2;
	Image background;
	Image tandwiel3;
	Image tandwiel4;
	Image background_spinner;
	Image spinneroverlay;
	Image icon_background;
	Image count_down1;
	Image count_down2;
	Image start;
	Image count_down;

	int image = 3;
	int targetrotation = 0;
	int tandwielOffset = 30;
	int selectionDegrees = 45;
	int selectedOption = 0;
	int oldSelectedOption = 0;
	
	float rotation = 0;
	float spinnerrotation = 0;
	
	double deviceScore = 0;
	double score = 0;
	double rotationEase = 5.0;
	
	Dimension screenSize;
	Dimension center;
	
	boolean gamestarted = false;
	boolean countdownActive = true;

	public GameState(int stateID, ServerAdapter server, GameData gameData) {
		super(stateID);
		this.server = server;
		this.gameData = gameData;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		stateBasedGame = sbg;
		
		center = new Dimension(AsaGame.SOURCE_RESOLUTION.width / 2 - 100, AsaGame.SOURCE_RESOLUTION.height / 2);
		background = new Image(Resource.getPath(Resource.GAME_BACKGROUND));
		tandwiel1 = new Image(Resource.getPath(Resource.TANDWIEL5));
		tandwiel2 = new Image(Resource.getPath(Resource.TANDWIEL6));
		tandwiel3 = new Image(Resource.getPath(Resource.TANDWIEL6));
		tandwiel4 = new Image(Resource.getPath(Resource.TANDWIEL6));
		count_down = new Image(Resource.getPath(Resource.COUNT_DOUWN3));
		count_down1 = new Image(Resource.getPath(Resource.COUNT_DOUWN1));
		count_down2 = new Image(Resource.getPath(Resource.COUNT_DOUWN2));
		start = new Image(Resource.getPath(Resource.START_GAME));
	}

	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		setFont(graphics);
		background.draw(0, 0);
		tandwiel1.draw(-tandwiel1.getWidth() / 2, AsaGame.SOURCE_RESOLUTION.height / 2 - tandwiel1.getHeight() / 2);
		tandwiel2.draw(tandwiel1.getWidth() / 2 - tandwielOffset - 40, AsaGame.SOURCE_RESOLUTION.height / 2 - tandwiel2.getHeight());
		tandwiel3.draw(tandwiel2.getWidth() - tandwielOffset, AsaGame.SOURCE_RESOLUTION.height / 2 - (tandwiel3.getHeight() / 7));
		tandwiel4.draw(tandwiel2.getWidth() - tandwielOffset + (tandwiel3.getWidth() - (tandwiel3.getWidth() / 5)), AsaGame.SOURCE_RESOLUTION.height / 2 - (tandwiel3.getHeight() - 145));
		graphics.drawString(decimalFormat.format(score), center.getWidth(), center.getHeight());
		graphics.drawString(decimalFormat.format(deviceScore) + "", (center.getWidth()*2 - 100 - (int)(String.valueOf(deviceScore).length())), center.getHeight());

		if(countdownActive){
			count_down.draw(center.getWidth() + (count_down.getWidth()), center.getHeight() - (count_down.getHeight() / 2));
		}
	}

	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
		super.update(gameContainer, stateBasedGame, delta);
		rotation += (targetrotation - rotation) / rotationEase;
		tandwiel1.setRotation(rotation);
		tandwiel2.setRotation((float) ((float) -(rotation * 1.818181818181818) + 16.36363636363636));
		tandwiel3.setRotation((float) ((float) (rotation * 1.818181818181818) - 3));
		tandwiel4.setRotation((float) ((float) -(rotation * 1.818181818181818) + 14.36363636363636));
		count_down.setAlpha((float)(count_down.getAlpha() + 0.02));
		if (gamestarted) {
			deviceScore = deviceScore + ((device.getWattTotal() / device.getDivideBy()) /100);
		}
	}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg){
		arduino.addListener(new ArduinoAdapter() {
			@Override
			public void wheelEvent(int direction, int speed) {
				if (direction == 1) {
					targetrotation += 3 * speed;
				} else {
					targetrotation -= 3 * speed;
				}
				if (gamestarted) {
					score = score + (((double)speed*2)/10);
				}
			}
		});
		device = server.getDeviceById(gameData.getDeviceId());
		try {
			background = new Image(Resource.getPath(device.getPhotoUrl()));
		} catch (SlickException ex) {
			
		}
		countdownActive = true;
		score = 0;
		deviceScore = 0;
		startTimer();
	}
	
	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException {
		arduino.removeAllListeners();
	}
	
	public void setFont(Graphics graphics) throws SlickException{
		graphics.setFont(new AngelCodeFont(Resource.getPath("OnzeFont2.fnt"), Resource.getPath("OnzeFont2_0.tga")));
	}

	public void startTimer(){
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					count_down = new Image(Resource.getPath(Resource.COUNT_DOUWN2));
					count_down.setAlpha(0);
				} catch (SlickException ex) {
					
				}
			}
		}, 1000);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					count_down = new Image(Resource.getPath(Resource.COUNT_DOUWN1));
					count_down.setAlpha(0);
				} catch (SlickException ex) {
					
				}
			}
		}, 2000);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					count_down = new Image(Resource.getPath(Resource.START_GAME));
					count_down.setAlpha(0);
				} catch (SlickException ex) {
					
				}
			}
		}, 3000);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				gamestarted = true;
			}
		}, 3100);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				countdownActive = false;
			}
		}, 4000);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				gamestarted = false;
				gameData.setDeviceScore(deviceScore);
				gameData.setPlayerScore(score);
			}
		}, 13100);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Transition fadeIn = new FadeInTransition();
				Transition fadeOut = new FadeOutTransition();
				stateBasedGame.enterState(AsaGame.HIGHSCORESTATE, fadeOut, fadeIn);
			}
		}, 18000);
	}
}