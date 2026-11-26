
package com.escapex;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class PauseMenu {

    public void show(Stage stage) {

        Label title = new Label("GAME PAUSED");

        title.setStyle(
            "-fx-font-size: 42px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #00d9ff;"
        );

        Label subtitle = new Label(
            "Take a breath. Your mission is waiting."
        );

        subtitle.setStyle(
            "-fx-font-size: 15px;" +
            "-fx-text-fill: #aab4c5;"
        );

        Button resumeButton = createButton("RESUME GAME");
        Button settingsButton = createButton("SETTINGS");
        Button levelsButton = createButton("LEVEL SELECTION");
        Button exitButton = createButton("EXIT GAME");

        resumeButton.setOnAction(e ->
            new GameplayScreen().show(stage)
        );

        settingsButton.setOnAction(e ->
            new SettingsScreen().show(stage, () -> show(stage))
        );

        levelsButton.setOnAction(e ->
            new LevelSelection().show(stage)
        );

        exitButton.setOnAction(e -> stage.close());

        VBox layout = new VBox(
            20,
            title,
            subtitle,
            resumeButton,
            settingsButton,
            levelsButton,
            exitButton
        );

        layout.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(layout);
        root.setStyle("-fx-background-color: #10131c;");

        Scene scene = new Scene(root, 1000, 650);

        stage.setTitle("ESCAPE-X | Paused");
        stage.setScene(scene);
        stage.show();
    }

    private Button createButton(String text) {

        Button button = new Button(text);

        button.setPrefWidth(280);
        button.setPrefHeight(48);

        button.setStyle(
            "-fx-background-color: #202b3b;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 6px;" +
            "-fx-border-color: #00d9ff;" +
            "-fx-border-radius: 6px;" +
            "-fx-cursor: hand;"
        );

        return button;
    }
}

