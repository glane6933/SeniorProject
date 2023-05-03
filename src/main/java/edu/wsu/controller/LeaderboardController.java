package edu.wsu.controller;

import edu.wsu.App;
import edu.wsu.model.User;
import edu.wsu.view.Util;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class LeaderboardController {
    public TableView<User> leaderboardTable;
    public TableColumn<Object, String> usernameColumn;
    public TableColumn<Object, Integer> highScoreColumn;
    public VBox leaderboardVBox;
    private DatabaseController db;
    public void setDatabaseController(DatabaseController db) {
        this.db = db;
    }

    public void showLeaderboardData(List<User> leaderboardData) {
        ObservableList<User> data = FXCollections.observableArrayList(leaderboardData);
        leaderboardTable.setItems(data);
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        highScoreColumn.setCellValueFactory(new PropertyValueFactory<>("highScore"));
    }

    public void mainMenu(ActionEvent e) throws IOException {
        FXMLLoader menuLoader = new FXMLLoader(App.class.getResource("fxml/menu.fxml"));
        Parent root = menuLoader.load();
        MenuController menuController = menuLoader.getController();
        menuController.setDatabaseController(db);
        Util.getStage(e).setScene(new Scene(root));
        menuController.username.setText(db.getLoggedInUser());
    }
}
