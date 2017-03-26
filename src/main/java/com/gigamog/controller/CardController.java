package com.gigamog.controller;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.gigamog.cardmodels.Deck;
import com.gigamog.cardmodels.Decks;
import com.gigamog.datastore.DataStorageModule;
import com.gigamog.datastore.InSession;
import com.gigamog.utils.ConfigValues;

@Singleton
//version since if we upgrade and change endpoints we don't want to break old code
//we set card because we might have other endpoints later that have nothing to do with cards
@Path("/v1/card")
public class CardController {

	// we create the request here since we are using session as our memory
	@Context
	HttpServletRequest request;

	/*
	 * so originally I wanted to add the datastore class here so that it can be
	 * easily changed, however for some reason it crashed my application with a
	 * very non descriptive error. Since I'm trying to stay with the under 8ish
	 * hours rules I decided to just skip it
	 */

	//DataStorageModule db = new InSession(request);



	@PUT
	@Path("/{deckName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createDeck(@PathParam("deckName") String deckName) {

		Deck playingCards = new Deck(deckName);

		// we pass the InSessionVariable which implements DataStorageModule.
		// any datastore we use will implements it and all we have to do is
		// change this variable to move it

		playingCards.insert(new InSession(request));

		/*
		 * so here I'm doing toString because for some reason jackson is not
		 * serializing for me. I'm fairly sure it's because I need to find the
		 * right package to add to gradle but I can't seem to get it to work.
		 * Since I have a time limit I decided to serialize the cheap cheesy
		 * way.
		 * 
		 * In my own projects I ussualy add gson as a provider to replace
		 * jackson. However I decided to stay true to the test and timeline and
		 * not add code from my passed project. I
		 * 
		 */

		return Response.status(200).entity(playingCards.toString()).build();
	}

	@POST
	@Path("/{deckName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response shuffleDeck(@PathParam("deckName") String deckName) {

		Deck playingCards = new Deck().selectOne(new InSession(request), deckName);

		playingCards.shuffle(ConfigValues.getShuffleAlgorithm());

		playingCards.update(new InSession(request));

		/*
		 * so the specs did not say to return the deck on shuffle but I feel
		 * like if some one shuffled the deck they are going to want their cards
		 * back. I figure this is a good way to save a trip to the server
		 */

		return Response.status(200).entity(playingCards.toString()).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listAllDecks() {

		Decks decks = new Deck().selectAll(new InSession(request));

		return Response.status(200).entity(decks.toString()).build();
	}

	
	@GET
	@Path("/{deckName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDeck(@PathParam("deckName") String deckName) {

		return Response.status(200).entity(new Deck().selectOne(new InSession(request), deckName).toString()).build();
	}

	@DELETE
	@Path("/{deckName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteDeck(@PathParam("deckName") String deckName) {
		// we do not return a body as it is a 204 return

		new Deck().delete(new InSession(request), deckName);
		return Response.status(204).build();
	}

}
