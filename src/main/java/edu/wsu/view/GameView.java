package edu.wsu.view;

import edu.wsu.App;
import edu.wsu.model.Sudoku;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.io.IOException;

public class GameView extends StackPane {

    private Label timeField;
    private Label scoreField;
    private final PausePane pausePane;
    private final GameOverPane gameOverPane;
    private final GraphicsContext gc;
    private final Canvas canvas;
    private boolean paused = false;

    public GameView() {
        super();
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-background-color: #add8e6;");

        canvas = new Canvas(Util.SCENE_WIDTH, Util.SCENE_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);

        GridPane playSpace = new GridPane();
        GridPane numberSelectionArea = new GridPane();
        this.getChildren().addAll(playSpace, numberSelectionArea);

        BorderPane hud = new BorderPane();
        GridPane scoreBox = new GridPane();
        GridPane timeBox = new GridPane();
        Label scoreLabel = new Label("Score:");
        Label timeLabel = new Label("Time:");
        scoreLabel.setFont(Font.font(Util.BASE_FONT, 20));
        timeLabel.setFont(Font.font(Util.BASE_FONT, 20));
        scoreField = new Label("1000");
        scoreField.setFont(Font.font(Util.BASE_FONT, 30));
        timeField = new Label("0000");
        timeField.setFont(Font.font(Util.BASE_FONT, 30));
        scoreBox.add(scoreField, 0, 1);
        scoreBox.setPadding(new Insets(15, 15, 15, 15));
        timeBox.add(timeField, 0, 1);
        timeBox.setPadding(new Insets(15, 15, 15, 15));
        hud.setTop(scoreBox);
        hud.setBottom(timeBox);
        this.getChildren().add(hud);

        gameOverPane = new GameOverPane(timeField, scoreField);
        pausePane = new PausePane();
    }

    public void setEventHandler(EventHandler<KeyEvent> e) {
        canvas.setOnKeyPressed(e);
        canvas.requestFocus();
    }

    public void swapToMainMenu(ActionEvent e) {
        try {
            FXMLLoader menuLoader = new FXMLLoader(App.class.getResource("fxml/menu.fxml"));
            Parent menuRoot = menuLoader.load();
            Util.getStage(e).setScene(new Scene(menuRoot));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void update(Sudoku sudoku) {
        wipeGC();
        drawScore(sudoku.getScore());
    }

    public void wipeGC() {
        gc.setFill(Color.rgb(0,0,0,0));
        gc.clearRect(0,0,getWidth(),getHeight());
        gc.fillRect(0,0,getWidth(),getHeight());
    }

    public void drawScore(int score) {
        scoreField.setText(String.format("%04d", score));
    }

    public void drawTime(int time) {
        timeField.setText(String.format("%04d", time));
    }

    public void end() {
        getChildren().add(gameOverPane);
    }

    public void playAgain() {
        getChildren().remove(gameOverPane);
    }

    public void togglePause() {
        if(paused) {
            paused = false;
            getChildren().remove(pausePane);
        } else {
            paused = true;
            getChildren().add(pausePane);
        }
    }

    public GameOverPane getGameOverPane() {
        return gameOverPane;
    }

    public PausePane getPausePane() {
        return pausePane;
    }
}