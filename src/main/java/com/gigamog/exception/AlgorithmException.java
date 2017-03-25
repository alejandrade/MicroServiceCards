package com.gigamog.exception;

public class AlgorithmException extends Exception {

	/*
	 * this class allowed me to throw exceptions to http code so it's kinda cool
	 */

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AlgorithmException(String errorMessage) {
		super(errorMessage);
	}

}
