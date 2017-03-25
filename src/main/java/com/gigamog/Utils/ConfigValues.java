package com.gigamog.Utils;

import java.util.logging.Logger;

import com.gigamog.exception.CustomHttpException;

public class ConfigValues {
	
	/*
	 * I wrote this stub so that when I implement property file I don't have to change
	 * any of the main code. 
	 * 
	 * I decided to not implement this because I only have one algorithm.
	 * 
	 * 
	 * This is a singleton because that way I can load properties from file once and have values set.
	 * Then I can write a method to reload them at runtime.
	 */
	
	
	static Logger log = Logger.getLogger(ConfigValues.class.getName());
	private static ConfigValues instance;
	private static final String SHUFFLE_ALGORITHM = "SHUFFLE_ALGORITHM";

	private ConfigValues() {

	}

	public static ConfigValues getInstance() {

		if (instance == null)
			instance = new ConfigValues();
		return instance;
	}

	public ShuffleAlgorithm getShuffleAlgorithm() {
		ShuffleAlgorithm shuffle;
		switch ("modern") {

		case "modern":
			shuffle = new ModernShuffleAlgorithm();
			break;
		default:
			//in my property file I will have SHUFFLE_ALGORITHM this just tell the developer 
			//that he messed up something
			throw new CustomHttpException(SHUFFLE_ALGORITHM + " is missing from config file", 500);
		}
		return shuffle;

	}

}
