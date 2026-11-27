
package com.escapex;

import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LevelSelection {

    private static final String BG = "#080e18";
    private static final String PANEL = "#142337";
    private static final String CYAN = "#00d9ff";
    private static final String MUTED = "#aab8c9";

    public void show(Stage stage) {

        // HEADER

        Label eyebrow = new Label(
            "ESCAPE-X  /  MISSION DATABASE"
        );

        eyebrow.setStyle(
            "-fx-text-fill: " + CYAN + ";" +
            "-fx-font-size: 11px;" +
            "-fx-font-weight: bold;" +
            "-fx-letter-spacing: 3px;"
        );

        Label title = new Label(
            "SELECT YOUR MISSION"
        );

        title.setStyle(
            "-fx-text-fill: #f4f8ff;" +
            "-fx-font-size: 43px;" +
            "-fx-font-weight: 900;" +
            "-fx-effect: dropshadow(gaussian, #00d9ff55, 16, 0.3, 0, 0);"
        );

        Label subtitle = new Label(
            "THREE MISSIONS. ONE WAY OUT."
        );

        subtitle.setStyle(
            "-fx-text-fill: " + MUTED + ";" +
            "-fx-font-size: 14px;" +
            "-fx-letter-spacing: 3px;"
        );

        VBox header = new VBox(
            13,
            eyebrow,
            title,
            subtitle
        );

        header.setAlignment(Pos.CENTER);

        // MISSION CARDS

        VBox level1 = createLevelCard(
            stage,
            "01",
            "ABANDONED MANSION",
            "Explore a mysterious mansion and discover its hidden secrets.",
            "UNLOCKED",
            "MANSION",
            true
        );

        VBox level2 = createLevelCard(
            stage,
            "02",
            "SECRET LABORATORY",
            "Investigate a forgotten laboratory filled with dangerous experiments.",
            "COMING SOON",
            "LAB",
            false
        );

        VBox level3 = createLevelCard(
            stage,
            "03",
            "UNDERGROUND VAULT",
            "Enter a hidden underground vault and uncover its final mystery.",
            "COMING SOON",
            "VAULT",
            false
        );

        HBox cards = new HBox(
            24,
            level1,
            level2,
            level3
        );

        cards.setAlignment(Pos.CENTER);

        HBox.setHgrow(level1, Priority.ALWAYS);
        HBox.setHgrow(level2, Priority.ALWAYS);
        HBox.setHgrow(level3, Priority.ALWAYS);

        // BACK BUTTON

        Button backButton = createButton(
            "←    BACK TO MAIN MENU",
            false
        );

        backButton.setPrefWidth(280);

        backButton.setOnAction(e -> {
            new Main().start(stage);
        });

        // FOOTER

        Label footer = new Label(
            "ESCAPE-X   •   CHOOSE YOUR DESTINY"
        );

        footer.setStyle(
            "-fx-text-fill: #647b91;" +
            "-fx-font-size: 10px;" +
            "-fx-letter-spacing: 2px;"
        );

        // MAIN LAYOUT

        VBox layout = new VBox(
            42,
            header,
            cards,
            backButton,
            footer
        );

        layout.setAlignment(Pos.CENTER);
        layout.setPadding(
            new Insets(30, 45, 25, 45)
        );

        StackPane root = new StackPane(layout);

        root.setStyle(
            "-fx-background-color: " + BG + ";"
        );

        Scene scene = new Scene(
            root,
            1250,
            780
        );

        stage.setTitle(
            "ESCAPE-X | Select Mission"
        );

        stage.setScene(scene);
        stage.setWidth(1250);
        stage.setHeight(780);
        stage.setMinWidth(1100);
        stage.setMinHeight(700);
        stage.setResizable(true);
        stage.centerOnScreen();
        stage.show();
    }

    // CREATE MISSION CARD

    private VBox createLevelCard(
        Stage stage,
        String number,
        String name,
        String description,
        String status,
        String artwork,
        boolean unlocked
    ) {

        // IMAGE / ARTWORK AREA

        StackPane imagePanel = new StackPane();

        imagePanel.setPrefHeight(205);
        imagePanel.setMinHeight(205);
        imagePanel.setMaxWidth(Double.MAX_VALUE);

        imagePanel.setStyle(
            "-fx-background-color: #0b1928;" +
            "-fx-background-radius: 10px 10px 0 0;"
        );

        if (artwork.equals("MANSION")) {

            try {
                Image image = new Image(
                    getClass().getResourceAsStream(
                        "/images/mansion-background.jpg"
                    )
                );

                ImageView imageView = new ImageView(image);

                imageView.setFitWidth(350);
                imageView.setFitHeight(205);
                imageView.setPreserveRatio(false);
                imageView.setSmooth(true);

                imagePanel.getChildren().add(imageView);

            } catch (Exception ex) {
                addArtworkIcon(imagePanel, "⌂", "MANSION");
            }

        } else if (artwork.equals("LAB")) {

            addArtworkIcon(
                imagePanel,
                "⚗",
                "RESTRICTED RESEARCH FACILITY"
            );

        } else {

            addArtworkIcon(
                imagePanel,
                "◈",
                "CLASSIFIED VAULT"
            );
        }

        // LEVEL NUMBER

        Label levelNumber = new Label(
            "LEVEL " + number
        );

        levelNumber.setStyle(
            "-fx-text-fill: " + CYAN + ";" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-letter-spacing: 2px;"
        );

        // LEVEL NAME

        Label levelName = new Label(name);

        levelName.setWrapText(true);

        levelName.setMinHeight(55);

        levelName.setStyle(
            "-fx-text-fill: #f4f8ff;" +
            "-fx-font-size: 20px;" +
            "-fx-font-weight: bold;"
        );

        // DESCRIPTION

        Label levelDescription = new Label(
            description
        );

        levelDescription.setWrapText(true);

        levelDescription.setMinHeight(75);

        levelDescription.setStyle(
            "-fx-text-fill: " + MUTED + ";" +
            "-fx-font-size: 13px;" +
            "-fx-line-spacing: 4px;"
        );

        // STATUS

        Label statusLabel = new Label(
            (unlocked ? "●   " : "🔒   ") + status
        );

        statusLabel.setStyle(
            "-fx-text-fill: " +
            (unlocked ? "#00e6a0" : "#ff7474") + ";" +
            "-fx-font-size: 12px;" +
            "-fx-font-weight: bold;" +
            "-fx-letter-spacing: 1px;"
        );

        // ENTER BUTTON

        Button selectButton = createButton(
            unlocked ? "ENTER MISSION   →" : "MISSION LOCKED",
            unlocked
        );

        selectButton.setMaxWidth(Double.MAX_VALUE);

        selectButton.setDisable(!unlocked);

        if (unlocked) {
            selectButton.setOnAction(e ->
                new RoomScreen().show(stage)
            );
        }

        // CARD CONTENT

        VBox details = new VBox(
            15,
            levelNumber,
            levelName,
            levelDescription,
            statusLabel,
            selectButton
        );

        details.setPadding(
            new Insets(23)
        );

        details.setAlignment(
            Pos.CENTER_LEFT
        );

        VBox card = new VBox(
            0,
            imagePanel,
            details
        );

        card.setPrefWidth(350);
        card.setMinWidth(0);
        card.setMaxWidth(Double.MAX_VALUE);
        card.setMinHeight(510);

        card.setStyle(cardStyle(false, unlocked));

        // HOVER ANIMATION

        card.setOnMouseEntered(e -> {

            if (unlocked) {

                card.setStyle(
                    cardStyle(true, true)
                );

                ScaleTransition scale =
                    new ScaleTransition(
                        Duration.millis(160),
                        card
                    );

                scale.setToX(1.025);
                scale.setToY(1.025);
                scale.play();
            }
        });

        card.setOnMouseExited(e -> {

            card.setStyle(
                cardStyle(false, unlocked)
            );

            ScaleTransition scale =
                new ScaleTransition(
                    Duration.millis(160),
                    card
                );

            scale.setToX(1);
            scale.setToY(1);
            scale.play();
        });

        return card;
    }

    // PLACEHOLDER ARTWORK FOR LOCKED MISSIONS

    private void addArtworkIcon(
        StackPane panel,
        String symbol,
        String caption
    ) {

        Label icon = new Label(symbol);

        icon.setStyle(
            "-fx-text-fill: " + CYAN + ";" +
            "-fx-font-size: 75px;" +
            "-fx-font-weight: bold;" +
            "-fx-effect: dropshadow(gaussian, #00d9ff88, 22, 0.4, 0, 0);"
        );

        Label text = new Label(caption);

        text.setStyle(
            "-fx-text-fill: #8096aa;" +
            "-fx-font-size: 10px;" +
            "-fx-font-weight: bold;" +
            "-fx-letter-spacing: 2px;"
        );

        VBox content = new VBox(
            8,
            icon,
            text
        );

        content.setAlignment(Pos.CENTER);

        panel.getChildren().add(content);
    }

    // CARD STYLE

    private String cardStyle(
        boolean hover,
        boolean unlocked
    ) {

        String border = hover
            ? CYAN
            : "#30485f";

        return
            "-fx-background-color: " +
            (hover ? "#192d42" : PANEL) + ";" +
            "-fx-background-radius: 10px;" +
            "-fx-border-color: " + border + ";" +
            "-fx-border-width: " +
            (hover ? "2px" : "1px") + ";" +
            "-fx-border-radius: 10px;" +
            (hover
                ? "-fx-effect: dropshadow(gaussian, #00d9ff55, 20, 0.3, 0, 0);"
                : "");
    }

    // BUTTON DESIGN

    private Button createButton(
        String text,
        boolean primary
    ) {

        Button button = new Button(text);

        button.setPrefHeight(52);

        button.setPadding(
            new Insets(0, 18, 0, 18)
        );

        button.setStyle(
            buttonStyle(primary, false)
        );

        button.setOnMouseEntered(e -> {

            if (!button.isDisabled()) {
                button.setStyle(
                    buttonStyle(primary, true)
                );
            }
        });

        button.setOnMouseExited(e -> {

            if (!button.isDisabled()) {
                button.setStyle(
                    buttonStyle(primary, false)
                );
            }
        });

        return button;
    }

    private String buttonStyle(
        boolean primary,
        boolean hover
    ) {

        if (primary) {

            return
                "-fx-background-color: " +
                (hover ? "#75f4ff" : CYAN) + ";" +
                "-fx-text-fill: #07131e;" +
                "-fx-font-size: 13px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 8px;" +
                "-fx-border-color: " + CYAN + ";" +
                "-fx-border-radius: 8px;" +
                "-fx-effect: dropshadow(gaussian, #00d9ff66, 14, 0.3, 0, 0);" +
                "-fx-cursor: hand;";
        }

        return
            "-fx-background-color: #15283d;" +
            "-fx-text-fill: #edf6ff;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 8px;" +
            "-fx-border-color: " +
            (hover ? CYAN : "#36536c") + ";" +
            "-fx-border-radius: 8px;" +
            "-fx-cursor: hand;";
    }
}
