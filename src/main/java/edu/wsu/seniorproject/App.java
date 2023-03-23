package edu.wsu.seniorproject;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

import static edu.wsu.seniorproject.view.Util.loadFXML;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(loadFXML("menu"));
        stage.setScene(scene);
        stage.setTitle("Sudoku");
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