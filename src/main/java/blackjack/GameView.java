package blackjack;

import javafx.scene.layout.GridPane;

public class GameView {
    private GridPane gameView;
    private int money;

    public GameView(int money) {
        this.money = money;
    }
    GridPane pane = new GridPane();

    public GridPane getGameView() {
        System.out.println(money);
        return pane;
    }
}
