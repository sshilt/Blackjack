package fi.utu.tech.javafx.blackjack;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameView extends StackPane {
    private GameState gameState;
    private BorderPane pane;
    private BorderPane root;

    public GameView(BorderPane root, int money) {
        this.root = root;
        this.gameState = new GameState(money);
    }

    public void showGameView() {
        pane = new BorderPane();
        pane.setStyle("-fx-background-color: mintcream");

        VBox vbox = new VBox(5);
        Label dealerText = new Label("Dealer");
        dealerText.setFont(new Font(15));

        String dealerScoreText;
        if (gameState.getDealerScore()[0] == gameState.getDealerScore()[1]) {
            dealerScoreText = String.valueOf(gameState.getDealerScore()[0]);
        }
        else {
            dealerScoreText = gameState.getDealerScore()[0] + " / " + gameState.getDealerScore()[1];
        }
        Label dealerScoreLabel = new Label(dealerScoreText);

        HBox dealerCardsBox = new HBox(10);
        dealerCardsBox.setMinHeight(290);
        dealerCardsBox.setPadding(new Insets(0, 0, 100, 0));
        dealerCardsBox.setAlignment(Pos.CENTER);

        for (Card c : gameState.getDealerCards()) {
            dealerCardsBox.getChildren().add(c.getCardImage());
        }

        String playerScoreText;
        if (gameState.getPlayerScore()[0] == gameState.getPlayerScore()[1]) {
            playerScoreText = String.valueOf(gameState.getPlayerScore()[0]);
        }
        else {
            playerScoreText = gameState.getPlayerScore()[0] + " / " + gameState.getPlayerScore()[1];
        }
        Label playerScoreLabel = new Label(playerScoreText);

        HBox playerCardsBox = new HBox(10);
        playerCardsBox.setMinHeight(190);
        playerCardsBox.setAlignment(Pos.CENTER);

        for (Card c : gameState.getPlayerCards()) {
            playerCardsBox.getChildren().add(c.getCardImage());
        }

        HBox buttonsBox = new HBox(10);
        buttonsBox.setAlignment(Pos.CENTER);

        Button standButton = new Button("Stand");

        standButton.setOnAction(actionEvent -> {
            while (((gameState.getDealerScore()[0] < 18) && (gameState.getDealerScore()[1] > 21)) ||
                    ((gameState.getDealerScore()[0] < 17) && (gameState.getDealerScore()[1] < 17))) {
                gameState.dealCard(true);
            }
            int result = gameState.calculateResult();

            showGameView();
            root.setCenter(pane);
        });

        Button hitButton = new Button("Hit");

        hitButton.setOnAction(actionEvent -> {
            gameState.dealCard(false);
            showGameView();
            root.setCenter(pane);
        });

        Button doubleButton = new Button("Double");

        doubleButton.setOnAction(actionEvent -> {
            gameState = new GameState(gameState.getMoney());
            showGameView();
            root.setCenter(pane);
        });

        HBox bettingBox = new HBox(10);
        bettingBox.setAlignment(Pos.BASELINE_CENTER);

        TextField betAmountField = new TextField();
        betAmountField.setMaxWidth(100);

        Button betButton = new Button("Confirm");

        betButton.setOnAction(actionEvent -> {
            gameState.addMoney(-(Integer.parseInt(betAmountField.getText())));
            gameState.dealCard(true);
            gameState.dealCard(false);
            gameState.dealCard(false);
            showGameView();
            root.setCenter(pane);
        });
        Label moneyLabel = new Label("Total money: " + gameState.getMoney());
        bettingBox.getChildren().addAll(betAmountField, betButton, moneyLabel);

        buttonsBox.getChildren().addAll(standButton, hitButton, doubleButton);
        vbox.getChildren().addAll(dealerText, dealerScoreLabel, dealerCardsBox, playerScoreLabel, playerCardsBox, buttonsBox, bettingBox);
        vbox.setAlignment(Pos.TOP_CENTER);
        pane.setCenter(vbox);
        root.setCenter(pane);
    }
}
