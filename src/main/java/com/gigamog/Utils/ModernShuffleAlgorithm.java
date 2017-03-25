package com.gigamog.Utils;

import java.util.ArrayList;
import java.util.Random;

import com.gigamog.CardModels.Card;




/*
 * 
 * as explained in config value file, I only implemented one algorithm because
 * if I had to reduce scope this seemed logical
 * 
 * 
 * Full disclosure I google best shuffle algorithm and implemeneted it. 
 * Seemed irresponsible to spend to much time on this aspect since I was on a
 * time crunch.
 * 
 */

public class ModernShuffleAlgorithm implements ShuffleAlgorithm {


	@Override
	public void run(ArrayList<Card> cards) {
		Random r = new Random();
		int maxSize  = cards.size()-1;
		for(int i=maxSize; i>0; i--){
			//we grab a random card between 0 and whichever the last card was
			int randomI = r.nextInt(i);
			
			//we make a reference to the current card we are manipulating
			Card originalCard = cards.get(i);
			
			//we replace the original card with the random one
			cards.set(i, cards.get(randomI));
			
			//we place the random one with the original one
			cards.set(randomI, originalCard);
			
		}
		
	}

}
