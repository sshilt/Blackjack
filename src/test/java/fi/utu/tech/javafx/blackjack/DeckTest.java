package fi.utu.tech.javafx.blackjack;

import net.jqwik.api.*;

public class DeckTest {

    Deck deck = new Deck();

    // Tests the deck size created is correct
    @Property
    boolean deckSize() {
        return (deck.getDeck().size() == 52);
    }

    // Tests that the deck contains correct cards
    @Property
    boolean deckContainsCards() {
        int[][] list = new int[2][20];

        for (Card c : deck.getDeck()) {
            for (int i = 2; i < 12; i++) {
                if (c.getRank().getValue() == i) {
                    list[0][i]++;
                    break;
                }
            }
            if (c.getSuit() == Suit.SPADES) {
                list[1][0]++;

            } else if (c.getSuit() == Suit.CLUBS) {
                list[1][1]++;

            } else if (c.getSuit() == Suit.HEARTS) {
                list[1][2]++;

            } else if (c.getSuit() == Suit.DIAMONDS) {
                list[1][3]++;
            }
        }
        if (list[0][2] != 4) {
            return false;
        }
        if (list[1][0] != 13) {
            return false;
        }
        for (int i = 3; i < 10; i++) {
            if (list[0][2] != list[0][i]) {
                return false;
            }
        }
        for (int i = 1; i < 4; i++) {
            if (list[1][0] != list[1][i]) {
                return false;
            }
        }
        return true;
    }
}
