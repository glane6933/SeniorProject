package edu.wsu.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GameOverPane {
    public static void show(int score, int secondsElapsed, int win) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Game Over");
        window.setMinWidth(250);

        Label messageLabel;
        if(win == 1) {
            // Game was won
            messageLabel = new Label("You won!");
        } else {
            // Game was lost
            messageLabel = new Label("Game Over!");
        }
        Label scoreLabel = new Label("Score: " + score);
        Label timeLabel = new Label("Time Elapsed: " + secondsElapsed + " seconds");

        List<Label> labelList = new ArrayList<>();

        labelList.add(messageLabel);
        labelList.add(scoreLabel);
        labelList.add(timeLabel);

        for(Label label : labelList) {
            label.setFont(Font.font("Times New Roman Bold", 24));
        }

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(messageLabel, scoreLabel, timeLabel, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
