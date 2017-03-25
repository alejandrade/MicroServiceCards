package com.gigamog.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class WelcomeController {
	

	/*
	 * Root of the project. That way you know it's working when you first start
	 * it. I like doing this as it acts as a canary when you want to check if
	 * your server has crashed.
	 * 
	 */
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response welcome() {
		StringBuilder buil = new StringBuilder();
		buil.append("<h3><a href='https://github.com/alejandrade/NikeDemo'>please click here for documentation</a></h3>");
		return Response.status(200).entity(buil.toString()).build();
	}
}
