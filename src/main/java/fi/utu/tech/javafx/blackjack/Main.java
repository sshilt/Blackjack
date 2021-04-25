package fi.utu.tech.javafx.blackjack;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.util.Stack;

/**
 * The type Main.
 */
public class Main extends Application {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void start (Stage primaryStage) {
        primaryStage.setTitle("Blackjack");
        VBox vbox = new VBox(25);
        vbox.setAlignment(Pos.CENTER);
        Label title = new Label("Blackjack");
        title.setFont(new Font(30));

        Label label = new Label("Set starting balance (1-100,000)");
        label.setFont(new Font(15));
        TextField field = new TextField("100");

        field.setMaxWidth(75);
        field.setAlignment(Pos.CENTER);
        Button button = new Button("Confirm");

        vbox.getChildren().addAll(title, label, field, button);

        BorderPane root = new BorderPane();
        root.setCenter(vbox);

        // Opens a new window showing basic rules of the game
        MenuItem rulesItem = new MenuItem("Rules");
        rulesItem.setOnAction(actionEvent -> {
             final String text =
                    "Beat the dealer's score while not going over 21.\n" +
                    "Blackjack consists of 2 cards equaling a 21 score.\n" +
                    "Blackjack is worth more than other 21 score hands.\n\n" +
                    "Blackjack pays 3 to 2.\n" +
                    "Dealer hits on soft 17.";
            Label rulesLabel = new Label(text);
            rulesLabel.setFont(new Font(20));
            rulesLabel.setWrapText(true);
            StackPane pane = new StackPane();
            pane.getChildren().add(rulesLabel);
            Stage stage = new Stage();
            stage.setTitle("Rules");
            stage.setScene(new Scene(pane, 500, 300));
            stage.show();
        });

        // Closes the game
        MenuItem quitItem = new MenuItem("Quit game");
        quitItem.setOnAction(actionEvent -> {
            Platform.exit();
        });

        Menu menu = new Menu("Menu");
        menu.getItems().addAll(rulesItem, quitItem);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(menu);

        root.setTop(menuBar);

        button.setOnAction(actionEvent -> {
            if (field.getText().matches("[0-9]+")) {
                if (1 <= Integer.parseInt(field.getText()) && Integer.parseInt(field.getText()) <= 100000) {
                    double money = Integer.parseInt(field.getText());
                    new GameView(root, money).showGameView();
                }
            }
        });

        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
    }
}

