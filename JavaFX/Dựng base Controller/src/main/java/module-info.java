module org.example.project {
    requires javafx.controls;
    requires javafx.fxml;


    opens controller to javafx.fxml, javafx.controlsf;
    exports controller;
    opens app to javafx.fxml, javafx.controlsf;
    exports app;
}