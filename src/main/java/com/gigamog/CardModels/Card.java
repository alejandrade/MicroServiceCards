package com.gigamog.CardModels;

public class Card extends Jsonable {

	/*
	 * this is just a card object, makes things easier to work with
	 * nothing special going on here
	 */

	private String deckName;
	private String rank;
	private String suit;

	public Card(String deckName, String rank, String suit) {
		this.deckName = deckName;
		this.rank = rank;
		this.suit = suit;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getSuit() {
		return suit;
	}

	public void setSuit(String suit) {
		this.suit = suit;
	}

	public String getCompositeCardType() {
		return rank + "-" + suit;
	}

	public String getDeckName() {
		return deckName;
	}

	public void setDeckName(String deckName) {
		this.deckName = deckName;
	}

}
