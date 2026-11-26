
package com.escapex;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SettingsScreen {

    public void show(Stage stage, Runnable onBack) {

        Label title = new Label("SETTINGS");

        title.setStyle(
            "-fx-font-size: 40px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #00d9ff;"
        );

        Label soundLabel = new Label("SOUND VOLUME");
        soundLabel.setStyle(
            "-fx-text-fill: white;" +
            "-fx-font-size: 16px;"
        );

        Slider soundSlider = new Slider(0, 100, 70);
        soundSlider.setMaxWidth(300);
        soundSlider.setShowTickLabels(true);
        soundSlider.setShowTickMarks(true);

        Label musicLabel = new Label("MUSIC VOLUME");
        musicLabel.setStyle(
            "-fx-text-fill: white;" +
            "-fx-font-size: 16px;"
        );

        Slider musicSlider = new Slider(0, 100, 60);
        musicSlider.setMaxWidth(300);
        musicSlider.setShowTickLabels(true);
        musicSlider.setShowTickMarks(true);

        Button backButton = new Button("BACK");

        backButton.setPrefWidth(200);
        backButton.setPrefHeight(45);

        backButton.setStyle(
            "-fx-background-color: #202b3b;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;" +
            "-fx-border-color: #00d9ff;" +
            "-fx-border-radius: 5px;" +
            "-fx-background-radius: 5px;" +
            "-fx-cursor: hand;"
        );

        backButton.setOnAction(e -> onBack.run());

        VBox layout = new VBox(
            20,
            title,
            soundLabel,
            soundSlider,
            musicLabel,
            musicSlider,
            backButton
        );

        layout.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(layout);
        root.setStyle("-fx-background-color: #10131c;");

        Scene scene = new Scene(root, 1000, 650);

        stage.setTitle("ESCAPE-X | Settings");
        stage.setScene(scene);
        stage.show();
    }
}

