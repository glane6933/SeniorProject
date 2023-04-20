package edu.wsu.Server;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

public class FirebaseAuthentication {
    private FirebaseAuth firebaseAuth;

    public FirebaseAuthentication() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void createAccount(String email, String username, String password) {
        try {
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(email)
                    .setPassword(password)
                    .setDisplayName(username);
            UserRecord userRecord = firebaseAuth.createUser(request);
            System.out.println("Successfully created new user: " + userRecord.getUid());
        } catch (FirebaseAuthException e) {
            System.err.println("Error creating new user: " + e.getMessage());
        }
    }

    public void login(String email, String password) {

    }

    public void logout() {

    }

}
