package asa.client.resources;

import java.net.URL;

public class Resource {

	public static final String RESOURCE_PATH = "/asa/client/resources/";
	public static final String LOGCONFIG = RESOURCE_PATH + "log4j.properties";
	public static final String BACKGROUND = "../src/asa/client/resources/gearsanimation/00.png";
	public static final String[] GEARS_SEQUENCE = {
		RESOURCE_PATH + "gearsanimation/00.png",
		RESOURCE_PATH + "gearsanimation/01.png",
		RESOURCE_PATH + "gearsanimation/02.png",
		RESOURCE_PATH + "gearsanimation/03.png",
		RESOURCE_PATH + "gearsanimation/04.png",
		RESOURCE_PATH + "gearsanimation/05.png",
		RESOURCE_PATH + "gearsanimation/06.png",
		RESOURCE_PATH + "gearsanimation/07.png",
		RESOURCE_PATH + "gearsanimation/08.png",
		RESOURCE_PATH + "gearsanimation/09.png",
		RESOURCE_PATH + "gearsanimation/10.png",
		RESOURCE_PATH + "gearsanimation/11.png",
		RESOURCE_PATH + "gearsanimation/12.png",
		RESOURCE_PATH + "gearsanimation/13.png",
		RESOURCE_PATH + "gearsanimation/14.png",
		RESOURCE_PATH + "gearsanimation/15.png", 
		RESOURCE_PATH + "gearsanimation/16.png",
		RESOURCE_PATH + "gearsanimation/17.png",
		RESOURCE_PATH + "gearsanimation/18.png"
	};

	public static URL get(String path) {
		URL resourceUrl = null;
		try {
			resourceUrl = Resource.class.getResource(path);
		} catch (Exception exception) {

		}
		return resourceUrl;
	}
}
