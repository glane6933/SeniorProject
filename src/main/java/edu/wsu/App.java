package edu.wsu;

import edu.wsu.view.Util;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader menuLoader = new FXMLLoader(App.class.getResource("fxml/menu.fxml"));
        stage.setScene(new Scene(menuLoader.load(), Util.SCENE_WIDTH, Util.SCENE_HEIGHT));
        stage.setTitle("Sudoku");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
//        FileInputStream token = new FileInputStream("resources/edu/wsu/seniorproject/token.json");
//
//        FirebaseOptions options = FirebaseOptions.builder()
//                .setCredentials(GoogleCredentials.fromStream(token))
//                .build();
//
//        FirebaseApp.initializeApp(options);
//        Firestore db = FirestoreClient.getFirestore();
        launch();
    }
}