package edu.wsu;

import edu.wsu.controller.DatabaseController;
import edu.wsu.controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loginLoader = new FXMLLoader(App.class.getResource("fxml/login.fxml"));
        Parent root = loginLoader.load();
        LoginController loginController = loginLoader.getController();
        DatabaseController db = new DatabaseController();
        loginController.setDatabaseController(db);
        stage.setScene(new Scene(root));
        stage.setTitle("Sudoku");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}