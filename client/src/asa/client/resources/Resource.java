package asa.client.resources;

import java.awt.Color;
import java.awt.Font;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

import org.apache.log4j.Logger;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class Resource {

	private static Logger logger = Logger.getLogger(Resource.class);
	
	public static final String RESOURCE_PATH = "/asa/client/resources/";
	
	public static final String LOGCONFIG = "log4j.properties";

	public static final String TANDWIEL1 = "tandwiel1.png";
	public static final String TANDWIEL2 = "tandwiel2.png";
	public static final String TANDWIEL3 = "tandwiel3.png";
	public static final String TANDWIEL4 = "tandwiel4.png";
	public static final String TANDWIEL5 = "tandwiel5.png";
	public static final String TANDWIEL6 = "tandwiel6.png";
	public static final String TANDWIEL7 = "tandwiel7.png";
	
	public static final String BACKGROUND_KOFFIE = "background_koffie.png";
	public static final String BACKGROUND_PRINTER = "background_printer.png";
	public static final String BACKGROUND_AUTOMAAT = "background_cola.png";
	public static final String BACKGROUND_BEAMER = "background_beamert.png";
	
	public static final String ICON_BACKGROUND_EASY = "icon_background_easy.png";
	public static final String ICON_BACKGROUND_MEDIUM = "icon_background_medium.png";
	public static final String ICON_BACKGROUND_HARD = "icon_background_hard.png";
	
	public static final String ICON_KOFFIE = "icon_koffie.png";
	public static final String ICON_PRINTER = "icon_printer.png";
	public static final String ICON_AUTOMAAT = "icon_automaat.png";
	public static final String ICON_BEAMER = "icon_beamer.png";
	
	public static final String ICONS_DETAILS = "icons_details.png";
	
	public static final String SPINNER = "spinner.png";
	public static final String SPINNER_OVERLAY = "spinneroverlay.png";
	public static final String BACKGROUND_SPINNER = "background_spinner.png";
	
	public static final String GAME_BACKGROUND = "game_background.png";
	public static final String COUNT_DOUWN1 = "nederland1-300.png";
	public static final String COUNT_DOUWN2 = "nederland2-300.png";
	public static final String COUNT_DOUWN3 = "nederland3-300.png";
	public static final String START_GAME = "start.png";
	public static final String NUMBERS_RED = "cijfertjesrood.png";
	public static final String NUMBERS_BLACK = "cijfertjeszwart.png";
	public static final String KASTJE_LINKS = "linkerkastje.png";
	public static final String KASTJE_LINKS_BOVEN = "kastjelinksboven.png";
	public static final String KASTJE_RECHTS = "rechterkastje.png";
	public static final String ROPES = "touwtjes.png";
	public static final String GAME_SPINNER = "draaispoel.png";
	public static final String WIRES = "draden.png";
	public static final String PLAYER_ = "voortganglinks.png";
	public static final String DEVICE_ = "voortgangrechts.png";
	public static final String VOORTGANGS_BALK = "voortgangsbalk.png";
	public static final String OVERLAY = "overlayspel1.png";
	
	public static final String background_highscore = "background_highscore.png";
	public static final String background_item_highscore = "background_item_highscore.png";
	public static final String overlay_selected = "overlay_selected.png";
	public static final String tandwiel_vertical = "tandwiel_vertical.png";
	public static final String PERSON = "person.png";
	
	public static final String LABEL_EASY = "label_easy.png";
	public static final String LABEL_MEDIUM = "label_medium.png";
	public static final String LABEL_HARD = "label_hard.png";
	
	public static final String FONT_SANCHEZ = "Sanchezregular.tff";	
	
	public static URL getURL(String path) {
		URL resourceUrl = null;
		try {
			resourceUrl = Resource.class.getResource(RESOURCE_PATH + path);
		} catch (Exception exception) {

		}
		return resourceUrl;
	}
	
	public static String getPath(String path){
		String filepath = "";
		try {
			String resource = Resource.class.getResource(RESOURCE_PATH + path).getFile();
			filepath = URLDecoder.decode(resource, "UTF-8").toString();
		} catch (UnsupportedEncodingException ex) {
			logger.error("Unable to locate resource " + path);
		} catch (NullPointerException ex){
			logger.error("NullPointer: Unable to locate resource " + RESOURCE_PATH + path);
		}
		return filepath;
	}
	
	@SuppressWarnings("unchecked")
	public static UnicodeFont getFont(String path, int fontSize, Color color){
		UnicodeFont font = new UnicodeFont(new Font(path, Font.PLAIN, fontSize));
		font.addAsciiGlyphs();
		font.addGlyphs(400, 600);
		font.getEffects().add(new ColorEffect(color));
		try {
			font.loadGlyphs();
		} catch (SlickException e){
			logger.error("Unable to load glyphs for font " + path);
		}
		return font;
	}
}
