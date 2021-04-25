package fi.utu.tech.javafx.blackjack;

import java.util.ArrayList;

/**
 * The type Game state.
 */
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

    /**
     * Instantiates a new Game state.
     *
     * @param money the money
     */
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

    /**
     * Sets betting round state.
     *
     * @param state the state
     */
    public void setIsBettingRound(boolean state) {
        isBettingRound = state;
    }

    /**
     * Gets betting round state.
     *
     * @return the is betting round state
     */
    public boolean getIsBettingRound() {
        return isBettingRound;
    }

    /**
     * Sets double hidden state.
     *
     * @param state the state
     */
    public void setIsDoubleHidden(boolean state) {
        isDoubleHidden = state;
    }

    /**
     * Gets double hidden state.
     *
     * @return the is double hidden -state
     */
    public boolean getIsDoubleHidden() {
        return isDoubleHidden;
    }

    /**
     * Deal card.
     *
     * @param dealer the dealer
     */
    public void dealCard(boolean dealer) {
        Card card = deck.getDeck().get(0);
        deck.getDeck().remove(0);
        if (dealer) {
            dealerCards.add(card);
            // Increase dealer score
            if (card.getRank() == Rank.ACE) {
                dealerScore[0] += 1;
                dealerScore[1] += 11;
            }
            else {
                dealerScore[0] += card.getRank().getValue();
                dealerScore[1] += card.getRank().getValue();
            }
            return;
        }
        playerCards.add(card);
        // Increase player score
        if (card.getRank() == Rank.ACE) {
            playerScore[0] += 1;
            playerScore[1] += 11;
        }
        else {
            playerScore[0] += card.getRank().getValue();
            playerScore[1] += card.getRank().getValue();
        }
    }

    /**
     * Calculate result int.
     *
     * @return the int
     * 0: ignore, 1: blackjack, 2: push, 3: player wins, 4: dealer wins
     */
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

    /**
     * Make bet.
     *
     * @param amount     the amount
     * @param multiplier the multiplier
     */
    public void makeBet(int amount, int multiplier) {
        this.money -= amount;
        this.betAmount = multiplier * amount;
    }

    /**
     * Gets money.
     *
     * @return the money
     */
    public double getMoney() {
        return money;
    }

    /**
     * Gets bet amount.
     *
     * @return the bet amount
     */
    public int getBetAmount() {
        return betAmount;
    }

    /**
     * Sets result text.
     *
     * @param s the s
     */
    public void setResultText(String s) {
        resultText = s;
    }

    /**
     * Gets result text.
     *
     * @return the result text
     */
    public String getResultText() {
        return resultText;
    }

    /**
     * Gets dealer cards.
     *
     * @return the dealer cards
     */
    public ArrayList<Card> getDealerCards() {
        return dealerCards;
    }

    /**
     * Gets player cards.
     *
     * @return the player cards
     */
    public ArrayList<Card> getPlayerCards() {
        return playerCards;
    }

    /**
     * Get dealer score int [ ].
     *
     * @return the int [ ]
     */
    public int[] getDealerScore() {
        return dealerScore;
    }

    /**
     * Get player score int [ ].
     *
     * @return the int [ ]
     */
    public int[] getPlayerScore() {
        return playerScore;
    }
}
