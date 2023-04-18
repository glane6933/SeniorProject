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

public class PausePane extends StackPane {
    Label pausedLabel;
    Button mainMenu;

    public PausePane() {
        super();
        this.setAlignment(Pos.CENTER);

        Pane grayFilter = new Pane(); {
            grayFilter.setOpacity(.75);
            grayFilter.setStyle("-fx-background-color: #808080;");
            this.getChildren().add(grayFilter);
        }

        VBox vBox = new VBox(); {
            vBox.setAlignment(Pos.CENTER);
            pausedLabel = new Label("Paused"); {
                pausedLabel.setFont(Font.font(Util.TITLE_FONT, 100));
                pausedLabel.setTextFill(Color.WHITE);
                VBox.setMargin(pausedLabel, new Insets(5,5,5,5));
                vBox.getChildren().add(pausedLabel);
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
