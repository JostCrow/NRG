package asa.client;
import asa.client.resources.Resource;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
//import org.petecode.jgrabber.GrabberThread;
//import org.petecode.jgrabber.jGrabber;

public class HighscoreState extends BasicGameState {

	int stateID = -1;
//	jGrabber grabber = new jGrabber();
//	GrabberThread grabberThread;

	public HighscoreState(int stateID) {
		this.stateID = stateID;
	}

	@Override
	public int getID() {
		return stateID;
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
//		try {
//			grabberThread = new GrabberThread("test", Resource.getPath(""), new URL("/dev/input/mice"), 10, 20000, 20000);
//			grabberThread.run();
//		} catch (MalformedURLException ex) {
//			Logger.getLogger(HighscoreState.class.getName()).log(Level.SEVERE, null, ex);
//		}
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

	}

}