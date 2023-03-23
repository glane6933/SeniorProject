package edu.wsu.seniorproject;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static edu.wsu.seniorproject.view.Util.loadFXML;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(loadFXML("menu"));
        stage.setScene(scene);
        stage.setTitle("Sudoku");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}