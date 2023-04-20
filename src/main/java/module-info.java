module edu.wsu.seniorproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires firebase.admin;
    requires com.google.auth.oauth2;
    requires com.google.auth;

    opens edu.wsu to javafx.fxml;
    exports edu.wsu;

    opens edu.wsu.controller to javafx.fxml;
    exports edu.wsu.controller;

    opens edu.wsu.view to javafx.fxml;
    exports edu.wsu.view;

    exports edu.wsu.Server;
    opens edu.wsu.Server to javafx.fxml;
}