package com.gigamog.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MyExceptionMapper implements ExceptionMapper<Exception> {
	
	/*
	 * Pretending that this is a production software, I feel like the exception mapper is one of the most important
	 * parts of any web service. I decided to implement it. It also makes debuging results much easyer
	 */
	
	
	
	@Override
	public Response toResponse(Exception exception) {
		
		ExceptionModel mod = null;

		if (exception instanceof CustomHttpException) {
			CustomHttpException exc = (CustomHttpException) exception;
			mod = new ExceptionModel(exc.getHttpErrorCode(), exc.getMessage());

		} else if (exception instanceof UnauthorizedRequestException) {
			UnauthorizedRequestException exc = (UnauthorizedRequestException) exception;
			mod = new ExceptionModel(exc.getHttpErrorCode(), exc.getMessage());
		} else {
			mod = new ExceptionModel(500, "there seems to have been an unspecified error,"
					+ " because this is debug here is the error: " + exception.getMessage());

		}
		//exception.printStackTrace();
		return Response.status(mod.getStatus()).entity(mod.toString()).build();
	}

}
