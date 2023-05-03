package edu.wsu.model;

public class User {
    private String username;
    private int highScore;

    public User(String username, int highScore) {
        this.username = username;
        this.highScore = highScore;
    }

    public String getUsername() {
        return username;
    }

    public int getHighScore() {
        return highScore;
    }
}
