module edu.wsu.seniorproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires firebase.admin;
    requires com.google.auth.oauth2;

    opens edu.wsu.seniorproject to javafx.fxml;
    exports edu.wsu.seniorproject;
    exports edu.wsu.seniorproject.controller;
    opens edu.wsu.seniorproject.controller to javafx.fxml;
}