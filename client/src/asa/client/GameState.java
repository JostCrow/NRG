package asa.client;

import asa.client.DTO.GameData;
import asa.client.resources.Resource;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.log4j.Logger;
import org.lwjgl.util.Dimension;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.state.transition.Transition;
import service.Device;

public class GameState extends ArduinoGameState {

	DecimalFormat specialFormat = new DecimalFormat("00000.00", new DecimalFormatSymbols(Locale.GERMAN));
	Logger logger = Logger.getLogger(this.getClass());
	ServerAdapter server;
	Device device;
	GameData gameData;
	Timer timer;
	int startposition;
	
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
	Image device_icon;
	Image linker_kastje;
	Image rechter_kastje;
	Image touwtjes;
	Image spinner1;
	Image spinner2;
	Image spinner3;
	Image linker_kastje_boven;
	
	Image red_number;
	Image black_number;

	int image = 3;
	int targetrotation = 0;
	int tandwielOffset = 30;
	int selectionDegrees = 45;
	int selectedOption = 0;
	int oldSelectedOption = 0;
	
	int[] playerPositions = new int[7];
	int[] devicePositions = new int[7];
	
	float rotation = 0;
	float spinnerrotation = 0;
	
	double deviceScore = 0;
	double score = 0;
	double rotationEase = 5.0;
	
	Dimension screenSize;
	Dimension center;
	
	boolean gamestarted = false;
	boolean countdownActive = true;
	
	Random random;

	public GameState(int stateID, ServerAdapter server, GameData gameData) {
		super(stateID);
		this.server = server;
		this.gameData = gameData;
	}

	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
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
		icon_background = new Image(Resource.getPath(Resource.ICON_BACKGROUND_EASY));
		device_icon = new Image(Resource.getPath(Resource.ICON_KOFFIE));
		linker_kastje = new Image(Resource.getPath(Resource.KASTJE_LINKS));
		linker_kastje_boven = new Image(Resource.getPath(Resource.KASTJE_LINKS_BOVEN));
		rechter_kastje = new Image(Resource.getPath(Resource.KASTJE_RECHTS));
		touwtjes = new Image(Resource.getPath(Resource.ROPES));
		spinner1 = new Image(Resource.getPath(Resource.GAME_SPINNER));
		spinner2 = new Image(Resource.getPath(Resource.GAME_SPINNER));
		spinner3 = new Image(Resource.getPath(Resource.GAME_SPINNER));
		
		red_number = new Image(Resource.getPath(Resource.NUMBERS_RED));
		black_number = new Image(Resource.getPath(Resource.NUMBERS_BLACK));
		random = new Random();
	}

	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		black_number.draw(linker_kastje.getWidth()/2+linker_kastje.getWidth()/6, playerPositions[0]);
		black_number.draw(linker_kastje.getWidth()/2+linker_kastje.getWidth()/6+((black_number.getWidth()+4)), playerPositions[1]);
		black_number.draw(linker_kastje.getWidth()/2+linker_kastje.getWidth()/6+((black_number.getWidth()+4)*2), playerPositions[2]);
		black_number.draw(linker_kastje.getWidth()/2+linker_kastje.getWidth()/6+((black_number.getWidth()+4)*3), playerPositions[3]);
		black_number.draw(linker_kastje.getWidth()/2+linker_kastje.getWidth()/6+((black_number.getWidth()+4)*4), playerPositions[4]);
		red_number.draw(linker_kastje.getWidth()/2+linker_kastje.getWidth()/6+((black_number.getWidth()+4)*5), playerPositions[5]);
		red_number.draw(linker_kastje.getWidth()/2+linker_kastje.getWidth()/6+((black_number.getWidth()+4)*6), playerPositions[6]);
		black_number.draw(center.getWidth()*2 - rechter_kastje.getWidth()+linker_kastje.getWidth()/6-15, devicePositions[0]);
		black_number.draw(center.getWidth()*2 - rechter_kastje.getWidth()+linker_kastje.getWidth()/6+((black_number.getWidth())-5), devicePositions[1]);
		black_number.draw(center.getWidth()*2 - rechter_kastje.getWidth()+linker_kastje.getWidth()/6+((black_number.getWidth())*2), devicePositions[2]);
		black_number.draw(center.getWidth()*2 - rechter_kastje.getWidth()+linker_kastje.getWidth()/6+((black_number.getWidth())*3+5), devicePositions[3]);
		black_number.draw(center.getWidth()*2 - rechter_kastje.getWidth()+linker_kastje.getWidth()/6+((black_number.getWidth())*4+15), devicePositions[4]);
		red_number.draw(center.getWidth()*2 - rechter_kastje.getWidth()+linker_kastje.getWidth()/6+((black_number.getWidth())*5+22), devicePositions[5]);
		red_number.draw(center.getWidth()*2 - rechter_kastje.getWidth()+linker_kastje.getWidth()/6+((black_number.getWidth())*6+30), devicePositions[6]);
		background.draw(0, 0);
		tandwiel1.draw(-tandwiel1.getWidth() / 2, AsaGame.SOURCE_RESOLUTION.height / 2 - tandwiel1.getHeight() / 2);
		if(countdownActive){
			count_down.draw(center.getWidth() + (count_down.getWidth()), center.getHeight() - (count_down.getHeight() / 2));
		}
		touwtjes.draw(linker_kastje.getWidth()/2+75, center.getHeight()*2 - linker_kastje.getHeight()-touwtjes.getHeight());
		spinner1.draw(linker_kastje.getWidth()/2+75, center.getHeight()*2 - linker_kastje.getHeight()-spinner1.getHeight()+spinner1.getHeight()/3);
		spinner2.draw(linker_kastje.getWidth()/2+225, center.getHeight()*2 - linker_kastje.getHeight()-spinner2.getHeight()+spinner1.getHeight()/3);
		spinner3.draw(linker_kastje.getWidth()/2+365, center.getHeight()*2 - linker_kastje.getHeight()-spinner3.getHeight()+spinner1.getHeight()/3);
		linker_kastje.draw(linker_kastje.getWidth()/2, center.getHeight()*2 - linker_kastje.getHeight());
		linker_kastje_boven.draw(350, center.getHeight()*2 - linker_kastje.getHeight()-touwtjes.getHeight()-linker_kastje_boven.getHeight());
		rechter_kastje.draw(center.getWidth()*2 - rechter_kastje.getWidth(), center.getHeight()*2 - rechter_kastje.getHeight());
		device_icon.draw(center.getWidth()*2 - rechter_kastje.getWidth()/2- device_icon.getWidth()/2, center.getHeight()*2- rechter_kastje.getHeight()/2-rechter_kastje.getHeight()/16);
	}
	
	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
		super.update(gameContainer, stateBasedGame, delta);
		rotation += (targetrotation - rotation) / rotationEase;
		tandwiel1.setRotation(rotation);
		count_down.setAlpha((float)(count_down.getAlpha() + 0.02));
		if (gamestarted) {
			spinner1.setRotation((float) ((float) -(rotation * 0.018181818181818) + 14.36363636363636));
			spinner2.setRotation((float) ((float) -(rotation * 0.818181818181818)));
			spinner3.setRotation((float) ((float) -(rotation * 4.818181818181818) + 14.36363636363636));
			
			deviceScore = deviceScore + random((float)device.getWattTotal() / device.getDivideBy());
			String number = specialFormat.format(deviceScore);
			number = number.replace(",", "");
			System.out.println(number);
			for(int i = 0; i < devicePositions.length; i++){
				try{
					int test = Integer.parseInt(number.substring(i, i+1));
					devicePositions[i] = startposition - (test*73);
					if(devicePositions[i] < (startposition -(73*9))){
						devicePositions[i] = startposition;
					}
				} catch(Exception e){
				}
			}
			String pnumber = specialFormat.format(score);
			pnumber = pnumber.replace(",", "");
			for(int i = 0; i < playerPositions.length; i++){
				try{
					int test = Integer.parseInt(pnumber.substring(i, i+1));
					playerPositions[i] = startposition - (test*73);
					if(playerPositions[i] < (startposition -(73*9))){
						playerPositions[i] = startposition;
					}
				} catch(Exception e){
				}
			}
		}
	}
	
	@Override
	public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame){
		initiateListeners(stateBasedGame);
		setSelectedDevice();
		startGame(stateBasedGame);
		resetPositions();
	}
	
	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException {
		arduino.removeAllListeners();
		resetGame();
		
	}
	
	private void resetGame(){
		timer.cancel();
		gamestarted = false;
		countdownActive = true;
		score = 0;
		deviceScore = 0;
		try {
			count_down = new Image(Resource.getPath(Resource.COUNT_DOUWN3));
		} catch (SlickException ex) {
		}
	}

	private void startGame(StateBasedGame stateBasedGame){
		timer = new Timer();
		startTimer(timer, stateBasedGame);
	}
	
	private void startTimer(Timer timer, final StateBasedGame stateBasedGame){
		/**
		 * change the image to the number 2.
		 */
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
		/**
		 * change the image to the number 1.
		 */
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
		/**
		 * change the image to START.
		 */
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
		/**
		 * starts the game.
		 */
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				gamestarted = true;
			}
		}, 3100);
		/**
		 * removes the countdown images.
		 */
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				countdownActive = false;
			}
		}, 4000);
		/**
		 * stops the game after 20 seconds.
		 */
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				gamestarted = false;
				gameData.setDeviceScore(deviceScore);
				gameData.setPlayerScore(score);
			}
		}, 23100);
		/**
		 * starts the HIGHSCORE state.
		 */
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Transition fadeIn = new FadeInTransition();
				Transition fadeOut = new FadeOutTransition();
				stateBasedGame.enterState(AsaGame.HIGHSCORESTATE, fadeOut, fadeIn);
			}
		}, 28000);
	}

	private void initiateListeners(final StateBasedGame stateBasedGame) {
		
		arduino.addListener(new ArduinoAdapter() {
			@Override
			public void wheelEvent(int direction, int speed) {
				if (direction == 1) {
					targetrotation += 3 * speed;
				} else {
					targetrotation -= 3 * speed;
				}
				if (gamestarted) {
					score = score + (((double)speed*2)/100);
				}
			}
			@Override
			public void buttonEvent(){
				Transition fadeIn = new FadeInTransition();
				Transition fadeOut = new FadeOutTransition();
				stateBasedGame.enterState(AsaGame.INFOSTATE, fadeOut, fadeIn);
			}
		});
	}

	private void setSelectedDevice() {
		device = server.getDeviceById(gameData.getDeviceId());
		try {
			if((device.getWattTotal()/device.getDivideBy()) > 300){
				icon_background = new Image(Resource.getPath(Resource.ICON_BACKGROUND_HARD));
			} else if ((device.getWattTotal()/device.getDivideBy()) > 100){
				icon_background = new Image(Resource.getPath(Resource.ICON_BACKGROUND_MEDIUM));
			} else {
				icon_background = new Image(Resource.getPath(Resource.ICON_BACKGROUND_EASY));
			}
			device_icon = new Image(Resource.getPath(device.getLogoUrl()));
		} catch (SlickException ex) {
		}
	}
	
	/**
	 * takes the device score and randomly generates a new score with a margin of 10%.
	 * 
	 * @param deviceAverage 
	 * @return 
	 */
	private float random(float deviceAverage){
		
		float min = ((deviceAverage) - (deviceAverage/10));
		float max = ((deviceAverage) + (deviceAverage/10));
		int range = (int)(max - min);
		float number = random.nextInt(range) + min;
		
		float test = (float)number/100;
		return test;
	}

	private void resetPositions() {
		startposition = center.getHeight()*2 - linker_kastje.getHeight()+ linker_kastje.getHeight()/8;
		playerPositions[0] = startposition;
		playerPositions[1] = startposition;
		playerPositions[2] = startposition;
		playerPositions[3] = startposition;
		playerPositions[4] = startposition;
		playerPositions[5] = startposition;
		playerPositions[6] = startposition;
		devicePositions[0] = startposition;
		devicePositions[1] = startposition;
		devicePositions[2] = startposition;
		devicePositions[3] = startposition;
		devicePositions[4] = startposition;
		devicePositions[5] = startposition;
		devicePositions[6] = startposition;
	}
}