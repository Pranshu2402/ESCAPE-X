
package com.escapex;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GameplayScreen {

    private static final String BG = "#080f1b";
    private static final String PANEL = "#142438";
    private static final String ACCENT = "#00d9ff";
    private static final String MUTED = "#aabbd0";

    private final List<String> inventory = new ArrayList<>();

    private VBox inventoryList;
    private Label statusLabel;
    private Label progressLabel;
    private Label objectiveLabel;

    private boolean keyCollected = false;
    private boolean codeDiscovered = false;
    private boolean escaped = false;

    public void show(Stage stage) {

        inventory.clear();
        keyCollected = false;
        codeDiscovered = false;
        escaped = false;

        // TOP BAR
        Label logo = new Label("ESCAPE-X");
        logo.setStyle(
            "-fx-text-fill: " + ACCENT + ";" +
            "-fx-font-size: 27px;" +
            "-fx-font-weight: bold;"
        );

        Label level = new Label(
            "LEVEL 01   |   ABANDONED MANSION"
        );
        level.setStyle(
            "-fx-text-fill: white;" +
            "-fx-font-size: 15px;" +
            "-fx-font-weight: bold;"
        );

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button pause = button("Ⅱ   PAUSE");
        pause.setOnAction(e -> new PauseMenu().show(stage));

        HBox topBar = new HBox(
            25, logo, level, spacer, pause
        );
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(16, 25, 16, 25));
        topBar.setStyle(
            "-fx-background-color: #101e30;" +
            "-fx-border-color: #173c55;" +
            "-fx-border-width: 0 0 2 0;"
        );

        // OBJECTIVE PANEL
        Label objectiveTitle = new Label("MISSION OBJECTIVE");
        objectiveTitle.setStyle(
            "-fx-text-fill: " + ACCENT + ";" +
            "-fx-font-size: 17px;" +
            "-fx-font-weight: bold;"
        );

        objectiveLabel = new Label(
            "Explore the mansion, collect useful objects " +
            "and discover the way out."
        );
        objectiveLabel.setWrapText(true);
        objectiveLabel.setStyle(
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;"
        );

        VBox objective = new VBox(
            8, objectiveTitle, objectiveLabel
        );
        objective.setPadding(new Insets(15, 20, 15, 20));
        objective.setStyle(panelStyle());

        // ROOM IMAGE
        ImageView roomImage = createRoomImage();

        StackPane roomScene = new StackPane();
        roomScene.setMinSize(400, 300);
        roomScene.setStyle(
            "-fx-background-color: #091522;" +
            "-fx-border-color: #176080;" +
            "-fx-border-width: 2px;" +
            "-fx-background-radius: 9px;" +
            "-fx-border-radius: 9px;"
        );

        roomImage.fitWidthProperty().bind(
            roomScene.widthProperty()
        );
        roomImage.fitHeightProperty().bind(
            roomScene.heightProperty()
        );

        // INTERACTIVE HOTSPOTS
        AnchorPane hotspots = new AnchorPane();

        Button door = hotspot("ⓘ");
        Button painting = hotspot("ⓘ");
        Button fireplace = hotspot("ⓘ");
        Button desk = hotspot("ⓘ");

        AnchorPane.setLeftAnchor(door, 17.0);
        AnchorPane.setTopAnchor(door, 55.0);

        AnchorPane.setLeftAnchor(painting, 42.0);
        AnchorPane.setTopAnchor(painting, 37.0);

        AnchorPane.setLeftAnchor(fireplace, 52.0);
        AnchorPane.setBottomAnchor(fireplace, 24.0);

        AnchorPane.setLeftAnchor(desk, 67.0);
        AnchorPane.setBottomAnchor(desk, 25.0);

        door.setOnAction(e -> inspectDoor(stage));
        painting.setOnAction(e -> inspectPainting(stage));
        fireplace.setOnAction(e -> inspectFireplace(stage));
        desk.setOnAction(e -> inspectDesk(stage));

        hotspots.getChildren().addAll(
            door, painting, fireplace, desk
        );

        roomScene.getChildren().addAll(
            roomImage, hotspots
        );

        Label roomCaption = new Label(
            "ROOM 01  /  ABANDONED MANSION"
        );
        roomCaption.setStyle(
            "-fx-text-fill: " + ACCENT + ";" +
            "-fx-font-size: 12px;" +
            "-fx-font-weight: bold;"
        );

        VBox roomPanel = new VBox(
            12, roomCaption, roomScene
        );
        roomPanel.setPadding(new Insets(16));
        roomPanel.setStyle(panelStyle());
        VBox.setVgrow(roomScene, Priority.ALWAYS);

        // RIGHT SIDEBAR
        Label statusHeading = heading("MISSION STATUS");

        statusLabel = new Label("● IN PROGRESS");
        statusLabel.setStyle(
            "-fx-text-fill: #69e6a6;" +
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;"
        );

        progressLabel = new Label("0 / 2 OBJECTIVES");
        progressLabel.setStyle(
            "-fx-text-fill: " + MUTED + ";" +
            "-fx-font-size: 13px;"
        );

        ProgressBar progress = new ProgressBar(0);
        progress.setMaxWidth(Double.MAX_VALUE);
        progress.setStyle(
            "-fx-accent: " + ACCENT + ";"
        );

        Label inventoryHeading = heading("INVENTORY");

        inventoryList = new VBox(10);

        Label emptyInventory = new Label(
            "No items collected yet.\n\n" +
            "Explore the room to find objects."
        );
        emptyInventory.setWrapText(true);
        emptyInventory.setStyle(
            "-fx-text-fill: " + MUTED + ";" +
            "-fx-font-size: 13px;"
        );
        inventoryList.getChildren().add(emptyInventory);

        Label controlsHeading = heading("CONTROLS");

        Label controls = new Label(
            "• Click glowing icons to investigate\n\n" +
            "• Collect objects\n\n" +
            "• Discover hidden clues\n\n" +
            "• Unlock the exit"
        );
        controls.setWrapText(true);
        controls.setStyle(
            "-fx-text-fill: " + MUTED + ";" +
            "-fx-font-size: 12px;"
        );

        VBox sidebar = new VBox(
            15,
            statusHeading,
            statusLabel,
            progress,
            progressLabel,
            new Separator(),
            inventoryHeading,
            inventoryList,
            new Separator(),
            controlsHeading,
            controls
        );

        sidebar.setPadding(new Insets(20));
        sidebar.setPrefWidth(245);
        sidebar.setMinWidth(210);
        sidebar.setStyle(panelStyle());

        HBox gameArea = new HBox(
            16, roomPanel, sidebar
        );
        HBox.setHgrow(roomPanel, Priority.ALWAYS);
        VBox.setVgrow(roomPanel, Priority.ALWAYS);

        // BOTTOM BUTTONS
        Button hintButton = button("✦   GET A HINT");
        Button backButton = button("‹   BACK TO LEVELS");

        hintButton.setOnAction(e -> showHint(stage));

        backButton.setOnAction(e -> {
            new LevelSelection().show(stage);
        });

        HBox bottomBar = new HBox(
            15, hintButton, backButton
        );
        bottomBar.setAlignment(Pos.CENTER);
        bottomBar.setPadding(new Insets(12));

        // MAIN LAYOUT
        VBox center = new VBox(
            14, objective, gameArea
        );
        center.setPadding(new Insets(18));
        VBox.setVgrow(gameArea, Priority.ALWAYS);

        Label footer = new Label(
            "E S C A P E - X     •     THINK. SOLVE. ESCAPE."
        );
        footer.setStyle(
            "-fx-text-fill: #65839b;" +
            "-fx-font-size: 10px;"
        );

        HBox footerBar = new HBox(footer);
        footerBar.setAlignment(Pos.CENTER);
        footerBar.setPadding(new Insets(8));
        footerBar.setStyle(
            "-fx-background-color: #091522;"
        );

        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(center);
        root.setBottom(
            new VBox(bottomBar, footerBar)
        );
        root.setStyle(
            "-fx-background-color: " + BG + ";"
        );

        Scene scene = new Scene(root, 1250, 800);

        stage.setTitle("ESCAPE-X | Gameplay");
        stage.setScene(scene);
        stage.setWidth(1250);
        stage.setHeight(800);
        stage.setMinWidth(1000);
        stage.setMinHeight(680);
        stage.centerOnScreen();
        stage.show();

        updateProgress(progress);
    }

    // LOAD ROOM IMAGE
    private ImageView createRoomImage() {

        ImageView view = new ImageView();

        var imageUrl = getClass().getResource(
            "/images/mansion-room.png"
        );

        if (imageUrl != null) {
            view.setImage(new Image(
                imageUrl.toExternalForm()
            ));
        } else {
            System.out.println(
                "Mansion image missing! Check resources/images."
            );
        }

        view.setPreserveRatio(false);
        view.setSmooth(true);

        return view;
    }

    // DOOR INTERACTION
    private void inspectDoor(Stage stage) {

        if (escaped) return;

        if (keyCollected && codeDiscovered) {

            TextInputDialog dialog =
                new TextInputDialog();

            dialog.setTitle("ESCAPE-X");
            dialog.setHeaderText("THE FINAL EXIT");
            dialog.setContentText(
                "Enter the four-digit mansion code:"
            );

            dialog.showAndWait().ifPresent(code -> {

                if ("1842".equals(code.trim())) {
                    completeMission(stage);
                } else {
                    message(
                        "Incorrect code",
                        "The door remains locked. Try again."
                    );
                }
            });

        } else {
            message(
                "Locked Mansion Door",
                "The exit requires a brass key and a " +
                "four-digit security code."
            );
        }
    }

    // PAINTING INTERACTION
    private void inspectPainting(Stage stage) {

        message(
            "The Mysterious Painting",
            "An old inscription is written behind the frame:\n\n" +
            "\"The year the mansion was built shall open the way.\"\n\n" +
            "You discover the code: 1842."
        );

        if (!codeDiscovered) {
            codeDiscovered = true;
            addItem("Hidden Code: 1842");
            updateProgress(null);
        }
    }

    // FIREPLACE INTERACTION
    private void inspectFireplace(Stage stage) {

        message(
            "The Fireplace",
            "The fireplace is still warm.\n\n" +
            "You notice strange markings on the stone. " +
            "Someone may have hidden something nearby.\n\n" +
            "Perhaps the study desk holds a useful object."
        );
    }

    // DESK INTERACTION
    private void inspectDesk(Stage stage) {

        if (keyCollected) {
            message(
                "Old Desk",
                "You have already searched the desk. " +
                "Nothing else remains."
            );
            return;
        }

        message(
            "Brass Key Found!",
            "You open the desk drawer and discover " +
            "an old brass key.\n\n" +
            "This could unlock the mansion exit."
        );

        keyCollected = true;
        addItem("Brass Key");
        updateProgress(null);
    }

    // INVENTORY
    private void addItem(String item) {

        inventory.add(item);
        inventoryList.getChildren().clear();

        for (String collected : inventory) {

            Label itemLabel = new Label(
                "◆   " + collected
            );

            itemLabel.setWrapText(true);
            itemLabel.setStyle(
                "-fx-text-fill: #69e6a6;" +
                "-fx-font-size: 12px;" +
                "-fx-padding: 8px;" +
                "-fx-background-color: #203b46;" +
                "-fx-background-radius: 5px;"
            );

            inventoryList.getChildren().add(itemLabel);
        }
    }

    // PROGRESS
    private void updateProgress(ProgressBar progress) {

        int completed = 0;

        if (keyCollected) completed++;
        if (codeDiscovered) completed++;

        progressLabel.setText(
            completed + " / 2 OBJECTIVES"
        );

        if (progress != null) {
            progress.setProgress(completed / 2.0);
        }

        if (completed == 2) {
            objectiveLabel.setText(
                "All clues collected! Find the exit and enter code 1842."
            );
        }
    }

    // HINT SYSTEM
    private void showHint(Stage stage) {

        if (escaped) return;

        String hint;

        if (!keyCollected && !codeDiscovered) {
            hint = "Start by investigating the old desk " +
                   "and the mysterious painting.";
        } else if (!keyCollected) {
            hint = "Look carefully at the old desk. " +
                   "Something useful is hidden inside.";
        } else if (!codeDiscovered) {
            hint = "Inspect the mysterious painting. " +
                   "There is a clue behind its frame.";
        } else {
            hint = "You have everything you need. " +
                   "Click the mansion door and enter 1842.";
        }

        message("MISSION HINT", hint);
    }

    // MISSION COMPLETE
    private void completeMission(Stage stage) {

        escaped = true;

        statusLabel.setText("● MISSION COMPLETED");
        statusLabel.setStyle(
            "-fx-text-fill: #69e6a6;" +
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;"
        );

        Alert alert = new Alert(
            Alert.AlertType.INFORMATION
        );

        alert.setTitle("ESCAPE-X");
        alert.setHeaderText("MISSION ACCOMPLISHED!");
        alert.setContentText(
            "Congratulations!\n\n" +
            "You escaped the Abandoned Mansion.\n\n" +
            "All objectives completed."
        );

        ButtonType levelsButton = new ButtonType(
            "BACK TO LEVELS"
        );

        alert.getButtonTypes().setAll(
            levelsButton
        );

        alert.showAndWait();

        new LevelSelection().show(stage);
    }

    // REUSABLE BUTTON
    private Button button(String text) {

        Button button = new Button(text);

        button.setPrefHeight(46);
        button.setMinWidth(165);
        button.setStyle(
            "-fx-background-color: #142b40;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-border-color: " + ACCENT + ";" +
            "-fx-border-width: 1.5px;" +
            "-fx-border-radius: 7px;" +
            "-fx-background-radius: 7px;" +
            "-fx-cursor: hand;"
        );

        button.setOnMouseEntered(e -> {
            button.setStyle(
                "-fx-background-color: " + ACCENT + ";" +
                "-fx-text-fill: #07111e;" +
                "-fx-font-size: 13px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 7px;" +
                "-fx-border-radius: 7px;" +
                "-fx-cursor: hand;"
            );
        });

        button.setOnMouseExited(e -> {
            button.setStyle(
                "-fx-background-color: #142b40;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 13px;" +
                "-fx-font-weight: bold;" +
                "-fx-border-color: " + ACCENT + ";" +
                "-fx-border-width: 1.5px;" +
                "-fx-border-radius: 7px;" +
                "-fx-background-radius: 7px;" +
                "-fx-cursor: hand;"
            );
        });

        return button;
    }

    // INTERACTIVE ROOM HOTSPOTS
    private Button hotspot(String text) {

        Button button = new Button(text);

        button.setPrefSize(42, 42);
        button.setStyle(
            "-fx-background-color: rgba(0,217,255,0.2);" +
            "-fx-text-fill: " + ACCENT + ";" +
            "-fx-font-size: 21px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 30px;" +
            "-fx-border-color: " + ACCENT + ";" +
            "-fx-border-width: 2px;" +
            "-fx-border-radius: 30px;" +
            "-fx-cursor: hand;"
        );

        return button;
    }

    // REUSABLE PANEL STYLE
    private String panelStyle() {
        return
            "-fx-background-color: " + PANEL + ";" +
            "-fx-background-radius: 10px;" +
            "-fx-border-color: #29445c;" +
            "-fx-border-radius: 10px;";
    }

    private Label heading(String text) {

        Label label = new Label(text);

        label.setStyle(
            "-fx-text-fill: " + ACCENT + ";" +
            "-fx-font-size: 16px;" +
            "-fx-font-weight: bold;"
        );

        return label;
    }

    // MESSAGE DIALOG
    private void message(String title, String content) {

        Alert alert = new Alert(
            Alert.AlertType.INFORMATION
        );

        alert.setTitle("ESCAPE-X");
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

