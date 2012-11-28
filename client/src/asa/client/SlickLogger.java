package asa.client;

import org.apache.log4j.Logger;
import org.newdawn.slick.util.LogSystem;

public class SlickLogger implements LogSystem {

	private Logger logger = Logger.getLogger(SlickLogger.class);
	
	@Override
	public void debug(String message) {
		logger.debug(message);
	}

	@Override
	public void error(Throwable throwable) {
		logger.error(throwable);
	}

	@Override
	public void error(String message) {
		logger.error(message);
	}

	@Override
	public void error(String message, Throwable throwable) {
		logger.error(message, throwable);
	}

	@Override
	public void info(String message) {
		logger.info(message);
	}

	@Override
	public void warn(String message) {
		logger.warn(message);
	}

	@Override
	public void warn(String message, Throwable throwable) {
		logger.warn(message, throwable);
	}

}
