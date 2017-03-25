package com.gigamog.exception;

public class CustomHttpException extends RuntimeException {

	/*
	 * this class allowed me to throw exceptions to http code so it's kinda cool
	 */
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int httpErrorCode = 0;
	
	public CustomHttpException(String errorMessage, int httpErrorCode){
		super(errorMessage);
		this.httpErrorCode = httpErrorCode;
	}

	public int getHttpErrorCode() {
		return httpErrorCode;
	}

	public void setHttpErrorCode(int httpErrorCode) {
		this.httpErrorCode = httpErrorCode;
	}

}
