package com.gigamog.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.gigamog.shufflealgorithms.ModernShuffleAlgorithm;
import com.gigamog.shufflealgorithms.ShuffleAlgorithm;

public class ConfigValues {

	/*
	 * I wrote this stub so that when I implement property file I don't have to
	 * change any of the main code.
	 * 
	 */

	static Logger log = Logger.getLogger(ConfigValues.class.getName());
	private static final String SHUFFLE_ALGORITHM = "modern";
	private static final boolean IsDebug = true;
	/*
	 * I don't think there is any reason the software should break if the future
	 * config file is incorrect. Because of this I decided to throw modern
	 * shuffle by default.
	 */
	public static ShuffleAlgorithm getShuffleAlgorithm() {
		ShuffleAlgorithm shuffle;
		
		switch (SHUFFLE_ALGORITHM) {

		case "modern":
			shuffle = new ModernShuffleAlgorithm();
			break;
		default:

			shuffle = new ModernShuffleAlgorithm();
			log.log(Level.WARNING, "Invalid shuffle algorithm, modern shuffle has been set by default");
		}
		return shuffle;

	}
	
	//this turns on more detailed error in the exception mapper
	public static boolean getIsDebug(){
		return IsDebug;
	}

}
