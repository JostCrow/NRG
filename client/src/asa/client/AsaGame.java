package asa.client;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import asa.client.resources.Resource;

public class AsaGame extends StateBasedGame{

	public static final int INFOSTATE = 0;
	public static final int GAMESTATE = 1;
	public static final int HIGHSCORESTATE = 2;
	
	private Logger logger = Logger.getLogger(AsaGame.class);
	
	public AsaGame() {
		super("ASA-Game");
		setLog4jConfiguration();
		printSystemInfo();
	}
	
	private void setLog4jConfiguration(){
		URL propertiesFile = Resource.get(Resource.LOGCONFIG);
		PropertyConfigurator.configure(propertiesFile);
	}
	
	private void printSystemInfo(){
		Logger logger = Logger.getLogger(AsaGame.class);
		Runtime runtime = Runtime.getRuntime();
		
		logger.debug("Available processors: " + runtime.availableProcessors());
		logger.debug("Free memory: " + runtime.freeMemory());
		logger.debug("Max memory: " + runtime.maxMemory());
		logger.debug("Total memory: " + runtime.totalMemory());
	}
	
	public static void main(String[] args) throws SlickException{
		AppGameContainer application = new AppGameContainer(new AsaGame());
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		application.setDisplayMode(800,  500, false);
		//application.setDisplayMode(screenSize.width,  screenSize.height, true);
		application.start();
		application.setTargetFrameRate(20);
		Arduino arduino = Arduino.getInstance();
	}
	
	@Override
	public void initStatesList(GameContainer gameContainer) throws SlickException{
		this.addState(new InfoState(INFOSTATE));
		this.addState(new GameState(GAMESTATE));
		this.addState(new HighscoreState(HIGHSCORESTATE));
	}
}