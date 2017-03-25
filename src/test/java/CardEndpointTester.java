import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import com.gigamog.Starter;
import com.gigamog.cardmodels.Card;
import com.gigamog.cardmodels.Deck;
import com.gigamog.cardmodels.Decks;
import com.google.gson.Gson;

public class CardEndpointTester {

	String currentUrls = "http://localhost:" + Starter.DEFAULT_PORT + Starter.DEFAULT_PATH + "/v1/card";
	Gson gson = new Gson();

	/*
	 * This test case is here to see if I'm throwing the right errors on delete
	 */

	@Test
	public void testDoubleDeleteError() {
		String doubleDeleteDeck = "furious";
		Resp respCreate = HttpUtil.HttpPut(currentUrls + "/" + doubleDeleteDeck, "");
		Resp respDeleteGood = HttpUtil.HttpDelete(currentUrls + "/" + doubleDeleteDeck);
		Resp respDeleteBad = HttpUtil.HttpDelete(currentUrls + "/" + doubleDeleteDeck);
		Assert.assertEquals(respDeleteGood.getStatus(), 204);
		Assert.assertEquals(respDeleteBad.getStatus(), 404);
	}

	/*
	 * this is here to see if I'm telling user the deck doesn't exist
	 */

	@Test
	public void testGetUknownDeck() {
		String unknownDeck = "pikachu";
		Resp respSingle = HttpUtil.HttpGet(currentUrls + "/" + unknownDeck);
		Assert.assertEquals(respSingle.getStatus(), 404);
	}

	/*
	 * some basic tests to make sure I can get one deck correctly
	 */
	@Test
	public void getOneDeck() {

		String deckName = "deckCreated";
		for (int i = 0; i < 10; i++) {
			Resp respCreate = HttpUtil.HttpPut(currentUrls + "/" + deckName + i, "");
		}
		Random r = new Random();

		int result = r.nextInt(9);
		Resp respSingle = HttpUtil.HttpGet(currentUrls + "/" + deckName + result);

		Deck deck = gson.fromJson(respSingle.getBody(), Deck.class);
		Assert.assertTrue(deck.getDeckName().equals(deckName + result));

	}

	/*
	 * some basic tests to make sure I delete a deck and that it's the right
	 * deck
	 */
	@Test
	public void deleteDeck() {
		String deckName = "deckCreated";
		for (int i = 0; i < 10; i++) {
			Resp respCreate = HttpUtil.HttpPut(currentUrls + "/" + deckName + i, "");
		}
		Random r = new Random();

		int result = r.nextInt(9);
		Resp respSingle = HttpUtil.HttpDelete(currentUrls + "/" + deckName + result);

		Resp respList = HttpUtil.HttpGet(currentUrls + "/");
		Decks decks = gson.fromJson(respList.getBody(), Decks.class);
		boolean itemWasDeleted = true;
		for (Deck deck : decks.getCardDecks()) {
			if (deck.getDeckName().equals("deckName+result")) {
				itemWasDeleted = false;
				break;
			}
		}

		Assert.assertTrue(itemWasDeleted);

	}

	/*
	 * making sure I get all the decks I created
	 */
	@Test
	public void getAllDecks() {
		for (int i = 0; i < 10; i++) {
			String deckName = "deckCreated" + i;
			Resp respCreate = HttpUtil.HttpPut(currentUrls + "/" + deckName, "");
		}

		Resp respList = HttpUtil.HttpGet(currentUrls + "/");
		
		Decks decks = gson.fromJson(respList.getBody(), Decks.class);
		Assert.assertTrue(decks.getNumberOfDecks() > 9);

	}

	/*
	 * just making sure I can create a deck and shuffle it and I don't loose or
	 * gain cards. This will come in handy when I implement more algorithms
	 */
	@Test
	public void shuffleDeck() {

		String deckName = "shuffleTest";
		Resp respCreate = HttpUtil.HttpPut(currentUrls + "/" + deckName, "");
		Resp resp = HttpUtil.HttpPost(currentUrls + "/" + deckName, "");

		Deck deckPre = gson.fromJson(respCreate.getBody(), Deck.class);

		Deck deckShuffled = gson.fromJson(resp.getBody(), Deck.class);

		// here we are checking our return makes sense
		Assert.assertTrue(deckShuffled.getDeckName().equals(deckName));
		Assert.assertTrue(deckShuffled.getDeck().size() == 52);

		StringBuilder beforeShuffle = new StringBuilder();
		for (Card card : deckPre.getDeck()) {
			beforeShuffle.append(card.getCompositeCardType());
		}

		StringBuilder afterShuffle = new StringBuilder();
		for (Card card : deckShuffled.getDeck()) {
			afterShuffle.append(card.getCompositeCardType());
		}

		// here we are checking our return is shuffled
		Assert.assertTrue(!beforeShuffle.toString().equals(afterShuffle.toString()));

	}

	@Test
	public void createDeck() {

		String deckName = "standard";
		Resp resp = HttpUtil.HttpPut(currentUrls + "/" + deckName, "");
		Gson gson = new Gson();
		Deck deck = gson.fromJson(resp.getBody(), Deck.class);
		// making sure the deck has the right name, the right number and the
		// right order
		Assert.assertTrue(deck.getDeckName().equals(deckName));
		Assert.assertTrue(deck.getDeck().size() == 52);
		Assert.assertTrue(deck.getDeck().get(0).getRank().equals("Ace"));

	}

}
