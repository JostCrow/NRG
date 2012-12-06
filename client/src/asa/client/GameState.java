package asa.client;

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

public class GameState extends ArduinoGameState {

	ServerAdapter server;
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
	Image count_down3;
	Image start;
	
	int image = 3;
	
	Logger logger = Logger.getLogger(this.getClass());
	int targetrotation = 0;
	int tandwielOffset = 30;
	float rotation = 0;
	float spinnerrotation = 0;
	double rotationEase = 5.0;
	Dimension screenSize;
	Dimension center;
	int selectionDegrees = 45;
	int selectedOption = 0;
	int oldSelectedOption = 0;
	double score = 0;
	boolean gamestarted = false;

	public GameState(int stateID, ServerAdapter server) {
		super(stateID);
		this.server = server;
		this.stateID = stateID;
	}

	@Override
	public int getID() {
		return stateID;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		arduino.addListener(new ArduinoAdapter() {
			@Override
			public void wheelEvent(int direction, int speed) {
				if (direction == 1) {
					targetrotation += 3 * speed;
				} else {
					targetrotation -= 3 * speed;
				}
				if (gamestarted) {
					score = score + speed;
				}
			}
		});
		startTimer();

		center = new Dimension(AsaGame.SOURCE_RESOLUTION.width / 2 - 100, AsaGame.SOURCE_RESOLUTION.height / 2);
		tandwiel1 = new Image(Resource.getPath(Resource.TANDWIEL5));
		tandwiel2 = new Image(Resource.getPath(Resource.TANDWIEL6));
		tandwiel3 = new Image(Resource.getPath(Resource.TANDWIEL6));
		tandwiel4 = new Image(Resource.getPath(Resource.TANDWIEL6));
		background = new Image(Resource.getPath(Resource.GAME_BACKGROUND));
		count_down1 = new Image(Resource.getPath(Resource.COUNT_DOUWN1));
		count_down2 = new Image(Resource.getPath(Resource.COUNT_DOUWN2));
		count_down3 = new Image(Resource.getPath(Resource.COUNT_DOUWN3));
		start = new Image(Resource.getPath(Resource.START_GAME));
	}

	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		background.draw(0, 0);		
		tandwiel1.draw(-tandwiel1.getWidth() / 2, AsaGame.SOURCE_RESOLUTION.height / 2 - tandwiel1.getHeight() / 2);
		tandwiel2.draw(tandwiel1.getWidth() / 2 - tandwielOffset - 40, AsaGame.SOURCE_RESOLUTION.height / 2 - tandwiel2.getHeight());
		tandwiel3.draw(tandwiel2.getWidth() - tandwielOffset, AsaGame.SOURCE_RESOLUTION.height / 2 - (tandwiel3.getHeight() / 7));
		tandwiel4.draw(tandwiel2.getWidth() - tandwielOffset + (tandwiel3.getWidth() - (tandwiel3.getWidth() / 5)), AsaGame.SOURCE_RESOLUTION.height / 2 - (tandwiel3.getHeight() - 145));
		graphics.setFont(new AngelCodeFont(Resource.getPath("OnzeFont.fnt"), Resource.getPath("OnzeFont_1.tga")));
		graphics.setColor(Color.black);
		int length = String.valueOf(score).length();
		graphics.drawString(score + "", (center.getWidth() - ((length) * 13)), center.getHeight());
		
		if(image == 3){
			count_down3.draw(center.getWidth()+(count_down3.getWidth()/2),center.getHeight()-(count_down3.getHeight()/2));
		} else if (image == 2){
			count_down2.draw(center.getWidth()+(count_down2.getWidth()/2),center.getHeight()-(count_down2.getHeight()/2));
		} else if (image == 1){
			count_down1.draw(center.getWidth()+(count_down1.getWidth()/2),center.getHeight()-(count_down1.getHeight()/2));
		} else if (image == 0){
			start.draw(center.getWidth()+(start.getWidth()/2),center.getHeight()-(start.getHeight()/2));
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
	}

	public void startTimer() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				image = 2;
			}
		}, 1000);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				image = 1;
			}
		}, 2000);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				image = 0;
				gamestarted = true;
			}
		}, 3000);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				image = -1;
			}
		}, 4000);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				gamestarted = false;
			}
		}, 23000);
	}
}