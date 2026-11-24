
package com.escapex;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {

        Label welcome = new Label("WELCOME TO ESCAPE-X");

        welcome.setStyle(
            "-fx-font-size: 28px;" +
            "-fx-font-weight: bold;"
        );

        StackPane root = new StackPane(welcome);

        Scene scene = new Scene(root, 1000, 650);

        stage.setTitle("ESCAPE-X");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
