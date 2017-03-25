package com.gigamog.CardModels;

import java.util.ArrayList;

import com.gigamog.Utils.DataStorageModule;
import com.gigamog.Utils.ShuffleAlgorithm;

public class Deck extends Jsonable{
	
	/*
	 * made these values transient as they don't need to be serialized	
	 */
	private final transient String[] SUITS = { "Spades", "Hearts", "Diamonds", "Clubs" };
	private final transient String[] RANKS = { "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King" };
	
	
	private String deckName;
	private ArrayList<Card> deck;

	/*
	 * I use this constructor to build decks
	 */
	
	public Deck(String deckName) {
		this.deckName = deckName;
		deck = new ArrayList<>();
		//on instantiation we create the deck because the object is a deck so it needs cards.
		for (String suit : SUITS) {
			for (String rank : RANKS) {
				
				deck.add(new Card(deckName, rank, suit));
			}
		}
		
		
	}
//this one when I need to call methods
	public Deck(){
		
	}
	
	/*
	 * as you can see none of these methods have anything special going on. This is because I can dependency injection
	 * everything I need to this class. 
	 */
	
	public void shuffle(ShuffleAlgorithm alg) {
		alg.run(this.getDeck());
	}


	public void insert(DataStorageModule dsm) {
		dsm.insert(this);
	}
	

	public void update(DataStorageModule dsm) {
		dsm.update(this, this.deckName);
	}


	public Deck selectOne(DataStorageModule dsm, String deckName) {
		return dsm.selectOne(deckName);
		
	}
	
	public Decks selectAll(DataStorageModule dsm){
		return dsm.selectAll();
	}
	
	public void delete(DataStorageModule dsm, String deckName){
		dsm.delete(deckName);
	}


	public String getDeckName() {
		return deckName;
	}

	public void setDeckName(String deckName) {
		this.deckName = deckName;
	}

	public ArrayList<Card> getDeck() {
		return deck;
	}

	public void setDeck(ArrayList<Card> deck) {
		this.deck = deck;
	}

}
