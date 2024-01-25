import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.RepeatedTest;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MyTest {
	BaccaratDealer theDealer;
	BaccaratGameLogic gameLogic;



	@BeforeEach
	void intializeTests() {
		theDealer = new BaccaratDealer();
		gameLogic = new BaccaratGameLogic();
		theDealer.generateDeck();
	}



	// CARD TEST BELOW

	@Test
	void cardTest() {
		Card card = new Card("Hearts", 7);
		assertEquals("Hearts", card.suite);
		assertEquals(7, card.value);
		assertEquals("7", card.name);
		assertFalse(card.isAce);
		assertFalse(card.isFaceCard);

		Card card2 = new Card("Clubs", 11);
		assertEquals("Clubs", card2.suite);
		assertEquals(11, card2.value);
		assertEquals("Jack", card2.name);
		assertFalse(card2.isAce);
		assertTrue(card2.isFaceCard);

		Card card3 = new Card("Diamonds", 14);
		assertEquals("Diamonds", card3.suite);
		assertEquals(14, card3.value);
		assertEquals("Ace", card3.name);
		assertTrue(card3.isAce);
		assertFalse(card3.isFaceCard);



	}



	// BACCARATDEALER TESTS BELOW

	@Test
	void generateDeckTest() {
		// should be the 2 of hearts
		assertEquals(2, theDealer.deck.get(0).value);
		assertEquals("2", theDealer.deck.get(0).name);
		assertEquals("Hearts", theDealer.deck.get(0).suite);
		// should be the 2 of diamonds
		assertEquals(2, theDealer.deck.get(13).value);
		assertEquals("2", theDealer.deck.get(13).name);
		assertEquals("Diamonds", theDealer.deck.get(13).suite);
		// should be the 2 of spades
		assertEquals(2, theDealer.deck.get(26).value);
		assertEquals("2", theDealer.deck.get(26).name);
		assertEquals("Spades", theDealer.deck.get(26).suite);
		// should be the 2 of clubs
		assertEquals(2, theDealer.deck.get(39).value);
		assertEquals("2", theDealer.deck.get(39).name);
		assertEquals("Clubs", theDealer.deck.get(39).suite);



	}



	@Test
	void generateDeckTest2() {
		// should be the ace of hearts
		assertEquals(14, theDealer.deck.get(12).value);
		assertEquals("Ace", theDealer.deck.get(12).name);
		assertEquals("Hearts", theDealer.deck.get(12).suite);
		// should be the ace of diamonds
		assertEquals(14, theDealer.deck.get(25).value);
		assertEquals("Ace", theDealer.deck.get(25).name);
		assertEquals("Diamonds", theDealer.deck.get(25).suite);
		// should be the ace of spades
		assertEquals(14, theDealer.deck.get(38).value);
		assertEquals("Ace", theDealer.deck.get(38).name);
		assertEquals("Spades", theDealer.deck.get(38).suite);
		// should be the ace of clubs
		assertEquals(14, theDealer.deck.get(51).value);
		assertEquals("Ace", theDealer.deck.get(51).name);
		assertEquals("Clubs", theDealer.deck.get(51).suite);
	}



	@Test
	void drawOneTest() {
		Card card1 = theDealer.drawOne();
		assertEquals(14, card1.value);
		assertEquals("Ace", card1.name); // King
		assertEquals("Clubs", card1.suite);
		assertTrue(card1.isAce);
		Card card2 = theDealer.drawOne();
		assertEquals(13, card2.value);
		assertEquals("King", card2.name); // King
		assertEquals("Clubs", card2.suite);
		assertTrue(card2.isFaceCard);
	}



	@Test
	void drawOneTest2() {
		theDealer.dealHand(); // change top of deck, deal one should produce different cards from first test
		Card card1 = theDealer.drawOne();
		assertNotEquals(14, card1.value); // 14
		assertNotEquals("Ace", card1.name); // Ace
		assertEquals(12, card1.value);
		assertEquals("Queen", card1.name); // King
		assertEquals("Clubs", card1.suite);
		assertTrue(card1.isFaceCard);
		Card card2 = theDealer.drawOne();
		assertNotEquals(13, card2.value); // 14
		assertNotEquals("King", card2.name); // Ace
		assertEquals(11, card2.value);
		assertEquals("Jack", card2.name); // King
		assertEquals("Clubs", card2.suite);
		assertTrue(card2.isFaceCard);
	}



	@Test
	void dealHandTest() {
		ArrayList<Card> hand = theDealer.dealHand();
		assertEquals(14, hand.get(0).value); // 14
		assertEquals("Ace", hand.get(0).name); // Ace
		assertEquals("Clubs", hand.get(0).suite); // Clubs
		assertTrue(hand.get(0).isAce); // Ace is ace
		assertEquals(13, hand.get(1).value); // 13
		assertEquals("King", hand.get(1).name); // King
		assertEquals("Clubs", hand.get(1).suite); // Clubs
		assertTrue(hand.get(1).isFaceCard); // King is face card
	}



	@Test
	void dealHandTest2() {
		ArrayList<Card> playerHand;
		playerHand = theDealer.dealHand();
		assertEquals(14, playerHand.get(0).value);
		assertEquals(13, playerHand.get(1).value);
		theDealer.generateDeck();
		ArrayList<Card> newHand;
		newHand = theDealer.dealHand();
		assertEquals(14, newHand.get(0).value);
		assertEquals(13, newHand.get(1).value);
	}



	@RepeatedTest(5)
	void shuffleTest() {
		ArrayList<Card> standardDeck = theDealer.deck;
		ArrayList<Card> tempDeck = theDealer.deck;
		assertEquals(standardDeck, tempDeck);

		theDealer.shuffleDeck();
		ArrayList<Card> shuffledDeck = theDealer.deck;
		assertNotEquals(standardDeck, shuffledDeck);
	}



	@Test
	void deckSize() {
		assertEquals(52, theDealer.deckSize());
		theDealer.dealHand();
		assertEquals(50, theDealer.deckSize());
		theDealer.drawOne();
		assertEquals(49, theDealer.deckSize());
		theDealer.dealHand();
		theDealer.drawOne();
		assertEquals(46, theDealer.deckSize());
	}



	@Test
	void deckSize2() {
		assertEquals(52, theDealer.deckSize());
		for (int i = 0; i < 26; i++) {
			theDealer.dealHand();
		}
		assertEquals(0, theDealer.deckSize());
	}






	// BACCARATGAMELOGIC TESTS BELOW

	@RepeatedTest(5)
	void handTotalTest() {
		theDealer.shuffleDeck();
		ArrayList<Card> playerHand = theDealer.dealHand();
		ArrayList<Card> bankerHand = theDealer.dealHand();
//		System.out.println("The player has the " + playerHand.get(0).name + " of " + playerHand.get(0).suite +
//				" and the " + playerHand.get(1).name + " of " + playerHand.get(1).suite);
//		System.out.println("The dealer has the " + dealerHand.get(0).name + " of " + dealerHand.get(0).suite +
//				" and the " + dealerHand.get(1).name + " of " + dealerHand.get(1).suite);
//		System.out.println("Player total = " + gameLogic.handTotal(playerHand));
//		System.out.println("Dealer total = " + gameLogic.handTotal(dealerHand));

		int playerActualValue;
		int bankerActualValue;
		playerActualValue = playerHand.get(0).value + playerHand.get(1).value;
		bankerActualValue = bankerHand.get(0).value + bankerHand.get(1).value;
		if (playerHand.get(0).isAce || playerHand.get(1).isAce) {
			assertNotEquals(playerActualValue, gameLogic.handTotal(playerHand));
		}
		else if (playerHand.get(0).isFaceCard || playerHand.get(1).isFaceCard) {
			assertNotEquals(playerActualValue, gameLogic.handTotal(playerHand));
		}
		else if (playerHand.get(0).value + playerHand.get(1).value >= 10) {
			assertEquals(playerActualValue - 10, gameLogic.handTotal(playerHand));
		}

		if (bankerHand.get(0).isAce || bankerHand.get(1).isAce) {
			assertNotEquals(bankerActualValue, gameLogic.handTotal(bankerHand));
		}
		else if (bankerHand.get(0).isFaceCard || bankerHand.get(1).isFaceCard) {
			assertNotEquals(bankerActualValue, gameLogic.handTotal(bankerHand));
		}
		else if (bankerHand.get(0).value + bankerHand.get(1).value >= 10) {
			assertEquals(bankerActualValue - 10, gameLogic.handTotal(bankerHand));
		}
	}



	@RepeatedTest(5)
	void evaluatePlayerDrawTest () {
		theDealer.shuffleDeck();
		ArrayList<Card> playerHand = theDealer.dealHand();

		if (gameLogic.handTotal(playerHand) <= 5) {
			assertTrue(gameLogic.evaluatePlayerDraw(playerHand));
		}
		if (gameLogic.handTotal(playerHand) == 6 || gameLogic.handTotal(playerHand) == 7) {
			assertFalse(gameLogic.evaluatePlayerDraw(playerHand));
		}
		if (gameLogic.handTotal(playerHand) >= 8) {
			assertFalse(gameLogic.evaluatePlayerDraw(playerHand));
		}
	}



	@RepeatedTest(5)
	void evaluateBankerDrawTest () {
		theDealer.shuffleDeck();
		ArrayList<Card> playerHand = theDealer.dealHand();
		ArrayList<Card> bankerHand = theDealer.dealHand();

//		System.out.println("The player has the " + playerHand.get(0).name + " of " + playerHand.get(0).suite +
//				" and the " + playerHand.get(1).name + " of " + playerHand.get(1).suite);
//		System.out.println("The Banker has the " + bankerHand.get(0).name + " of " + bankerHand.get(0).suite +
//				" and the " + bankerHand.get(1).name + " of " + bankerHand.get(1).suite);
//		System.out.println("Player total = " + gameLogic.handTotal(playerHand));
//		System.out.println("Banker total = " + gameLogic.handTotal(bankerHand));

		int playerCard3value; // value of players 3rd card in relation to rules of the game
		if (gameLogic.evaluatePlayerDraw(playerHand)) {
			Card playerCard3 = theDealer.drawOne();
			if (playerCard3.isFaceCard) {
				playerCard3value = 0;
			}
			else if (playerCard3.isAce) {
				playerCard3value = 1;
			}
			else {
				playerCard3value = playerCard3.value;
			}

			if (playerCard3value == 0 || playerCard3value == 1) {
				if (gameLogic.handTotal(bankerHand) <= 3) { // banker must draw
					assertTrue(gameLogic.evaluateBankerDraw(bankerHand, playerCard3));
				}
				else {
					assertFalse(gameLogic.evaluateBankerDraw(bankerHand, playerCard3));
				}
			}

			if (playerCard3value == 2 || playerCard3value == 3) {
				if (gameLogic.handTotal(bankerHand) <= 4) { // banker must draw
					assertTrue(gameLogic.evaluateBankerDraw(bankerHand, playerCard3));
				}
				else {
					assertFalse(gameLogic.evaluateBankerDraw(bankerHand, playerCard3));
				}
			}

			if (playerCard3value == 4 || playerCard3value == 5) {
				if (gameLogic.handTotal(bankerHand) <= 5) { // banker must draw
					assertTrue(gameLogic.evaluateBankerDraw(bankerHand, playerCard3));
				}
				else {
					assertFalse(gameLogic.evaluateBankerDraw(bankerHand, playerCard3));
				}
			}

			if (playerCard3value == 6 || playerCard3value == 7) {
				if (gameLogic.handTotal(bankerHand) <= 6) { // banker must draw
					assertTrue(gameLogic.evaluateBankerDraw(bankerHand, playerCard3));
				}
				else {
					assertFalse(gameLogic.evaluateBankerDraw(bankerHand, playerCard3));
				}
			}
		}
		else { // player did not draw a 3rd card
			if (gameLogic.handTotal(bankerHand) <= 5) { // banker must draw
				assertTrue(gameLogic.evaluateBankerDraw(bankerHand, null));
			}
			else {
				assertFalse(gameLogic.evaluateBankerDraw(bankerHand, null));
			}
		}

	}



	@RepeatedTest(5)
	void whoWonTest() {
		theDealer.shuffleDeck();
		ArrayList<Card> playerHand = theDealer.dealHand();
		ArrayList<Card> bankerHand = theDealer.dealHand();

		// determines who is winning at the start, both have 2 cards
		if (gameLogic.handTotal(playerHand) > gameLogic.handTotal(bankerHand)) {
			assertEquals("Player", gameLogic.whoWon(playerHand, bankerHand));
		}
		if (gameLogic.handTotal(bankerHand) > gameLogic.handTotal(playerHand)) {
			assertEquals("Banker", gameLogic.whoWon(playerHand, bankerHand));
		}
		if (gameLogic.handTotal(bankerHand) == gameLogic.handTotal(playerHand)) {
			assertEquals("Draw", gameLogic.whoWon(playerHand, bankerHand));
		}

		// evaluates if player and/or banker need a third card, then determines who won
		Card playerCard3 = null;
		Card bankerCard3;
		if (gameLogic.evaluatePlayerDraw(playerHand)) { // deal third to player
			playerCard3 = theDealer.drawOne();
			playerHand.add(playerCard3); //add card to the hand
		}
		if (gameLogic.evaluateBankerDraw(playerHand, playerCard3)) { // deal third to banker
			bankerCard3 = theDealer.drawOne();
			bankerHand.add(bankerCard3); // add card to the hand
		}

		if (gameLogic.handTotal(playerHand) > gameLogic.handTotal(bankerHand)) {
			assertEquals("Player", gameLogic.whoWon(playerHand, bankerHand));
		}
		if (gameLogic.handTotal(bankerHand) > gameLogic.handTotal(playerHand)) {
			assertEquals("Banker", gameLogic.whoWon(playerHand, bankerHand));
		}
		if (gameLogic.handTotal(bankerHand) == gameLogic.handTotal(playerHand)) {
			assertEquals("Draw", gameLogic.whoWon(playerHand, bankerHand));
		}

	}


}
