package asa.client.resources;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Resource {

	public static final String RESOURCE_PATH = "/asa/client/resources/";
	
	public static final String LOGCONFIG = "log4j.properties";

	public static final String TANDWIEL1 = "tandwiel1.png";
	public static final String TANDWIEL2 = "tandwiel2.png";
	public static final String TANDWIEL3 = "tandwiel3.png";
	public static final String TANDWIEL4 = "tandwiel4.png";
	public static final String TANDWIEL5 = "tandwiel5.png";
	public static final String TANDWIEL6 = "tandwiel6.png";
	
	public static final String BACKGROUND_KOFFIE = "background_koffie.png";
	public static final String BACKGROUND_PRINTER = "background_printer.png";
	public static final String BACKGROUND_AUTOMAAT = "background_cola.png";
	public static final String BACKGROUND_BEAMER = "background_beamert.png";
	
	public static final String ICON_KOFFIE = "icon_koffie.png";
	public static final String ICON_PRINTER = "icon_printer.png";
	public static final String ICON_AUTOMAAT = "icon_automaat.png";
	public static final String ICON_BEAMER = "icon_beamer.png";
	
	public static final String SPINNER = "spinner.png";
	public static final String SPINNER_OVERLAY = "spinneroverlay.png";
	public static final String BACKGROUND_SPINNER = "background_spinner.png";
	
	
	public static URL getURL(String path) {
		URL resourceUrl = null;
		try {
			resourceUrl = Resource.class.getResource(RESOURCE_PATH + path);
		} catch (Exception exception) {

		}
		return resourceUrl;
	}
	
	public static String getPath(String path){
		try {
			String filepath = Resource.class.getResource(RESOURCE_PATH + path).getFile();
			return URLDecoder.decode(filepath, "UTF-8").toString();
		} catch (UnsupportedEncodingException ex) {
			Logger.getLogger(Resource.class.getName()).log(Level.SEVERE, null, ex);
			return "Not found";
		}
	}
}
