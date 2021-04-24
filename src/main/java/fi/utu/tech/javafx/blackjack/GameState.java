package fi.utu.tech.javafx.blackjack;

import java.util.ArrayList;

public class GameState {
    private double money;
    private int betAmount;
    private boolean isBettingRound;
    private boolean isDoubleHidden;
    private String resultText;
    private Deck deck;
    private ArrayList<Card> dealerCards;
    private ArrayList<Card> playerCards;
    private int[] dealerScore;
    private int[] playerScore;

    public GameState(double money) {
        this.money = money;
        this.isBettingRound = true;
        this.isDoubleHidden = false;
        this.deck = new Deck();
        this.deck.shuffleDeck();
        this.dealerCards = new ArrayList<>();
        this.playerCards = new ArrayList<>();
        this.dealerScore = new int[]{0,0};
        this.playerScore = new int[]{0,0};
    }

    public void setIsBettingRound(boolean state) {
        isBettingRound = state;
    }
    public boolean getIsBettingRound() {
        return isBettingRound;
    }

    public void setIsDoubleHidden(boolean state) {
        isDoubleHidden = state;
    }
    public boolean getIsDoubleHidden() {
        return isDoubleHidden;
    }

    public void dealCard(boolean dealer) {
        Card card = deck.getDeck().get(0);
        deck.getDeck().remove(0);
        if (dealer) {
            dealerCards.add(card);
            if (card.getRank() == Rank.ACE) {
                dealerScore[0] += 1;
                dealerScore[1] += 11;
            }
            else {
                dealerScore[0] += card.getRank().value;
                dealerScore[1] += card.getRank().value;
            }
            return;
        }
        if (card.getRank() == Rank.ACE) {
            playerScore[0] += 1;
            playerScore[1] += 11;
        }
        else {
            playerScore[0] += card.getRank().value;
            playerScore[1] += card.getRank().value;
        }
        playerCards.add(card);
    }

    // 0: ignore, 1: blackjack, 2: push, 3: player wins, 4: dealer wins
    public int calculateResult() {

        // Checks if player has a blackjack in starting position. 2.5x paid if dealer doesn't also get a blackjack
        if (playerCards.size() == 2 && dealerCards.size() == 1) {
            if (playerScore[1] == 21) {
                dealCard(true); // Deals a card to the dealer
                if (dealerScore[1] == 21) {
                    money += betAmount;
                    return 2;
                }
                money += (2.5 * betAmount);
                return 1;
            }
        }
        else {
            // Double checks that the values are inbounds
            if (playerScore[0] < 22 || playerScore[1] < 22) {
                int dealer;
                int player;

                // Selects the highest player and dealer score value that is inbounds
                if (playerScore[1] < 22) {
                    player = playerScore[1];
                }
                else {
                    player = playerScore[0];
                }
                if (dealerScore[1] < 22) {
                    dealer = dealerScore[1];
                }
                else {
                    dealer = dealerScore[0];
                }

                // Push. Bet returned, unless dealer has blackjack while player has "non-blackjack" 21 score
                if (dealer == player && !(dealerScore[1] == 21 && dealerCards.size() == 2)) {
                    money += betAmount;
                    return 2;
                }
                // Player wins by higher inbounds score or by dealer busting. 2x bet paid
                else if ((dealer < player) || (dealerScore[0] > 21 && dealerScore[1] > 21)) {
                    money += (2 * betAmount);
                    return 3;
                }
                // Dealer wins
                else {
                    return 4;
                }
            }
        }
        // Incorrect function call if this is reached
        return 0;
    }

    public void makeBet(int amount, int multiplier) {
        this.money -= amount;
        this.betAmount = multiplier * amount;
    }
    public double getMoney() {
        return money;
    }
    public int getBetAmount() {
        return betAmount;
    }

    public void setResultText(String s) {
        resultText = s;
    }
    public String getResultText() {
        return resultText;
    }

    public ArrayList<Card> getDealerCards() {
        return dealerCards;
    }
    public ArrayList<Card> getPlayerCards() {
        return playerCards;
    }

    public int[] getDealerScore() {
        return dealerScore;
    }
    public int[] getPlayerScore() {
        return playerScore;
    }
}
