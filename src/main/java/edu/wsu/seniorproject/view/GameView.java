package edu.wsu.seniorproject.view;

import edu.wsu.seniorproject.controller.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.util.Observable;
import java.util.Observer;

public class GameView extends AnchorPane implements Observer {

    public GameView() throws Exception {
        FXMLLoader loader = new FXMLLoader(Util.class.getResource("/edu/wsu/seniorproject/game.fxml"));
        loader.setRoot(this);
        GameController controller = new GameController();
        loader.setController(controller);
        loader.load();

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
