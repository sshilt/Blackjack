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

/**
 * The type Game view.
 */
public class GameView extends StackPane {
    private GameState gameState;
    private BorderPane root;
    private BorderPane pane;

    /**
     * Instantiates a new Game view.
     *
     * @param root  the root
     * @param money the money
     */
    public GameView(BorderPane root, double money) {
        this.root = root;
        this.gameState = new GameState(money);
        // Holds the game's visual nodes (score labels, cards etc.)
        pane = new BorderPane();
        pane.setStyle("-fx-background-color: mintcream");
    }

    /**
     * Show game view.
     */
    public void showGameView() {
        VBox vbox = new VBox(5);
        Label dealerLabel = new Label("Dealer");
        dealerLabel.setFont(new Font(15));

        // Sets label text according to current score. If the hand contains an ace, displays two possible values
        // if both are lower than 22
        String dealerScoreText;
        if ((gameState.getDealerScore()[0] == gameState.getDealerScore()[1]) || (gameState.getDealerScore()[1] > 21)) {
            dealerScoreText = String.valueOf(gameState.getDealerScore()[0]);
        }
        else {
            dealerScoreText = gameState.getDealerScore()[0] + " / " + gameState.getDealerScore()[1];
        }
        Label dealerScoreLabel = new Label(dealerScoreText);

        HBox dealerCardsBox = new HBox(10);
        dealerCardsBox.setMinHeight(190);
        dealerCardsBox.setAlignment(Pos.CENTER);

        for (Card c : gameState.getDealerCards()) {
            dealerCardsBox.getChildren().add(c.getCardImage());
        }

        Label resultLabel = new Label(gameState.getResultText());
        resultLabel.setFont(new Font(20));
        resultLabel.setAlignment(Pos.CENTER);
        resultLabel.setPadding(new Insets(20, 0, 20, 0));

        String playerScoreText;
        if ((gameState.getPlayerScore()[0] == gameState.getPlayerScore()[1]) || (gameState.getPlayerScore()[1] > 21)) {
            playerScoreText = String.valueOf(gameState.getPlayerScore()[0]);
        }
        else {
            playerScoreText = gameState.getPlayerScore()[0] + " / " + gameState.getPlayerScore()[1];
        }
        Label playerScoreLabel = new Label(playerScoreText);

        HBox playerCardsBox = new HBox(10);
        playerCardsBox.setMinHeight(190);
        playerCardsBox.setAlignment(Pos.CENTER);
        //
        for (Card c : gameState.getPlayerCards()) {
            playerCardsBox.getChildren().add(c.getCardImage());
        }

        Label totalBetLabel = new Label("Total bet: " + gameState.getBetAmount());

        HBox buttonsBox = new HBox(10);
        buttonsBox.setPadding(new Insets(10,0,0,0));
        buttonsBox.setAlignment(Pos.CENTER);

        Button standButton = new Button("Stand");
        standButton.setPrefWidth(70);
        standButton.setOnAction(actionEvent -> {
            // Deal cards to dealer until he gets a soft 18+ score or a hard 17+ score (dealer hits on soft 17)
            while (((gameState.getDealerScore()[0] < 18) && (gameState.getDealerScore()[1] > 21)) ||
                    ((gameState.getDealerScore()[0] < 17) && (gameState.getDealerScore()[1] < 17))) {
                gameState.dealCard(true);
            }
            int result = gameState.calculateResult();
            if (result == 2) {
                gameState.setIsBettingRound(true);
                gameState.setResultText("Push. Your bet has been refunded.");
            }
            else if (result == 3) {
                gameState.setIsBettingRound(true);
                gameState.setResultText("You win " + (2 * gameState.getBetAmount()) + "!");
            }
            else if (result == 4) {
                gameState.setIsBettingRound(true);
                gameState.setResultText("Dealer wins. Better luck next time!");
            }
            showGameView();
        });

        // Deals the player one more card
        Button hitButton = new Button("Hit");
        hitButton.setPrefWidth(70);
        hitButton.setOnAction(actionEvent -> {
            gameState.setIsDoubleHidden(true);
            gameState.dealCard(false);
            if (gameState.getPlayerScore()[0] > 21 && gameState.getPlayerScore()[1] > 21) {
                gameState.setIsBettingRound(true);
                gameState.setResultText("You bust. Better luck next time!");
            }
            // Automatically trigger standButton event if player reaches a score of 21
            else if (gameState.getPlayerScore()[1] == 21) {
                standButton.fire();
            }
            showGameView();
        });

        Button doubleButton = new Button("Double");
        doubleButton.setPrefWidth(70);
        // Hides the doubleButton after hitButton has been pushed
        if (gameState.getIsDoubleHidden()) {
            doubleButton.setDisable(true);
        }
        // Doubles the bet amount and deals one more card to player after which fires standButton to calculate result
        doubleButton.setOnAction(actionEvent -> {
            gameState.makeBet(gameState.getBetAmount(), 2);
            gameState.dealCard(false);
            if (gameState.getPlayerScore()[0] > 21 && gameState.getPlayerScore()[1] > 21) {
                gameState.setIsBettingRound(true);
                gameState.setResultText("You bust. Better luck next time!");
                showGameView();
            }
            else {
                standButton.fire();
            }
        });

        // Hides the decision round buttons for betting round
        if (gameState.getIsBettingRound()) {
            standButton.setDisable(true);
            hitButton.setDisable(true);
            doubleButton.setDisable(true);
        }
        buttonsBox.getChildren().addAll(standButton, hitButton, doubleButton);

        HBox bettingBox = new HBox(10);
        bettingBox.setAlignment(Pos.CENTER);

        TextField betAmountField = new TextField("1");
        betAmountField.setPrefWidth(80);
        betAmountField.setAlignment(Pos.CENTER);

        // Accepts the bet if input is valid and starts the decision round
        Button betButton = new Button("Confirm bet");
        betButton.setPrefWidth(80);
        betButton.setOnAction(actionEvent -> {
            if (betAmountField.getText().matches("[0-9]+")) {
                if (1 <= Integer.parseInt(betAmountField.getText()) && Integer.parseInt(betAmountField.getText()) <= gameState.getMoney()) {
                    gameState = new GameState(this.gameState.getMoney());
                    gameState.makeBet(Integer.parseInt(betAmountField.getText()), 1);

                    // Deals one card for dealer, 2 for player
                    gameState.dealCard(true);
                    gameState.dealCard(false);
                    gameState.dealCard(false);

                    // Sets decision round on
                    gameState.setIsBettingRound(false);

                    // Checks for blackjack and skips to betting round if player gets blackjack
                    int result = gameState.calculateResult();
                    if (result == 1) {
                        gameState.setIsBettingRound(true);
                        gameState.setResultText("Blackjack! You win " + 2.5 * gameState.getBetAmount() + "!");
                    } else if (result == 2) {
                        gameState.setIsBettingRound(true);
                        gameState.setResultText("Push. Your bet has been refunded.");
                    }
                    showGameView();
                }
            }
        });

        // Hides betting fields for decision round
        if (!gameState.getIsBettingRound()) {
            betAmountField.setDisable(true);
            betButton.setDisable(true);
            // Doubling is disabled if player doesn't have enough money
            if (gameState.getMoney() < gameState.getBetAmount()) {
                doubleButton.setDisable(true);
            }
        }

        bettingBox.setPadding(new Insets(10,0,10,0));
        bettingBox.getChildren().addAll(betAmountField, betButton);

        Label moneyLabel = new Label("Total money: " + gameState.getMoney());
        moneyLabel.setFont(new Font(15));
        moneyLabel.setAlignment(Pos.CENTER);

        vbox.getChildren().addAll(dealerLabel, dealerScoreLabel, dealerCardsBox, resultLabel, playerScoreLabel, playerCardsBox, totalBetLabel, buttonsBox, bettingBox, moneyLabel);
        vbox.setAlignment(Pos.TOP_CENTER);
        pane.setCenter(vbox);
        root.setCenter(pane);
    }
}
