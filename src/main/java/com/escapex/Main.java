
package com.escapex;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    private static final String BG = "#080e18";
    private static final String PANEL = "#101d2b";
    private static final String CYAN = "#00d9ff";
    private static final String MUTED = "#aab8c9";

    @Override
    public void start(Stage stage) {

        Label eyebrow = new Label(
            "A STORY-DRIVEN ESCAPE EXPERIENCE"
        );

        eyebrow.setStyle(
            "-fx-text-fill: " + CYAN + ";" +
            "-fx-font-size: 11px;" +
            "-fx-font-weight: bold;" +
            "-fx-letter-spacing: 2.5px;"
        );

        Label title = new Label("ESCAPE-X");

        title.setStyle(
            "-fx-text-fill: #f4f8ff;" +
            "-fx-font-size: 58px;" +
            "-fx-font-weight: 900;" +
            "-fx-effect: dropshadow(gaussian, #00d9ff88, 18, 0.35, 0, 0);"
        );

        Label subtitle = new Label(
            "THINK.  SOLVE.  ESCAPE."
        );

        subtitle.setStyle(
            "-fx-text-fill: " + CYAN + ";" +
            "-fx-font-size: 15px;" +
            "-fx-font-weight: bold;" +
            "-fx-letter-spacing: 4px;"
        );

        Label description = new Label(
            "Enter the unknown. Solve the mystery.\n" +
            "Find your way out before time runs out."
        );

        description.setWrapText(true);

        description.setStyle(
            "-fx-text-fill: " + MUTED + ";" +
            "-fx-font-size: 15px;" +
            "-fx-line-spacing: 7px;"
        );

        // MENU BUTTONS

        Button play = createButton(
            "▶     PLAY GAME", true
        );

        Button continueButton = createButton(
            "◷     CONTINUE", false
        );

        Button settings = createButton(
            "⚙     SETTINGS", false
        );

        Button exit = createButton(
            "↗     EXIT GAME", false
        );

        play.setOnAction(e ->
            new LevelSelection().show(stage)
        );

        continueButton.setOnAction(e ->
            showInfo(
                "No saved game found",
                "Start a new mission using PLAY GAME."
            )
        );

        settings.setOnAction(e ->
            showSettings(stage)
        );

        exit.setOnAction(e ->
            stage.close()
        );

        VBox menu = new VBox(
            14,
            play,
            continueButton,
            settings,
            exit
        );

        menu.setAlignment(Pos.CENTER);
        menu.setMaxWidth(390);

        // BRANDING

        VBox branding = new VBox(
            23,
            eyebrow,
            title,
            subtitle,
            description,
            menu
        );

        branding.setAlignment(Pos.CENTER_LEFT);
        branding.setPadding(
            new Insets(55, 42, 45, 65)
        );

        branding.setMinWidth(470);
        branding.setPrefWidth(510);
        branding.setMaxWidth(540);

        branding.setStyle(
            "-fx-background-color: linear-gradient(to right, #080e18 82%, #0b1522);"
        );

        // MANSION IMAGE

        Image image = new Image(
            getClass().getResourceAsStream(
                "/images/mansion-background.jpg"
            )
        );

        ImageView mansion = new ImageView(image);

        mansion.setPreserveRatio(false);
        mansion.setSmooth(true);

        StackPane imagePanel = new StackPane(mansion);

        imagePanel.setMinWidth(0);
        imagePanel.setPrefWidth(760);

        mansion.fitWidthProperty().bind(
            imagePanel.widthProperty()
        );

        mansion.fitHeightProperty().bind(
            imagePanel.heightProperty()
        );

        Label imageCaption = new Label(
            "MISSION 01  /  THE ABANDONED MANSION"
        );

        imageCaption.setStyle(
            "-fx-text-fill: #e9f7ff;" +
            "-fx-font-size: 11px;" +
            "-fx-font-weight: bold;" +
            "-fx-letter-spacing: 2px;" +
            "-fx-background-color: #07111dcc;" +
            "-fx-padding: 12px 16px;"
        );

        StackPane.setAlignment(
            imageCaption, Pos.BOTTOM_LEFT
        );

        StackPane.setMargin(
            imageCaption, new Insets(0, 0, 24, 24)
        );

        imagePanel.getChildren().add(imageCaption);

        HBox content = new HBox(
            branding,
            imagePanel
        );

        HBox.setHgrow(
            imagePanel,
            Priority.ALWAYS
        );

        // FOOTER

        Label footer = new Label(
            "ESCAPE-X   •   THINK. SOLVE. ESCAPE."
        );

        footer.setStyle(
            "-fx-text-fill: #71849a;" +
            "-fx-font-size: 10px;" +
            "-fx-letter-spacing: 2px;"
        );

        BorderPane root = new BorderPane(content);

        root.setBottom(footer);

        BorderPane.setAlignment(
            footer, Pos.CENTER_LEFT
        );

        BorderPane.setMargin(
            footer, new Insets(12, 0, 12, 65)
        );

        root.setStyle(
            "-fx-background-color: " + BG + ";"
        );

        Scene scene = new Scene(root, 1280, 760);

        stage.setTitle("ESCAPE-X | Main Menu");
        stage.setScene(scene);
        stage.setMinWidth(1050);
        stage.setMinHeight(650);
        stage.setWidth(1280);
        stage.setHeight(760);
        stage.setResizable(true);
        stage.centerOnScreen();
        stage.show();
    }

    // PREMIUM BUTTON CREATION

    private Button createButton(
        String text,
        boolean primary
    ) {

        Button button = new Button(text);

        button.setMaxWidth(Double.MAX_VALUE);
        button.setPrefHeight(58);
        button.setMinHeight(58);

        button.setAlignment(Pos.CENTER_LEFT);

        button.setPadding(
            new Insets(0, 25, 0, 25)
        );

        button.setFocusTraversable(true);

        button.setStyle(
            buttonStyle(primary, "normal")
        );

        // HOVER EFFECT

        button.setOnMouseEntered(e -> {
            button.setStyle(
                buttonStyle(primary, "hover")
            );
        });

        button.setOnMouseExited(e -> {
            button.setStyle(
                buttonStyle(primary, "normal")
            );
        });

        // PRESS EFFECT

        button.setOnMousePressed(e -> {
            button.setStyle(
                buttonStyle(primary, "pressed")
            );
        });

        button.setOnMouseReleased(e -> {
            button.setStyle(
                buttonStyle(primary,
                    button.isHover() ? "hover" : "normal"
                )
            );
        });

        return button;
    }

    // BUTTON VISUAL STYLES

    private String buttonStyle(
        boolean primary,
        String state
    ) {

        if (primary) {

            if (state.equals("pressed")) {
                return
                    "-fx-background-color: #009dbb;" +
                    "-fx-text-fill: #06131c;" +
                    "-fx-font-size: 15px;" +
                    "-fx-font-weight: bold;" +
                    "-fx-background-radius: 9px;" +
                    "-fx-border-color: #78f3ff;" +
                    "-fx-border-width: 1px;" +
                    "-fx-border-radius: 9px;";
            }

            if (state.equals("hover")) {
                return
                    "-fx-background-color: linear-gradient(to right, #72f4ff, #00d9ff);" +
                    "-fx-text-fill: #06131c;" +
                    "-fx-font-size: 15px;" +
                    "-fx-font-weight: bold;" +
                    "-fx-background-radius: 9px;" +
                    "-fx-border-color: white;" +
                    "-fx-border-width: 1.5px;" +
                    "-fx-border-radius: 9px;" +
                    "-fx-effect: dropshadow(gaussian, #00d9ffbb, 24, 0.5, 0, 0);";
            }

            return
                "-fx-background-color: linear-gradient(to right, #00e5ff, #00bddd);" +
                "-fx-text-fill: #06131c;" +
                "-fx-font-size: 15px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 9px;" +
                "-fx-border-color: #75f5ff;" +
                "-fx-border-width: 1px;" +
                "-fx-border-radius: 9px;" +
                "-fx-effect: dropshadow(gaussian, #00d9ff66, 16, 0.3, 0, 0);" +
                "-fx-cursor: hand;";
        }

        if (state.equals("pressed")) {
            return
                "-fx-background-color: #17394d;" +
                "-fx-text-fill: #00d9ff;" +
                "-fx-font-size: 15px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 9px;" +
                "-fx-border-color: #00d9ff;" +
                "-fx-border-width: 2px;" +
                "-fx-border-radius: 9px;";
        }

        if (state.equals("hover")) {
            return
                "-fx-background-color: linear-gradient(to right, #1c3e55, #122c40);" +
                "-fx-text-fill: #00e5ff;" +
                "-fx-font-size: 15px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 9px;" +
                "-fx-border-color: #00d9ff;" +
                "-fx-border-width: 1.5px;" +
                "-fx-border-radius: 9px;" +
                "-fx-effect: dropshadow(gaussian, #00d9ff55, 15, 0.3, 0, 0);" +
                "-fx-cursor: hand;";
        }

        return
            "-fx-background-color: linear-gradient(to right, #15283d, #101e30);" +
            "-fx-text-fill: #edf6ff;" +
            "-fx-font-size: 15px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 9px;" +
            "-fx-border-color: #34536c;" +
            "-fx-border-width: 1px 1px 1px 4px;" +
            "-fx-border-radius: 9px;" +
            "-fx-cursor: hand;";
    }

    // SETTINGS WINDOW

    private void showSettings(Stage owner) {

        Stage dialog = new Stage();

        dialog.initOwner(owner);
        dialog.setTitle("ESCAPE-X | Settings");

        Label heading = new Label("GAME SETTINGS");

        heading.setStyle(
            "-fx-text-fill: " + CYAN + ";" +
            "-fx-font-size: 24px;" +
            "-fx-font-weight: bold;"
        );

        Label volumeLabel = new Label("MASTER VOLUME");
        volumeLabel.setTextFill(Color.WHITE);

        Slider volume = new Slider(0, 100, 70);
        volume.setShowTickLabels(true);

        Label brightnessLabel = new Label("BRIGHTNESS");
        brightnessLabel.setTextFill(Color.WHITE);

        Slider brightness = new Slider(0, 100, 75);
        brightness.setShowTickLabels(true);

        CheckBox fullscreen = new CheckBox(
            "Fullscreen mode"
        );

        fullscreen.setTextFill(Color.WHITE);

        fullscreen.setSelected(
            owner.isFullScreen()
        );

        fullscreen.setOnAction(e ->
            owner.setFullScreen(
                fullscreen.isSelected()
            )
        );

        Button back = createButton("←   BACK", false);

        back.setOnAction(e ->
            dialog.close()
        );

        VBox layout = new VBox(
            20,
            heading,
            volumeLabel,
            volume,
            brightnessLabel,
            brightness,
            fullscreen,
            back
        );

        layout.setPadding(new Insets(32));
        layout.setAlignment(Pos.CENTER_LEFT);

        layout.setStyle(
            "-fx-background-color: " + PANEL + ";"
        );

        dialog.setScene(
            new Scene(layout, 410, 390)
        );

        dialog.setResizable(false);
        dialog.showAndWait();
    }

    private void showInfo(
        String header,
        String message
    ) {

        Alert alert = new Alert(
            Alert.AlertType.INFORMATION
        );

        alert.setTitle("ESCAPE-X");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

