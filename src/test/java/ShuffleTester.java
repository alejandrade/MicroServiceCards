import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;

import com.gigamog.cardmodels.Card;
import com.gigamog.cardmodels.Deck;
import com.gigamog.shufflealgorithms.ModernShuffleAlgorithm;
import com.gigamog.shufflealgorithms.ShuffleAlgorithm;
import com.gigamog.utils.ConfigValues;


public class ShuffleTester {
	
	//we grab algorithm from config file so we can test it before deploying
	ShuffleAlgorithm alg = ConfigValues.getShuffleAlgorithm();
	
	@Test
	public void testModernShuffleNotEqual(){
		//here we are testing that the shuffle is actually shuffling
		
		Deck pok = new Deck("red13");
		StringBuilder beforeShuffle = new StringBuilder();
		for(Card card : pok.getDeck()){
			beforeShuffle.append(card.getCompositeCardType());
		}
		
		StringBuilder afterShuffle = new StringBuilder();
		pok.shuffle(alg);
		for(Card card : pok.getDeck()){
			afterShuffle.append(card.getCompositeCardType());
		}
		
		
		Assert.assertTrue(!beforeShuffle.toString().equals(afterShuffle.toString()));
		
	}
	
	@Test
	public void testSecurity(){
		// a deck of cards can be arranged an absurdly amount of times uniquely.
		//however computers are not random... but if I can get 100 thousand we are good
		for(int i=0; i<100000; i++){
			Deck pok = new Deck("red13");
			StringBuilder beforeShuffle = new StringBuilder();
			for(Card card : pok.getDeck()){
				beforeShuffle.append(card.getCompositeCardType());
			}
			
			StringBuilder afterShuffle = new StringBuilder();
			pok.shuffle(alg);
			for(Card card : pok.getDeck()){
				afterShuffle.append(card.getCompositeCardType());
			}
			
			
			Assert.assertTrue(!beforeShuffle.toString().equals(afterShuffle.toString()));
		}

		
	}
	
	@Test
	public void testModernShuffleNotBraking(){
		
		//here we are testing that the shuffle isn't causing some kind of weird duplicates
		//it also tests that there is still 52 cards in the deck
		Deck pok = new Deck("red13");
		
		pok.shuffle(alg);
		HashSet<String> hs = new HashSet<String>();
		for(Card card : pok.getDeck()){
			hs.add(card.getCompositeCardType());
		}
		Assert.assertEquals(52, hs.size());
	}
	
	
	@Test(timeout=100)
	public void testPerformance(){
		//we are making sure the shuffle doesn't take over a 100 miliseconds
		Deck pok = new Deck("red13");
		pok.shuffle(alg);
	}


}
