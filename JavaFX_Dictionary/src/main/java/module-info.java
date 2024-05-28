module dictionary {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires json.simple;
    requires jlayer;

    exports controller;
    opens controller to javafx.fxml, javafx.controls, javafx.web;
    exports app;
    opens app to javafx.controls, javafx.fxml, javafx.web;
}