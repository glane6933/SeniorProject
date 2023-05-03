package edu.wsu.controller;

import edu.wsu.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseController {
    private String loggedInUser;



    public static void loadDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    private static Connection getDBConnection() {
        loadDriver();
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://senior-project.cvyt79jshmur.us-east-1.rds.amazonaws.com:3306/sudoku?" +
                    "user=java&password=java123!");
            return conn;
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return null;
    }

    public boolean createAccount(String username, String email, String password) {
        String createAccountQuery = "INSERT INTO game_data (username, email, password) VALUES (?, ?, ?)";
        try(Connection conn = getDBConnection()) {
            assert conn != null;
            try(PreparedStatement statement = conn.prepareStatement(createAccountQuery)) {
                statement.setString(1, username);
                statement.setString(2, email);
                statement.setString(3, password);
                statement.executeUpdate();
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return false;
        }
    }

    public boolean login(String username, String password) {
        String loginQuery = "SELECT * FROM game_data WHERE username = ? AND password = ?";
        try(Connection conn = getDBConnection()) {
            assert conn != null;
            try(PreparedStatement statement = conn.prepareStatement(loginQuery)) {
                statement.setString(1, username);
                statement.setString(2, password);
                try(ResultSet resultSet = statement.executeQuery()) {
                    if(!resultSet.next()) {
                        System.err.println("Account not found");
                        return false;
                    }
                    loggedInUser = resultSet.getString("username");
                    return true;
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return false;
        }
    }

    public boolean logout() {
        try {
            loggedInUser = null;
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean changeHighScore(int score) {
        String changeHighScoreQuery = "UPDATE game_data SET high_score = ? WHERE username = ?";
        if(loggedInUser != null) {
            try (Connection conn = getDBConnection()) {
                assert conn != null;
                try (PreparedStatement statement = conn.prepareStatement(changeHighScoreQuery)) {
                    statement.setInt(1, score);
                    statement.setString(2, loggedInUser);
                    statement.executeUpdate();
                    return true;
                }
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                return false;
            }
        }
        return false;
    }

    public int getHighScore() {
        String getHighScoreQuery = "SELECT high_score FROM game_data WHERE username = ?";
        try(Connection conn = getDBConnection()) {
            assert conn != null;
            try(PreparedStatement statement = conn.prepareStatement(getHighScoreQuery)) {
                statement.setString(1, loggedInUser);
                try(ResultSet resultSet = statement.executeQuery()) {
                    if(resultSet.next()) {
                        return resultSet.getInt("high_score");
                    } else {
                        return 0;
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return 0;
        }
    }

    public List<User> getLeaderboard() {
        String getLeaderboardQuery = "SELECT username, high_score FROM game_data ORDER BY high_score DESC LIMIT 10";
        List<User> leaderboard = new ArrayList<>();
        try(Connection conn = getDBConnection()) {
            assert conn != null;
            try(PreparedStatement statement = conn.prepareStatement(getLeaderboardQuery)) {
                try(ResultSet resultSet = statement.executeQuery()) {
                    while(resultSet.next()) {
                        String username = resultSet.getString("username");
                        int score = resultSet.getInt("high_score");
                        User user = new User(username, score);
                        leaderboard.add(user);
                    }
                    return leaderboard;
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return null;
        }
    }

    public String getLoggedInUser() {
        return loggedInUser;
    }
}
