package edu.wsu.Server;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;
import edu.wsu.App;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class FireBaseService {
    FirebaseDatabase db;

    public FireBaseService() throws IOException {
        File file = new File(Objects.requireNonNull(App.class.getResource("token.json")).getFile());
        FileInputStream fis = new FileInputStream(file);
        FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(fis)).setDatabaseUrl("https://sudoku-9447f-default-rtdb.firebaseio.com/").build();
        FirebaseApp.initializeApp(options);
        db = FirebaseDatabase.getInstance();
    }

    public FirebaseDatabase getDb() {
        return db;
    }


}
