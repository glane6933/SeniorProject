package edu.wsu.view;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Util {

    public static final double SCENE_WIDTH = 640;
    public static final double SCENE_HEIGHT = 480;
    public static final String TITLE_FONT = "Forte";
    public static final String BASE_FONT = "Times New Roman";

    public static Stage getStage(ActionEvent e) {
        return (Stage) ((Node) e.getSource()).getScene().getWindow();
    }
}
