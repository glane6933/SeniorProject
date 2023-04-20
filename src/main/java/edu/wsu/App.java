package edu.wsu;

import edu.wsu.Server.ShowDbChanges;
import edu.wsu.view.Util;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader menuLoader = new FXMLLoader(App.class.getResource("fxml/menu.fxml"));
        stage.setScene(new Scene(menuLoader.load(), Util.SCENE_WIDTH, Util.SCENE_HEIGHT));
        stage.setTitle("Sudoku");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        Thread t = new Thread(new ShowDbChanges());
        t.start();
        launch();
    }
}