package fi.utu.tech.javafx.blackjack;

import javafx.scene.image.Image;

import javafx.scene.image.ImageView;

/**
 * The type Card.
 */
public class Card {
    private Rank rank;
    private Suit suit;

    /**
     * Instantiates a new Card.
     *
     * @param rank the rank
     * @param suit the suit
     */
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    /**
     * Gets rank.
     *
     * @return the rank
     */
    public Rank getRank() {
        return this.rank;
    }

    /**
     * Gets suit.
     *
     * @return the suit
     */
    public Suit getSuit() {
        return this.suit;
    }

    /**
     * Gets card image.
     *
     * @return the card image
     */
// Returns an ImageView-node corresponding to the given card
    public ImageView getCardImage() {
        String imagePath = "images/" + this.rank.toString() + "_" + this.suit.toString() + ".png";
        Image cardImage = new Image(getClass().getResource(imagePath).toExternalForm(), 130, 200, true, false);

        return new ImageView(cardImage);
    }
}
