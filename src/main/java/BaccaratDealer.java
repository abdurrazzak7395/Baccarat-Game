import java.util.ArrayList;
import java.util.Collections;
public class BaccaratDealer {
    public ArrayList<Card> deck;

//    BaccaratDealer(ArrayList<Card> deck) {
//        this.deck = deck;
//    }



    // generates 52 unique cards, forming a standard deck (this is where we would add logic 0 = face 1 = ace)
    public void generateDeck() {
        deck = new ArrayList<Card>();
        // generates 13 hearts, 2 through Ace
        for (int i = 2; i < 15; i++) {
            String suit = "Hearts";
            int value = i;
            Card card = new Card(suit, value);
            deck.add(card);
        }
        // generates 13 diamonds, 2 through Ace
        for (int i = 2; i < 15; i++) {
            String suit = "Diamonds";
            int value = i;
            Card card = new Card(suit, value);
            deck.add(card);
        }
        // generates 13 spades, 2 through Ace
        for (int i = 2; i < 15; i++) {
            String suit = "Spades";
            int value = i;
            Card card = new Card(suit, value);
            deck.add(card);
        }
        // generates 13 clubs, 2 through Ace
        for (int i = 2; i < 15; i++) {
            String suit = "Clubs";
            int value = i;
            Card card = new Card(suit, value);
            deck.add(card);
        }
    }



    // deals two cards off the top of the deck
    public ArrayList<Card> dealHand() {
        //System.out.println(deck.size()); // should be 52 on first call
        Card card1 = drawOne(); // since start at 0, index of last is -1
        //System.out.println(deck.size()); // should be 51 on first call
        Card card2 = drawOne(); // since start at 0, index of last is -1

        // make arrayList of 2 cards "hand"
        ArrayList<Card> hand = new ArrayList<Card>();
        hand.add(card1);
        hand.add(card2);
        return hand;
    }



    // deals one card off the top of the deck
    public Card drawOne() {
        //System.out.println(deck.size()); // should be 52 on first call
        return deck.remove(deck.size() - 1); // since start at 0, index of last is -1
    }



    // shuffles the deck, randomizing its contents
    public void shuffleDeck() {
        generateDeck();
        Collections.shuffle(deck);
    }



    // returns the number of cards in the deck
    public int deckSize() {
        return deck.size();
    }

}
