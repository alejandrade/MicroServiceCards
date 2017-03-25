package com.gigamog.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.gigamog.utils.ConfigValues;

@Provider
public class MyExceptionMapper implements ExceptionMapper<Exception> {

	/*
	 * Pretending that this is a production software, I feel like the exception
	 * mapper is one of the most important parts of any web service. I decided
	 * to implement it. It also makes debuging results much easyer
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
						
			if(ConfigValues.getIsDebug()){
				exception.printStackTrace();
			mod = new ExceptionModel(500, "there seems to have been an unspecified error, if you do not want"
					+ "to see this error message please set debug to false in configurations"
					+ " here is the error" + exception.getMessage());

			}else{
				mod = new ExceptionModel(500, "there seems to have been an unspecified error");
			}

		}
		
		return Response.status(mod.getStatus())
				.entity(mod.toString())
				.type(MediaType.APPLICATION_JSON)
				.build();
	}

}
