module edu.wsu.seniorproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires java.sql;
    requires mysql.connector.j;

    opens edu.wsu to javafx.fxml;
    exports edu.wsu;

    opens edu.wsu.controller to javafx.fxml;
    exports edu.wsu.controller;

    opens edu.wsu.view to javafx.fxml;
    exports edu.wsu.view;

    opens edu.wsu.model to javafx.fxml;
    exports edu.wsu.model;
}