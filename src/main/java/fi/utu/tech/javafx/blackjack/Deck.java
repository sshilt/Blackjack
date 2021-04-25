package fi.utu.tech.javafx.blackjack;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The type Deck.
 */
public class Deck {
    private ArrayList<Card> deck;

    /**
     * Instantiates a new Deck.
     */
    public Deck() {
        deck = new ArrayList<>();
        for (Suit s : Suit.values()) {
            for (Rank r : Rank.values()) {
                deck.add(new Card(r, s));
            }
        }
    }

    /**
     * Gets deck.
     *
     * @return the deck
     */
    public ArrayList<Card> getDeck() {
        return deck;
    }

    /**
     * Shuffle deck.
     */
    public void shuffleDeck() {
        Collections.shuffle(deck);
    }

}
