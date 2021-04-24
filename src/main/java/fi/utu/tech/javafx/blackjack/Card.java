package fi.utu.tech.javafx.blackjack;

import javafx.scene.image.Image;

import javafx.scene.image.ImageView;

public class Card {
    private Rank rank;
    private Suit suit;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Rank getRank() {
        return this.rank;
    }

    public Suit getSuit() {
        return this.suit;
    }

    // Returns an ImageView-node corresponding to the given card
    public ImageView getCardImage() {
        String imagePath = "images/" + this.rank.toString() + "_" + this.suit.toString() + ".png";
        Image cardImage = new Image(getClass().getResource(imagePath).toExternalForm(), 130, 200, true, false);

        return new ImageView(cardImage);
    }
}
