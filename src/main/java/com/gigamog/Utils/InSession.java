package com.gigamog.Utils;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.gigamog.CardModels.Deck;
import com.gigamog.CardModels.Decks;
import com.gigamog.exception.CustomHttpException;
import com.google.gson.Gson;

public class InSession implements DataStorageModule {
	ServletContext sc;

	// the requirements said use in memory so I figured session would be
	// necessary.
	public InSession(HttpServletRequest request) {
		HttpSession session= request.getSession(true);
		this.sc = session.getServletContext();

	}

	@Override
	public void insert(Deck fcd) {
		checkSession();
		
		//because insert by spec is indempotent it will always return a brand new deck.
		//I interpret this as the deck will be overwritten
		if(sc.getAttribute(fcd.getDeckName()) != null)
			sc.removeAttribute(fcd.getDeckName());

		
		//then we save it
		sc.setAttribute(fcd.getDeckName(), fcd);


	}
	
	@Override
	public void update(Deck fcd, String deckName) {
		checkSession();
		checkDeckExists(deckName);
		sc.removeAttribute(fcd.getDeckName());
		sc.setAttribute(fcd.getDeckName(), fcd);
		
	}

	@Override
	public void delete(String deckName) {
		checkSession();
		checkDeckExists(deckName);
		sc.removeAttribute(deckName);

	}

	@Override
	public Deck selectOne(String deckName) {
		checkSession();
		checkDeckExists(deckName);
		return (Deck)sc.getAttribute(deckName);
	}
	
	@Override
	public Decks selectAll() {
		Enumeration<String> deckNames = sc.getAttributeNames();
		Decks decks = new Decks();
		Gson gson = new Gson();
		
		while(deckNames.hasMoreElements()){
			decks.getCardDecks().add((Deck) sc.getAttribute(deckNames.nextElement()));
		}
		decks.setNumberOfDecks(decks.getCardDecks().size());
		
		return decks;
	}

	
	private void checkDeckExists(String deckName){
		if(sc.getAttribute(deckName) == null)
			throw new CustomHttpException("There doesn't seem to be a deck by that name", 404);
	}
	
	private void checkSession(){
		//so if I have issues with the session 
		//this will throw an unchecked exception which should safely take me to my exception mapper
		//I moved it to a function so that I don't have to keep writing it ontop of every function

		if (sc == null) 
			throw new CustomHttpException("Sorry, seems I'm having session issues", 404);
	}



}
