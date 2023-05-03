package edu.wsu.controller;

import edu.wsu.App;
import edu.wsu.view.Util;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {

    public TextField usernameField;
    public PasswordField passwordField;
    public Label popup;

    private static DatabaseController db;

    public LoginController() {

    }

    public void login(ActionEvent e) throws IOException, InterruptedException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if(db.login(username, password)) {
            System.out.println("Login successful");
            FXMLLoader menuLoader = new FXMLLoader(App.class.getResource("fxml/menu.fxml"));
            Parent root = menuLoader.load();
            MenuController menuController = menuLoader.getController();
            menuController.setDatabaseController(db);
            Util.getStage(e).setScene(new Scene(root));
            menuController.username.setText(db.getLoggedInUser());
        } else {
            System.out.println("Invalid username or password");
            popup.setText("Invalid Username or Password");
            usernameField.setText("");
            passwordField.setText("");
            Thread.sleep(3000);
            popup.setText("");
        }
    }

    public void register(ActionEvent e) throws IOException {
        FXMLLoader registerLoader = new FXMLLoader(App.class.getResource("fxml/register.fxml"));
        Parent root = registerLoader.load();
        RegisterController registerController = registerLoader.getController();
        registerController.setDatabaseController(db);
        Util.getStage(e).setScene(new Scene(root));
    }

    public void setDatabaseController(DatabaseController db) {
        this.db = db;
    }
}
