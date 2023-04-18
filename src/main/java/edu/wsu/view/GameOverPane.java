package edu.wsu.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameOverPane extends StackPane {
    Label gameOverLabel;
    Label scoreLabel;
    Label timeLabel;
    Button playAgain;
    Button mainMenu;

    public GameOverPane(Label timeField, Label scoreField) {
        super();
        this.setAlignment(Pos.CENTER);
        this.scoreLabel = scoreField;
        this.timeLabel = timeField;

        Pane grayFilter = new Pane(); {
            grayFilter.setOpacity(.75);
            grayFilter.setStyle("-fx-background-color: #808080;");
            this.getChildren().add(grayFilter);
        }

        VBox vBox = new VBox(); {
            vBox.setAlignment(Pos.CENTER);
            gameOverLabel = new Label("Game Over"); {
                gameOverLabel.setFont(Font.font(Util.TITLE_FONT, 100));
                gameOverLabel.setTextFill(Color.WHITE);
                VBox.setMargin(gameOverLabel, new Insets(5,5,5,5));
                vBox.getChildren().add(gameOverLabel);
            }
            scoreField.setFont(Font.font(Util.TITLE_FONT, 100));
            scoreField.setTextFill(Color.WHITE);
            VBox.setMargin(scoreField, new Insets(5,5,5,5));
            vBox.getChildren().add(scoreField);

            timeField.setFont(Font.font(Util.TITLE_FONT, 100));
            timeField.setTextFill(Color.WHITE);
            VBox.setMargin(timeField, new Insets(5,5,5,5));
            vBox.getChildren().add(timeField);

            playAgain = new Button("Play Again"); {
                playAgain.setCursor(Cursor.HAND);
                playAgain.setFont(Font.font(Util.BASE_FONT, 20));
                playAgain.setTextFill(Color.WHITE);
                playAgain.setStyle("-fx-background-color: #000000;");
                VBox.setMargin(playAgain, new Insets(5,5,5,5));
                vBox.getChildren().add(playAgain);
            }
            mainMenu = new Button("Main Menu"); {
                mainMenu.setCursor(Cursor.HAND);
                mainMenu.setFont(Font.font(Util.BASE_FONT, 20));
                mainMenu.setTextFill(Color.WHITE);
                mainMenu.setStyle("-fx-background-color: #000000;");
                VBox.setMargin(mainMenu, new Insets(5,5,5,5));
                vBox.getChildren().add(mainMenu);
            }
            this.getChildren().add(vBox);
        }
    }
}
