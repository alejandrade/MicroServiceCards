package com.gigamog.datastore;

import com.gigamog.cardmodels.Deck;
import com.gigamog.cardmodels.Decks;



//hopefully all my data modules will be able to follow this, that way I can interchange between datastores easily
public interface DataStorageModule {

	public void insert(Deck fcd);
	public void update(Deck fcd, String deckName);
	public Deck selectOne(String deckName);
	public Decks selectAll();
	public void delete(String deckName);
	
	
}
