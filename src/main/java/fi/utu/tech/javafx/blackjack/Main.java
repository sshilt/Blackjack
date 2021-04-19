package fi.utu.tech.javafx.blackjack;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start (Stage primaryStage) {
        primaryStage.setTitle("Blackjack");
        VBox vbox = new VBox(25);
        vbox.setAlignment(Pos.CENTER);
        Label label = new Label("Set starting balance (1-100,000)");
        TextField field = new TextField("100");

        field.setMaxWidth(100);
        Button button = new Button("Confirm");


        vbox.getChildren().addAll(label, field, button);

        BorderPane root = new BorderPane();
        root.setCenter(vbox);

        Menu menu1 = new Menu("File");
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(menu1);

        root.setTop(menuBar);

        button.setOnAction(actionEvent -> {
            int money = Integer.parseInt(field.getText());
            new GameView(money, root).showGameView();
        });

        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
    }

}

