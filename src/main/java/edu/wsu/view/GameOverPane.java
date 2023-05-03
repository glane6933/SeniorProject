package edu.wsu.view;

import edu.wsu.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class GameOverPane {
    public static void show(int score, int seconds) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Game Over");

        Label scoreLabel = new Label("Score: " + score);
        Label timeLabel = new Label("Time taken: " + seconds + " seconds");
        Button menuButton = new Button("Back to main menu");
        menuButton.setOnAction(event -> {
            stage.close();
            FXMLLoader menuLoader = new FXMLLoader(App.class.getResource("fxml/menu.fxml"));
            Parent root;
            try {
                root = menuLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Util.getStage(event).setScene(new Scene(root));
        });
    }
}
