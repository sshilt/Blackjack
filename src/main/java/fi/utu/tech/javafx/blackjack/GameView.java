package fi.utu.tech.javafx.blackjack;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class GameView extends StackPane {
    BorderPane pane;
    BorderPane root;
    private int money;
    private int dealerScore;
    private int playerScore;
    private HBox dealerCards;
    private HBox playerCards;
    private Deck deck;

    public GameView(int money, BorderPane root) {
        this.money = money;
        this.root = root;
    }

    public void showGameView() {
        pane = new BorderPane();
        pane.setStyle("-fx-background-color: mintcream");

        VBox vbox = new VBox(5);
        Label dealerText = new Label("Dealer");
        dealerText.setFont(new Font(15));

        Label dealerScoreLabel = new Label(String.valueOf(dealerScore));

        HBox dealerCards = new HBox(10);
        dealerCards.setPadding(new Insets(0, 0, 100 ,0));
        dealerCards.setAlignment(Pos.CENTER);

        Deck deck = new Deck();
        deck.shuffleDeck();

        for (int i = 0; i < 2; i++) {
            dealerCards.getChildren().add(deck.getDeck().get(i).getCardImage());
        }

        Label playerScoreLabel = new Label(String.valueOf(playerScore));

        HBox playerCards = new HBox(10);
        playerCards.setAlignment(Pos.CENTER);

        for (int i = 2; i < 4; i++) {
            playerCards.getChildren().add(deck.getDeck().get(i).getCardImage());
        }
        Button button = new Button("Click me");

        button.setOnAction(actionEvent -> {
            pane.getChildren().remove(playerScoreLabel);
            showGameView();
            root.setCenter(pane);
        });

        vbox.getChildren().addAll(dealerText, dealerScoreLabel, dealerCards, playerScoreLabel, playerCards, button);
        vbox.setAlignment(Pos.TOP_CENTER);
        pane.setCenter(vbox);
        root.setCenter(pane);
    }
}
