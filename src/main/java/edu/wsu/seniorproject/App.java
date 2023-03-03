package edu.wsu.seniorproject;

import edu.wsu.seniorproject.model.SudokuImplSingleton;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    private static Scene scene;
    private static FXMLLoader menuLoader = new FXMLLoader(App.class.getResource("menu.fxml"));
    private static FXMLLoader gameLoader = new FXMLLoader(App.class.getResource("game.fxml"));
    private static AnchorPane menuPane;
    private static AnchorPane gamePane;

    static {
        try {
            menuPane = menuLoader.load();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    static {
        try {
            gamePane = gameLoader.load();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        SudokuImplSingleton.getInstance().setMenuView(menuLoader.getController());
        SudokuImplSingleton.getInstance().setGameView(gameLoader.getController());
        scene = new Scene(menuPane, 600, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) {
        if(fxml.equals("game")) {
            scene.setRoot(gamePane);
        } else if(fxml.equals("menu")) {
            scene.setRoot(menuPane);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}