package com.gigamog.exception;

public class UnauthorizedRequestException extends RuntimeException {
	/*
	 * where I work it has become best practice to throw unauthorized when the
	 * user tries to do something hes not supposed to do. This helpes me throw 
	 * that error easily
	 */
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int httpErrorCode = 401;
	
	public UnauthorizedRequestException(String errorMessage){
		super(errorMessage);
	}

	public int getHttpErrorCode() {
		return httpErrorCode;
	}

	public void setHttpErrorCode(int httpErrorCode) {
		this.httpErrorCode = httpErrorCode;
	}
}
