package asa.client;

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
import org.newdawn.slick.util.Log;

import asa.client.resources.Resource;

public class AsaGame extends StateBasedGame{

	public static final int INFOSTATE = 0;
	public static final int GAMESTATE = 1;
	public static final int HIGHSCORESTATE = 2;
	
	public static Dimension RESOLUTION;
	public static final Dimension SOURCE_RESOLUTION = new Dimension(1920, 1080);
	
	private Logger logger = Logger.getLogger(AsaGame.class);
	
	private static final boolean DEBUG = false;
	
	public AsaGame() {
		super("ASA-Game");
		setLog4jConfiguration();
		printSystemInfo();
		setResolution();
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
	
	public void setResolution(){
		AsaGame.RESOLUTION = Toolkit.getDefaultToolkit().getScreenSize();
	}
	
	@Override
	public void initStatesList(GameContainer gameContainer) throws SlickException{
		this.addState(new InfoState(INFOSTATE));
		this.addState(new GameState(GAMESTATE));
		this.addState(new HighscoreState(HIGHSCORESTATE));
	}
	
	public static void main(String[] args) throws SlickException{
		ScalableGame scalableGame = new ScalableGame(new AsaGame(), SOURCE_RESOLUTION.width, SOURCE_RESOLUTION.height, true);
		AppGameContainer application = new AppGameContainer(scalableGame);
		application.setTargetFrameRate(100);
		if(AsaGame.DEBUG){
			application.setDisplayMode(RESOLUTION.width,  RESOLUTION.height, false);
		} else {
			application.setDisplayMode(RESOLUTION.width,  RESOLUTION.height, true);
			application.setMouseGrabbed(true);
		}
		application.start();
	}
}