package com.gigamog.CardModels;

import java.util.ArrayList;

public class Decks extends Jsonable {
	
	/*
	 * this class is just here to be a json response
	 */
	
	int numberOfDecks;
	ArrayList<Deck> cardDecks;
	
	public int getNumberOfDecks() {
		return numberOfDecks;
	}
	public void setNumberOfDecks(int numberOfDecks) {
		this.numberOfDecks = numberOfDecks;
	}
	public ArrayList<Deck> getCardDecks() {
		if(cardDecks == null)
			cardDecks =  new ArrayList<>();
		
		return cardDecks;
	}
}
