
package com.escapex;

import java.net.URL;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class RoomController {

    @FXML private Label roomCounter;
    @FXML private Label roomNumber;
    @FXML private Label roomTitle;
    @FXML private Label roomDescription;
    @FXML private Label objectiveText;
    @FXML private Label visualCaption;

    @FXML private ImageView roomImage;
    @FXML private StackPane roomVisual;

    private Stage stage;
    private int currentRoom = 0;

    private static final String ACCENT = "#00d9ff";

    private final RoomData[] rooms = {

        new RoomData(
            "THE ABANDONED MANSION",
            "A forgotten mansion hides secrets behind every door.",
            "Explore the mansion and discover the truth behind its abandonment.",
            "Every shadow tells a story.",
            "/images/mansion-room.png",
            "#101c2b"
        ),

        new RoomData(
            "THE MYSTERY LIBRARY",
            "An ancient library filled with forgotten knowledge and hidden passages.",
            "Search the bookshelves and uncover the secret passage.",
            "Some stories should never be opened.",
            "/images/library.jpg",
            "#302016"
        ),

        new RoomData(
            "THE SECRET LABORATORY",
            "A mysterious laboratory contains the remains of a secret experiment.",
            "Investigate the equipment and uncover the laboratory's secret.",
            "The experiment was never completed.",
            "/images/laboratory.jpg",
            "#071d32"
        ),

        new RoomData(
            "THE FORGOTTEN PRISON",
            "An abandoned prison holds the secrets of those who disappeared.",
            "Explore the cells and discover a way through the prison.",
            "Not everyone who entered escaped.",
            "/images/prison.jpg",
            "#191a20"
        ),

        new RoomData(
            "THE UNDERGROUND VAULT",
            "An ancient vault protects the final mystery.",
            "Discover the final secret and find your way out.",
            "The final door is waiting.",
            "/images/vault.jpg",
            "#382713"
        )
    };

    @FXML
    public void initialize() {
        showRoom(0);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void showRoom(int index) {

        currentRoom = index;

        RoomData room = rooms[currentRoom];

        roomCounter.setText(
            String.format("ROOM %02d / 05", currentRoom + 1)
        );

        roomNumber.setText(
            String.format("CHAMBER %02d", currentRoom + 1)
        );

        roomTitle.setText(room.title);
        roomDescription.setText(room.description);
        objectiveText.setText(room.objective);
        visualCaption.setText(room.caption);

        roomVisual.setStyle(
            "-fx-background-color: " + room.background + ";" +
            "-fx-background-radius: 12px;" +
            "-fx-border-color: " + ACCENT + ";" +
            "-fx-border-radius: 12px;"
        );

        URL imageUrl = getClass().getResource(room.imagePath);

        if (imageUrl != null) {

            Image image = new Image(imageUrl.toExternalForm());

            roomImage.setImage(image);
            roomImage.setVisible(true);

        } else {

            roomImage.setImage(null);
            roomImage.setVisible(false);

            System.err.println(
                "Room image not found: " + room.imagePath
            );
        }
    }

    @FXML
    private void nextRoom() {

        if (currentRoom < rooms.length - 1) {
            showRoom(currentRoom + 1);
        } else {
            showRoom(0);
        }
    }

    @FXML
    private void previousRoom() {

        if (currentRoom > 0) {
            showRoom(currentRoom - 1);
        } else {
            showRoom(rooms.length - 1);
        }
    }

    @FXML
    private void backToMenu() {

        if (stage != null) {
            new Main().start(stage);
        }
    }

    private static class RoomData {

        final String title;
        final String description;
        final String objective;
        final String caption;
        final String imagePath;
        final String background;

        RoomData(
            String title,
            String description,
            String objective,
            String caption,
            String imagePath,
            String background
        ) {
            this.title = title;
            this.description = description;
            this.objective = objective;
            this.caption = caption;
            this.imagePath = imagePath;
            this.background = background;
        }
    }
}
