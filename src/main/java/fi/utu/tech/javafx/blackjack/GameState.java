package fi.utu.tech.javafx.blackjack;


import java.util.ArrayList;

public class GameState {
    private int money;
    private Deck deck;
    private ArrayList<Card> dealerCards;
    private ArrayList<Card> playerCards;
    private int[] dealerScore;
    private int[] playerScore;

    public GameState(int money) {
        this.money = money;
        this.deck = new Deck();
        this.deck.shuffleDeck();
        this.dealerCards = new ArrayList<>();
        this.playerCards = new ArrayList<>();
        this.dealerScore = new int[]{0,0};
        this.playerScore = new int[]{0,0};
    }

    public void dealCard(boolean x) {
        Card card = deck.getDeck().get(0);
        deck.getDeck().remove(0);
        if (x) {
            dealerCards.add(card);
            return;
        }
        playerCards.add(card);
    }
    public int calculateResult() {
        int result = 0;

        return result;
    }
    public void addMoney(int amount) {
        this.money += amount;
    }
    public int getMoney() {
        return money;
    }
    public ArrayList<Card> getDealerCards() {
        return dealerCards;
    }
    public ArrayList<Card> getPlayerCards() {
        return playerCards;
    }
    public int[] getDealerScore() {
        dealerScore = new int[]{0,0};
        for (Card c : dealerCards) {
            if (c.getRank() == Rank.ACE) {
                dealerScore[0] = dealerScore[0] + 1;
                dealerScore[1] = dealerScore[1] + 11;
            }
            else {
                dealerScore[0] = dealerScore[0] + c.getRank().value;
                dealerScore[1] = dealerScore[1] + c.getRank().value;
            }
        }
        return dealerScore;
    }
    public int[] getPlayerScore() {
        playerScore = new int[]{0,0};
        for (Card c : playerCards) {
            if (c.getRank() == Rank.ACE) {
                playerScore[0] = playerScore[0] + 1;
                playerScore[1] = playerScore[1] + 11;
            }
            else {
                playerScore[0] = playerScore[0] + c.getRank().value;
                playerScore[1] = playerScore[1] + c.getRank().value;
            }
        }
        return playerScore;
    }
}
