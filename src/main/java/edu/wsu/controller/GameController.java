package edu.wsu.controller;

import edu.wsu.model.GameState;
import edu.wsu.model.Sudoku;
import edu.wsu.view.GameView;
import javafx.scene.input.KeyCode;

import java.util.Timer;
import java.util.TimerTask;

public class GameController {

    private final Sudoku sudoku;
    private final GameView gameView;

    Timer timer;
    TimerTask timerTask;
    private int seconds;


    public GameController(GameView gameView) {
        sudoku = Sudoku.getInstance();
        this.gameView = gameView;
    }

    public void togglePause() {
        gameView.togglePause();
        sudoku.togglePause();
    }

    public void start() {
        sudoku.startNewGame();
        timer = new Timer();
        seconds = 0;
        this.timerTask = new TimerTask() {
            @Override
            public void run() {
                seconds++;
                sudoku.update(seconds);
                gameView.update(sudoku);

                //event handler
                gameView.setEventHandler(event -> {
                    if(event.getCode() == KeyCode.ESCAPE) {
                        togglePause();
                    }
                });

                if(sudoku.state == GameState.COMPLETED) {
                    this.cancel();
                    gameView.end();
                } else if(sudoku.state == GameState.NOT_RUNNING) {
                    this.cancel();
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask, 1000, 1000);
    }
}
