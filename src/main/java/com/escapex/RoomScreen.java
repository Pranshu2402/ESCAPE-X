package com.escapex;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RoomScreen {

    public void show(Stage stage) {

        try {

            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/GameRoom.fxml")
            );

            Parent root = loader.load();

            RoomController controller = loader.getController();
            controller.setStage(stage);

            Scene scene = new Scene(root, 1200, 780);

            stage.setTitle("ESCAPE-X | Five Themed Rooms");
            stage.setScene(scene);
            stage.setWidth(1200);
            stage.setHeight(780);
            stage.setResizable(true);
            stage.centerOnScreen();
            stage.show();

        } catch (IOException exception) {

            throw new RuntimeException(
                "Unable to load the room interface.",
                exception
            );
        }
    }
}
