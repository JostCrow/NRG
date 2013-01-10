package asa.client;

import asa.client.DTO.GameData;
import asa.client.resources.Resource;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.log4j.Logger;
import org.lwjgl.util.Dimension;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import service.Device;

public class GameState extends ArduinoGameState {

	private Logger logger = Logger.getLogger(this.getClass());
	private ServerAdapter server;
	private Device device;
	private GameData gameData;
	private Timer timer;
	private StateBasedGame stateBasedGame;

	private Image tandwiel1;
	private Image tandwiel2;
	private Image background;
	private Image wires;
	private Image tandwiel3;
	private Image count_down;
	private Image count_down1;
	private Image count_down2;
	private Image device_icon;
	private Image linker_kastje;
	private Image rechter_kastje;
	private Image touwtjes;
	private Image spinner1;
	private Image spinner2;
	private Image spinner3;
	private Image linker_kastje_boven;
	private Image voortgangsbalk;
	private Image player_voortgang;
	private Image device_voortgang;
	private Image overlay;
	private Image red_number;
	private Image black_number;

	private Animation clock;

	private int startposition;
	private int targetrotation = 0;
	private int voortgangs_start_location = 0;

	private int[] playerPositions = new int[7];
	private int[] devicePositions = new int[7];

	private float rotation = 0;
	private float player_voortgang_location = 0;
	private float device_voortgang_location = 0;
	private float height_calc = 0;

	private double deviceScore = 0;
	private double score = 0;
	private double rotationEase = 5.0;

	private Dimension center;

	private boolean gamestarted = false;
	private boolean countdownActive = true;
	private boolean uitleg = true;

	private Random random;

	public GameState(int stateID, ServerAdapter server, GameData gameData) {
		super(stateID);
		this.server = server;
		this.gameData = gameData;
	}

	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
		this.stateBasedGame = stateBasedGame;
		center = new Dimension(AsaGame.SOURCE_RESOLUTION.width / 2 - 100, AsaGame.SOURCE_RESOLUTION.height / 2);
		background = new Image(Resource.getPath(Resource.GAME_BACKGROUND));
		overlay = new Image(Resource.getPath(Resource.OVERLAY));
		wires = new Image(Resource.getPath(Resource.WIRES));
		tandwiel1 = new Image(Resource.getPath(Resource.TANDWIEL5));
		tandwiel2 = new Image(Resource.getPath(Resource.TANDWIEL6));
		tandwiel3 = new Image(Resource.getPath(Resource.TANDWIEL6));
		count_down1 = new Image(Resource.getPath(Resource.COUNT_DOUWN1));
		count_down2 = new Image(Resource.getPath(Resource.COUNT_DOUWN2));
		count_down = new Image(Resource.getPath(Resource.COUNT_DOUWN3));
		device_icon = new Image(Resource.getPath(Resource.ICON_KOFFIE));
		linker_kastje = new Image(Resource.getPath(Resource.KASTJE_LINKS));
		linker_kastje_boven = new Image(Resource.getPath(Resource.KASTJE_LINKS_BOVEN));
		rechter_kastje = new Image(Resource.getPath(Resource.KASTJE_RECHTS));
		touwtjes = new Image(Resource.getPath(Resource.ROPES));
		spinner1 = new Image(Resource.getPath(Resource.GAME_SPINNER));
		spinner2 = new Image(Resource.getPath(Resource.GAME_SPINNER));
		spinner3 = new Image(Resource.getPath(Resource.GAME_SPINNER));

		voortgangsbalk = new Image(Resource.getPath(Resource.VOORTGANGS_BALK));
		player_voortgang = new Image(Resource.getPath(Resource.PLAYER_));
		device_voortgang = new Image(Resource.getPath(Resource.DEVICE_));
		voortgangs_start_location = voortgangsbalk.getHeight()-50;
		player_voortgang_location = voortgangsbalk.getHeight()-50;
		device_voortgang_location = voortgangsbalk.getHeight()-50;

		red_number = new Image(Resource.getPath(Resource.NUMBERS_RED));
		black_number = new Image(Resource.getPath(Resource.NUMBERS_BLACK));
		random = new Random();
		clock = new Animation();
		clock.setLooping(false);
		for(int i = 0; i < 601; i++){
			if((i+"").length() == 1){
				clock.addFrame(new Image(Resource.getPath("KLOK/Comp 1_0000" + i + ".png")), 20000/601);
			} else if((i+"").length() == 2){
				clock.addFrame(new Image(Resource.getPath("KLOK/Comp 1_000" + i + ".png")), 20000/601);
			} else{
				clock.addFrame(new Image(Resource.getPath("KLOK/Comp 1_00" + i + ".png")), 20000/601);
			}
		}
		clock.stop();
		resetPositions();
	}

	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		background.draw(0, 0);
		wires.draw(center.getWidth()*2-wires.getWidth(), 0);
		tandwiel1.draw(-tandwiel1.getWidth() / 2, AsaGame.SOURCE_RESOLUTION.height / 2 - tandwiel1.getHeight() / 2);
		tandwiel2.draw(center.getWidth()/2-tandwiel2.getWidth()/2-tandwiel2.getWidth()/4, center.getHeight() / 2 - tandwiel2.getHeight()/2);
		tandwiel3.draw(center.getWidth()/2+tandwiel2.getWidth()/6-3, center.getHeight() / 2 - tandwiel2.getHeight()/3);

		touwtjes.draw(linker_kastje.getWidth()/2+70+linker_kastje.getWidth()/16, center.getHeight()*2 - linker_kastje.getHeight()-touwtjes.getHeight());
		spinner1.draw(linker_kastje.getWidth()/2+70+linker_kastje.getWidth()/16, center.getHeight()*2 - linker_kastje.getHeight()-spinner1.getHeight()+spinner1.getHeight()/3);
		spinner2.draw(linker_kastje.getWidth()/2+220+linker_kastje.getWidth()/16, center.getHeight()*2 - linker_kastje.getHeight()-spinner2.getHeight()+spinner1.getHeight()/3);
		spinner3.draw(linker_kastje.getWidth()/2+363+linker_kastje.getWidth()/16, center.getHeight()*2 - linker_kastje.getHeight()-spinner3.getHeight()+spinner1.getHeight()/3);
		black_number.getSubImage(0, startposition-playerPositions[0], black_number.getWidth(), 73).draw(linker_kastje.getWidth()/2+linker_kastje.getWidth()/16+82, startposition);
		black_number.getSubImage(0, startposition-playerPositions[1], black_number.getWidth(), 73).draw(linker_kastje.getWidth()/2+linker_kastje.getWidth()/16+85+black_number.getWidth()+4, startposition);
		black_number.getSubImage(0, startposition-playerPositions[2], black_number.getWidth(), 73).draw(linker_kastje.getWidth()/2+linker_kastje.getWidth()/16+85+black_number.getWidth()*2+11, startposition);
		black_number.getSubImage(0, startposition-playerPositions[3], black_number.getWidth(), 73).draw(linker_kastje.getWidth()/2+linker_kastje.getWidth()/16+85+black_number.getWidth()*3+18, startposition);
		black_number.getSubImage(0, startposition-playerPositions[4], black_number.getWidth(), 73).draw(linker_kastje.getWidth()/2+linker_kastje.getWidth()/16+85+black_number.getWidth()*4+26, startposition);
		red_number.getSubImage(0, startposition-playerPositions[5], black_number.getWidth(), 73).draw(linker_kastje.getWidth()/2+linker_kastje.getWidth()/16+85+black_number.getWidth()*5+33, startposition);
		red_number.getSubImage(0, startposition-playerPositions[6], black_number.getWidth(), 73).draw(linker_kastje.getWidth()/2+linker_kastje.getWidth()/16+85+black_number.getWidth()*6+40, startposition);
		linker_kastje.draw(linker_kastje.getWidth()/2+linker_kastje.getWidth()/16, center.getHeight()*2 - linker_kastje.getHeight());
		linker_kastje_boven.draw(360, center.getHeight()*2 - linker_kastje.getHeight()-touwtjes.getHeight()-linker_kastje_boven.getHeight());

		clock.draw(linker_kastje.getWidth()/2+linker_kastje.getWidth()/16+linker_kastje.getWidth(), center.getHeight()*2 - linker_kastje.getHeight()-linker_kastje.getHeight()/3);

		black_number.getSubImage(0, startposition-devicePositions[0], black_number.getWidth(), 73).draw(center.getWidth()*2 - rechter_kastje.getWidth()+rechter_kastje.getWidth()/6+75, startposition);
		black_number.getSubImage(0, startposition-devicePositions[1], black_number.getWidth(), 73).draw(center.getWidth()*2 - rechter_kastje.getWidth()+rechter_kastje.getWidth()/6+78+black_number.getWidth()+4, startposition);
		black_number.getSubImage(0, startposition-devicePositions[2], black_number.getWidth(), 73).draw(center.getWidth()*2 - rechter_kastje.getWidth()+rechter_kastje.getWidth()/6+78+black_number.getWidth()*2+11, startposition);
		black_number.getSubImage(0, startposition-devicePositions[3], black_number.getWidth(), 73).draw(center.getWidth()*2 - rechter_kastje.getWidth()+rechter_kastje.getWidth()/6+78+black_number.getWidth()*3+19, startposition);
		black_number.getSubImage(0, startposition-devicePositions[4], black_number.getWidth(), 73).draw(center.getWidth()*2 - rechter_kastje.getWidth()+rechter_kastje.getWidth()/6+78+black_number.getWidth()*4+27, startposition);
		red_number.getSubImage(0, startposition-devicePositions[5], black_number.getWidth(), 73).draw(center.getWidth()*2 - rechter_kastje.getWidth()+rechter_kastje.getWidth()/6+78+black_number.getWidth()*5+34, startposition);
		red_number.getSubImage(0, startposition-devicePositions[6], black_number.getWidth(), 73).draw(center.getWidth()*2 - rechter_kastje.getWidth()+rechter_kastje.getWidth()/6+78+black_number.getWidth()*6+41, startposition);
		rechter_kastje.draw(center.getWidth()*2 - rechter_kastje.getWidth()+rechter_kastje.getWidth()/6-5, center.getHeight()*2 - rechter_kastje.getHeight());
		device_icon.draw(center.getWidth()*2 - rechter_kastje.getWidth()/2- device_icon.getWidth()/2+rechter_kastje.getWidth()/6-5, center.getHeight()*2- rechter_kastje.getHeight()/2-rechter_kastje.getHeight()/16);

		voortgangsbalk.draw(center.getWidth()+voortgangsbalk.getWidth()*2+voortgangsbalk.getWidth()/4, 10);
		player_voortgang.draw(center.getWidth()+voortgangsbalk.getWidth()*2+voortgangsbalk.getWidth()/4-player_voortgang.getWidth()+18, player_voortgang_location);
		device_voortgang.draw(center.getWidth()+voortgangsbalk.getWidth()*2+voortgangsbalk.getWidth()/4+voortgangsbalk.getWidth()-19, device_voortgang_location);
		device_icon.draw(center.getWidth()+voortgangsbalk.getWidth()*2+voortgangsbalk.getWidth()/4+voortgangsbalk.getWidth()-4, device_voortgang_location, 0.7f);

		if(countdownActive){
			count_down.draw(linker_kastje.getWidth()/2+linker_kastje.getWidth()/16+linker_kastje.getWidth()+65, center.getHeight()*2 - linker_kastje.getHeight()-linker_kastje.getHeight()/3);
		}
		if(uitleg){
			overlay.draw(0, 0);
		}
	}

	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
		super.update(gameContainer, stateBasedGame, delta);
		rotation += (targetrotation - rotation) / rotationEase;
		tandwiel1.setRotation(rotation);
		tandwiel2.setRotation((float) ((float) -(rotation*1.818181818181818)+9.36363636363636));
		tandwiel3.setRotation((float) ((float) (rotation*1.818181818181818)+15.36363636363636));
		if (gamestarted) {
			spinner1.setRotation((float) ((float) (rotation * 0.018181818181818) + 14.36363636363636));
			spinner2.setRotation((float) ((float) (rotation * 0.818181818181818)));
			spinner3.setRotation((float) ((float) (rotation * 8.818181818181818) + 14.36363636363636));

			deviceScore = deviceScore + random((float)device.getWattTotal() / device.getDivideBy());
			String number = specialFormat.format(deviceScore);
			number = number.replace(",", "");
			for(int i = 0; i < devicePositions.length; i++){
				try{
					int test = Integer.parseInt(number.substring(i, i+1));
					devicePositions[i] = startposition - (test*73);
					if(devicePositions[i] < (startposition -(73*9))){
						devicePositions[i] = startposition;
					}
				} catch(Exception e){
					logger.error("Could not convert devicescore to ints: " + e.getMessage());
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
					logger.error("Could not convert playerscore to ints: " + e.getMessage());
				}
			}

			if(score>height_calc){
				player_voortgang_location = (float)(voortgangs_start_location - ((score/score))*(voortgangs_start_location));
				device_voortgang_location = (float)(voortgangs_start_location - ((deviceScore/score))*(voortgangs_start_location));
			} else{
				player_voortgang_location = (float)(voortgangs_start_location - ((score/height_calc))*(voortgangs_start_location));
				device_voortgang_location = (float)(voortgangs_start_location - ((deviceScore/height_calc))*(voortgangs_start_location));
			}

		}
	}

	@Override
	public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame){
		initiateListeners(stateBasedGame);
		setSelectedDevice();
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
		clock.restart();
		clock.stop();
		uitleg = true;
		player_voortgang_location = voortgangsbalk.getHeight()-50;
		device_voortgang_location = voortgangsbalk.getHeight()-50;
		resetPositions();
		try {
			count_down = new Image(Resource.getPath(Resource.COUNT_DOUWN3));
		} catch (SlickException ex) {
			logger.error("Could not load CountdownImage3: " + ex.getMessage());
		}
	}

	private void startGame(){
		timer = new Timer();
		startTimer(timer);
	}

	private void startTimer(Timer timer){
		/**
		 * change the image to the number 2.
		 */
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					count_down = new Image(Resource.getPath(Resource.COUNT_DOUWN2));
				} catch (SlickException ex) {
					logger.error("Could not load CountdownImage2: " + ex.getMessage());
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
				} catch (SlickException ex) {
					logger.error("Could not load CountdownImage1: " + ex.getMessage());
				}
			}
		}, 2000);
		/**
		 * Starts the game.
		 */
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				gamestarted = true;
				clock.start();
				countdownActive = false;
			}
		}, 3000);
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
		}, 23000);
		/**
		 * starts the HIGHSCORE state.
		 */
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if(score > 0){
					stateBasedGame.enterState(AsaGame.PHOTOSTATE, AsaGame.FADEOUT, AsaGame.FADEIN);
				} else {
					stateBasedGame.enterState(AsaGame.INFOSTATE, AsaGame.FADEOUT, AsaGame.FADEIN);
				}
			}
		}, 26000);
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
					score = score + (((double)speed/100));
				}
			}
			@Override
			public void buttonEvent(){
				if(uitleg){
					startGame();
					uitleg = false;
				} else if(gamestarted) {
					stateBasedGame.enterState(AsaGame.INFOSTATE, AsaGame.FADEOUT, AsaGame.FADEIN);
				}
			}
		});
	}

	private void setSelectedDevice() {
		System.out.println(gameData.getDeviceId());
		device = server.getDeviceById(gameData.getDeviceId());
		try {
			device_icon = new Image(Resource.getPath(device.getLogoUrl()));
		} catch (SlickException ex) {
			logger.error("Could not load deviceIcon: " + ex.getMessage());
		}
		height_calc = (float)((device.getWattTotal()/device.getDivideBy())*20);
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
		int range = (int)((max - min)*1000) ;
		float number = (random.nextInt(range)/1000) + min;

		float test = (float)number/100;
		return test;
	}

	private void resetPositions() {
		startposition = center.getHeight()*2 - linker_kastje.getHeight()+ linker_kastje.getHeight()/8;
		for(int i = 0; i < playerPositions.length; i++){
			playerPositions[i] = startposition;
		}
		for(int i = 0; i< devicePositions.length; i++){
			devicePositions[i] = startposition;
		}
	}
}