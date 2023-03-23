package edu.wsu.seniorproject.controller;

import edu.wsu.seniorproject.model.Sudoku;
import edu.wsu.seniorproject.model.SudokuSingleton;
import edu.wsu.seniorproject.view.GameView;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuController {

    public void startGame(ActionEvent e) throws Exception {
        Sudoku s = SudokuSingleton.getInstance();

        GameView gameView = new GameView();
        s.addObserver(gameView);

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(gameView);
        stage.setScene(scene);
        stage.show();

        s.startGame();
    }

}