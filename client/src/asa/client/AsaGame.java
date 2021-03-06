package asa.client;

import asa.client.DTO.GameData;
import asa.client.resources.Resource;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.ScalableGame;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.state.transition.Transition;
import org.newdawn.slick.util.Log;

public class AsaGame extends StateBasedGame{
	private ServerAdapter server;

	public static final int INFOSTATE = 0;
	public static final int GAMESTATE = 1;
	public static final int PHOTOSTATE = 2;
	public static final int HIGHSCORESTATE = 3;

	public static final Transition FADEIN = new FadeInTransition();
	public static final Transition FADEOUT = new FadeOutTransition();

	public static Dimension RESOLUTION;
	public static final Dimension SOURCE_RESOLUTION = new Dimension(1920, 1080);

	private Logger logger = Logger.getLogger(AsaGame.class);

	private static final boolean DEBUG = false;

	private GameData gameData;

	public AsaGame() {
		super("ASA-Game");
		this.server = new ServerAdapter();
		setLog4jConfiguration();
		printSystemInfo();
		setResolution();
		gameData = new GameData();
	}

	private void setLog4jConfiguration(){
		URL propertiesFile = Resource.getURL(Resource.LOGCONFIG);
		PropertyConfigurator.configure(propertiesFile);
		Log.setLogSystem(new SlickLogger());
	}

	private void printSystemInfo(){
		Runtime runtime = Runtime.getRuntime();
		logger.debug("Available processors: " + runtime.availableProcessors());
		logger.debug("Free memory: " + runtime.freeMemory());
		logger.debug("Max memory: " + runtime.maxMemory());
		logger.debug("Total memory: " + runtime.totalMemory());
	}

	public final void setResolution(){
		AsaGame.RESOLUTION = Toolkit.getDefaultToolkit().getScreenSize();
	}


	@Override
	public void initStatesList(GameContainer gameContainer) throws SlickException{
		this.addState(new InfoState(INFOSTATE, this.server, gameData));
		this.addState(new GameState(GAMESTATE, this.server, gameData));
		this.addState(new PhotoState(PHOTOSTATE, this.server, gameData));
		this.addState(new HighscoreState(HIGHSCORESTATE, this.server, gameData));
		this.preRenderState(gameContainer, gameContainer.getGraphics());
	}

	public static void main(String[] args) throws SlickException{
		ScalableGame scalableGame = new ScalableGame(new AsaGame(), SOURCE_RESOLUTION.width, SOURCE_RESOLUTION.height, true);
		AppGameContainer application = new AppGameContainer(scalableGame);
		application.setTargetFrameRate(100);
		application.setShowFPS(DEBUG);
		application.setDisplayMode(RESOLUTION.width,  RESOLUTION.height, !DEBUG);
		application.setMouseGrabbed(!DEBUG);
		application.start();
	}
}