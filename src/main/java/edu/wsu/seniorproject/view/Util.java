package edu.wsu.seniorproject.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class Util {
    public static void setRoot(Scene scene, String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML (String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(Util.class.getResource("/edu/wsu/seniorproject/" + fxml + ".fxml"));
        return loader.load();
    }
}
