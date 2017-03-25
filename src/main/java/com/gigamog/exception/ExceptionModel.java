package com.gigamog.exception;

import com.google.gson.Gson;

public class ExceptionModel {
	
	/*
	 * Just trying to return a friendly looking error so coder can be prepared for it
	 */
	
	int status;
	String errorMessage = "";
	
	public ExceptionModel(int status, String errorMessage){
		this.status = status;
		this.errorMessage = errorMessage;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	

}
