package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PolicyHolderController extends MainController {
    @FXML
    private Button backBtn;
    @FXML
    private MenuItem fileclaimMenuItem;
    @FXML
    private MenuItem updateclaimMenuItem;
    @FXML
    private MenuItem retrieveclaimMenuItem;
    @FXML
    private MenuItem updateinfoMenuItem;
    @FXML
    private MenuItem updatedependentMenuItem;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleEvent();
    }

    public void handleEvent() {
        backBtn.setOnMouseClicked(event -> handleTransfer(backBtn, "/fxml/login.fxml"));
        fileclaimMenuItem.setOnAction(event -> handleTransfer(fileclaimMenuItem, "/fxml/fileclaim.fxml"));
        updateclaimMenuItem.setOnAction(event -> handleTransfer(updateclaimMenuItem, "/fxml/updateclaim.fxml"));
        retrieveclaimMenuItem.setOnAction(event -> handleTransfer(retrieveclaimMenuItem, "/fxml/retrieveclaim.fxml"));
        updateinfoMenuItem.setOnAction(event -> handleTransfer(updateinfoMenuItem, "/fxml/updateinfo.fxml"));
        updatedependentMenuItem.setOnAction(event -> handleTransfer(updatedependentMenuItem, "/fxml/updateinfo.fxml"));
    }
}
