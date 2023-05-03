package edu.wsu.controller;

import edu.wsu.App;
import edu.wsu.view.Util;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RegisterController {

    public TextField usernameField;
    public TextField emailField;
    public PasswordField passwordField;
    public Label popup;

    private DatabaseController db;

    public void register(ActionEvent e) throws IOException, InterruptedException {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        if(db.createAccount(username, email, password)) {
            System.out.println("Account creation successful");
            popup.setText("Account Creation Successful!");
            Thread.sleep(300);
            FXMLLoader loginLoader = new FXMLLoader(App.class.getResource("fxml/login.fxml"));
            Parent root = loginLoader.load();
            LoginController loginController = loginLoader.getController();
            loginController.setDatabaseController(db);
            Util.getStage(e).setScene(new Scene(root));
        }
    }

    public void setDatabaseController(DatabaseController db) {
        this.db = db;
    }
}
