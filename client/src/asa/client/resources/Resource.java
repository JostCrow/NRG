package asa.client.resources;

import java.net.URL;

public class Resource {

	public static final String RESOURCE_PATH = "/asa/client/resources/";
	public static final String LOGCONFIG = RESOURCE_PATH + "log4j.properties";
	public static final String BACKGROUND = "../src/asa/client/resources/gearsanimation/00.png";

	public static final String TANDWIEL1 = RESOURCE_PATH + "tandwiel1.png";
	public static final String TANDWIEL2 = RESOURCE_PATH + "tandwiel2.png";
	public static final String TANDWIEL3 = RESOURCE_PATH + "tandwiel3.png";
	public static final String TANDWIEL4 = RESOURCE_PATH + "tandwiel4.png";
	public static final String TANDWIEL5 = RESOURCE_PATH + "tandwiel5.png";
	public static final String TANDWIEL6 = RESOURCE_PATH + "tandwiel6.png";
	
	public static final String BACKGROUND_KOFFIE = RESOURCE_PATH + "background_koffie.png";
	public static final String SPINNER = RESOURCE_PATH + "spinner.png";
	public static final String BACKGROUND_SPINNER = RESOURCE_PATH + "background_spinner.png";
	
	public static URL getURL(String path) {
		URL resourceUrl = null;
		try {
			resourceUrl = Resource.class.getResource(path);
		} catch (Exception exception) {

		}
		return resourceUrl;
	}
	
	public static String getPath(String path){
		return "C:/Users/Rene van Aerle/Git/PhysicASA/client/src" + path;
	}
}
