package edu.wsu.controller;

import edu.wsu.App;
import edu.wsu.model.Difficulty;
import edu.wsu.model.Sudoku;
import edu.wsu.model.User;
import edu.wsu.view.Util;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.List;

public class MenuController {
    @FXML
    public Button startButton;

    @FXML
    public Button leaderboardButton;

    @FXML
    public Button settingsButton;

    @FXML
    public Button howToPlayButton;

    @FXML
    public Button quitGameButton;

    @FXML
    public Button logoutButton;

    @FXML
    public Label username;

    @FXML
    public ComboBox<String> difficultySelector;

    private DatabaseController db;

    public MenuController() {

    }

    public void initialize() {
        difficultySelector.getItems().add("Easy");
        difficultySelector.getItems().add("Medium");
        difficultySelector.getItems().add("Hard");
        difficultySelector.setValue("Easy");
    }

    public void startGame(ActionEvent e) throws IOException {
        Sudoku sudoku = Sudoku.getInstance();

        switch (difficultySelector.getValue()) {
            case "Easy" -> sudoku.setDifficulty(Difficulty.EASY);
            case "Medium" -> sudoku.setDifficulty(Difficulty.MEDIUM);
            case "Hard" -> sudoku.setDifficulty(Difficulty.HARD);
        }

//        FXMLLoader menuLoader = new FXMLLoader(App.class.getResource("fxml/menu.fxml"));
//        Parent root = menuLoader.load();
//        MenuController menuController = menuLoader.getController();
//        menuController.setDatabaseController(db);
//        Util.getStage(e).setScene(new Scene(root));

        FXMLLoader gameLoader = new FXMLLoader(App.class.getResource("fxml/game.fxml"));
        Parent root = gameLoader.load();
        GameController gameController = gameLoader.getController();
        gameController.setDatabaseController(db);
        Util.getStage(e).setScene(new Scene(root));
        gameController.start();
    }

    public void quitGame() {
        System.exit(0);
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        FXMLLoader logoutLoader = new FXMLLoader(App.class.getResource("fxml/login.fxml"));
        Parent root = logoutLoader.load();
        Util.getStage(actionEvent).setScene(new Scene(root));
    }

    public void openHowToPlay(ActionEvent actionEvent) throws IOException {
        FXMLLoader howToPlayLoader = new FXMLLoader(App.class.getResource("fxml/howToPlay.fxml"));
        Parent root = howToPlayLoader.load();
        Util.getStage(actionEvent).setScene(new Scene(root));
    }

    public void openSettings(ActionEvent actionEvent) throws IOException {
        FXMLLoader settingsLoader = new FXMLLoader(App.class.getResource("fxml/settings.fxml"));
        Parent root = settingsLoader.load();
        Util.getStage(actionEvent).setScene(new Scene(root));
    }

    public void openLeaderboard(ActionEvent actionEvent) throws IOException {
        List<User> leaderboardData = db.getLeaderboard();
        FXMLLoader leaderboardLoader = new FXMLLoader(App.class.getResource("fxml/leaderboard.fxml"));
        Parent root = leaderboardLoader.load();
        LeaderboardController leaderboardController = leaderboardLoader.getController();
        leaderboardController.setDatabaseController(db);
        Util.getStage(actionEvent).setScene(new Scene(root));
        leaderboardController.showLeaderboardData(leaderboardData);

    }

    public void setDatabaseController(DatabaseController db) {
        this.db = db;
    }
}