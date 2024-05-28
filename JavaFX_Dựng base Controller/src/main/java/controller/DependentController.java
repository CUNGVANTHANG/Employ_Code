package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

import java.net.URL;
import java.util.ResourceBundle;

public class DependentController extends MainController {
    @FXML
    private Button backBtn;
    @FXML
    private MenuItem retrieveMenuItem;
    @FXML
    private MenuItem showMenuItem;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleEvent();
    }

    public void handleEvent() {
        backBtn.setOnMouseClicked(event -> handleTransfer(backBtn, "/fxml/login.fxml"));
    }
}
