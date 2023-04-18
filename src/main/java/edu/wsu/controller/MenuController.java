package edu.wsu.controller;

import edu.wsu.model.Difficulty;
import edu.wsu.model.Sudoku;
import edu.wsu.view.GameView;
import edu.wsu.view.Util;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class MenuController {

    @FXML
    public Button startButton;

    @FXML
    public Button leaderboard;

    @FXML
    public Button settings;

    @FXML
    public Button howToPlay;

    @FXML
    public Button quitGame;

    @FXML
    public Button login;

    @FXML
    public Button logout;

    @FXML
    public Label username;

    @FXML
    public ComboBox<String> choseDifficulty;

    public void initialize() {
        choseDifficulty.getItems().add("Easy");
        choseDifficulty.getItems().add("Medium");
        choseDifficulty.getItems().add("Hard");
        choseDifficulty.setValue("Easy");
    }

    public void startGame(ActionEvent e) {
        Sudoku sudoku = Sudoku.getInstance();

        switch (choseDifficulty.getValue()) {
            case "Easy" -> sudoku.setDifficulty(Difficulty.EASY);
            case "Medium" -> sudoku.setDifficulty(Difficulty.MEDIUM);
            case "Hard" -> sudoku.setDifficulty(Difficulty.HARD);
        }
        GameView gameView = new GameView();
        GameController gameController = new GameController(gameView);
        Util.getStage(e).setScene(new Scene(gameView));
        gameController.start();
    }

    public void quitGame(ActionEvent actionEvent) {
    }

    public void openHowToPlay(ActionEvent actionEvent) {
    }

    public void openSettings(ActionEvent actionEvent) {
    }

    public void openLeaderboard(ActionEvent actionEvent) {
    }
}