package edu.wsu.controller;

import edu.wsu.model.GameState;
import edu.wsu.model.Sudoku;
import edu.wsu.view.GameOverPane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class GameController {

    private Sudoku sudoku;
    public Label time;
    public Label score;

    private boolean paused;
    private long startTime;
    private int secondsElapsed;
    private Timeline timeline;
    private int attempts = 3;

    @FXML
    private GridPane board;
    @FXML
    private GridPane numberSelection;

    private DatabaseController db;

    private List<List<Label>> labelGrid;
    private int[][] puzzle;

    public GameController() {
        sudoku = Sudoku.getInstance();
    }

    public void startTimer() {
        secondsElapsed = 0;
        updateTimerLabel();

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if(sudoku.state == GameState.COMPLETED) {
                sudoku.endGame();
                int score = (int) (10000.0 / secondsElapsed);
                db.changeHighScore(score);
                this.score.setText(String.valueOf(score));
                updateLabels();
                stopTimer();
            } else {
                secondsElapsed++;
                updateTimerLabel();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateTimerLabel() {
        time.setText(String.valueOf(secondsElapsed));
    }

    public void stopTimer() {
        timeline.stop();
    }

    public void setLabels() {
        puzzle = sudoku.getPuzzle();
        labelGrid = new ArrayList<>();
        for(int i = 0; i < 9; i++) {
            List<Label> row = new ArrayList<>();
            for(int j = 0; j < 9; j++) {
                Label label = (Label) board.lookup("#cell" + i + j);
                int rowIdx = i;
                int colIdx = j;
                if(puzzle[rowIdx][colIdx] != 0) {
                    label.setOnMouseClicked(null);
                } else {
                    label.setOnMouseClicked(event -> {
                        int choice = sudoku.getChoice();
                        if(choice != 0) {
                            puzzle[rowIdx][colIdx] = choice;
                            label.setText(Integer.toString(choice));
                            sudoku.setChoice(0);
                            sudoku.checkIfGameOver();
                            if(puzzle[rowIdx][colIdx] != sudoku.getSolution()[rowIdx][colIdx]) {
                                attempts--;
                                if(attempts == 0) {
                                    stopTimer();
                                    int score = (int) (10000.0 / secondsElapsed);
                                    db.changeHighScore(score);
                                    this.score.setText(String.valueOf(score));
                                    GameOverPane.show(score, secondsElapsed);
                                    board.setVisible(false);
                                    numberSelection.setVisible(false);
                                }
                            }
                        }
                    });
                }
                row.add(label);
            }
            labelGrid.add(row);
        }

        for(int i = 0; i < 9; i++) {
            Label label = (Label) numberSelection.lookup("#number" + (i+1));
            int choice = i + 1;
            label.setOnMouseClicked(event -> sudoku.setChoice(choice));
        }
    }

    public void updateLabels() {
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                int number = puzzle[i][j];
                Label label = labelGrid.get(i).get(j);
                label.setText(number == 0 ? "" : Integer.toString(number));
            }
        }

        long elapsedTime = (System.currentTimeMillis() / 1000) - startTime;
        int scoreValue = (int) (10000.0 / elapsedTime);
        score.setText(String.valueOf(scoreValue));
        time.setText(String.valueOf(elapsedTime));
    }

    public void togglePause() {
        sudoku.togglePause();
    }

    public void start() {
        sudoku.startNewGame();
        puzzle = sudoku.getPuzzle();
        setLabels();
        updateLabels();
        startTime = System.currentTimeMillis() / 1000;
        startTimer();
    }

    public void setDatabaseController(DatabaseController db) {
        this.db = db;
    }
}
