package edu.wsu.controller;

import edu.wsu.App;
import edu.wsu.model.Difficulty;
import edu.wsu.model.GameState;
import edu.wsu.model.Sudoku;
import edu.wsu.view.GameOverPane;
import edu.wsu.view.Util;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameController {
    public Label attemptNumberLabel;
    private Sudoku sudoku;
    private List<List<Label>> labelGrid;
    private DatabaseController db;
    private boolean paused;
    private int score;
    private long startTime;
    private int secondsElapsed;
    private int attempts;
    private double scoreDeduction;
    private Timeline timeline;
    @FXML
    private StackPane gameRoot;
    @FXML
    private GridPane board;
    @FXML
    private GridPane numberSelection;
    @FXML
    private Label timeNumberLabel;
    @FXML
    private Label scoreNumberLabel;
    private Pane pauseOverlay;

    public GameController() {
        sudoku = Sudoku.getInstance();
        labelGrid = new ArrayList<>();
    }

    public void setDatabaseController(DatabaseController db) {
        this.db = db;
    }

    public int attemptsFromDifficulty() {
        if(sudoku.getDifficulty() == Difficulty.EASY) {
            return 5;
        } else if(sudoku.getDifficulty() == Difficulty.MEDIUM) {
            return 4;
        } else {
            return 5;
        }
    }

    public void start() {
        sudoku.startNewGame();
        attempts = attemptsFromDifficulty();
        startTime = System.currentTimeMillis() / 1000;
        paused = false;
        pauseOverlay = createPauseOverlay();
        setLabels();
        startTimeline();
        setScoreDeduction();
    }

    private void setScoreDeduction() {
        if(sudoku.getDifficulty() == Difficulty.EASY) {
            scoreDeduction = 4;
            // each square is 100 points, 100 / 4 = 25 points
        } else if(sudoku.getDifficulty() == Difficulty.MEDIUM) {
            scoreDeduction = 2;
            // each square is 100 points, 100 / 2 = 50 points
        } else {
            scoreDeduction = 1;
            // each square is 100 points, 100 / 1 = 100 points
        }
    }

    private void startTimeline() {
        secondsElapsed = 0;
        updateHudLabels();

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if(sudoku.state == GameState.COMPLETED) {
                stopTimeline();
            } else {
                secondsElapsed++;
                updateLabels();
                updateHudLabels();
            }
            gameRoot.getScene().addEventHandler(KeyEvent.KEY_PRESSED, e -> {
                if (e.getCode() == KeyCode.ESCAPE) {
                    if(!paused) {
                        timeline.pause();
                        sudoku.togglePause();
                        gameRoot.getChildren().add(pauseOverlay);
                        paused = true;
                    } else {
                        timeline.play();
                        sudoku.togglePause();
                        gameRoot.getChildren().remove(pauseOverlay);
                        paused = false;
                    }
                }
            });
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private Pane createPauseOverlay() {
        StackPane overlay = new StackPane();
        overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");

        Label pausedLabel = new Label("Game Paused");
        pausedLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");

        Label unpauseLabel = new Label("Press Escape to Un-pause");
        unpauseLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(pausedLabel, unpauseLabel);
        vbox.setAlignment(Pos.CENTER);

        overlay.getChildren().add(vbox);
        overlay.setPrefSize(gameRoot.getWidth(), gameRoot.getHeight());

        return overlay;
    }

    private void updateNumberSelection() {
        for(int i = 0; i < 9; i++) {
            Label label = (Label) numberSelection.lookup("#number" + (i+1));
            int choice = i + 1;
            label.setOnMouseClicked(event -> sudoku.setChoice(choice));
        }
    }

    private void updateLabels() {
        updateNumberSelection();
        for(int i = 0; i < 9; i++) {
            List<Label> row = new ArrayList<>();
            for(int j = 0; j < 9; j++) {
                Label label = (Label) board.lookup("#cell" + i + j);
                if(sudoku.getPuzzle()[i][j] == sudoku.getSolution()[i][j]) {
                    label.setOnMouseClicked(null);
                } else {
                    int colIdx = j;
                    int rowIdx = i;
                    label.setOnMouseClicked(event -> {
                        if(sudoku.state == GameState.COMPLETED) {
                            return;
                        }
                        int choice = sudoku.getChoice();
                        if(choice != 0) {
                            label.setText(Integer.toString(choice));
                            sudoku.setNumber(colIdx, rowIdx, choice);
                            if(choice != sudoku.getSolutionNumber(colIdx, rowIdx)) {
                                attempts--;
                                label.setBackground(Background.fill(Color.LIGHTCORAL));
                                if(attempts == 0) {
                                    sudoku.endGame();
                                    updateHudLabels();
                                    GameOverPane.show(score, secondsElapsed, 2);
                                }
                            } else {
                                label.setBackground(Background.fill(Color.LIGHTGREEN));
                                score += 100 / scoreDeduction;
                                if(sudoku.checkIfGameOver()) {
                                    sudoku.endGame();
                                    db.changeHighScore(score);
                                    updateHudLabels();
                                    GameOverPane.show(score, secondsElapsed, 1);
                                }
                            }
                            sudoku.setChoice(0);
                        }
                    });
                }
                row.add(label);
            }
            labelGrid.add(row);
        }
    }

    private void stopTimeline() {
        timeline.stop();
    }

    private void updateHudLabels() {
        timeNumberLabel.setText(String.valueOf(secondsElapsed));
        scoreNumberLabel.setText(String.valueOf(score));
        attemptNumberLabel.setText(String.valueOf(attempts));
    }

    private void setLabels() {
        for(int i = 0; i < 9; i++) {
            List<Label> row = new ArrayList<>();
            for(int j = 0; j < 9; j++) {
                Label label = (Label) board.lookup("#cell" + i + j);
                label.setFont(Font.font("Times New Roman", 24));
                label.setAlignment(Pos.CENTER);
                if(sudoku.getPuzzle()[i][j] != 0) {
                    label.setText(String.valueOf(sudoku.getPuzzle()[i][j]));
                    label.setBackground(Background.fill(Color.LIGHTGREEN));
                }
                row.add(label);
            }
            labelGrid.add(row);
        }
    }

    public void switchToMenu(ActionEvent e) throws IOException {
        FXMLLoader menuLoader = new FXMLLoader(App.class.getResource("fxml/menu.fxml"));
        Parent root = menuLoader.load();
        MenuController menuController = menuLoader.getController();
        menuController.setDatabaseController(db);
        Util.getStage(e).setScene(new Scene(root));
        menuController.username.setText(db.getLoggedInUser());
    }
}
