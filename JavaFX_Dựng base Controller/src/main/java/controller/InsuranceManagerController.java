package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

import java.net.URL;
import java.util.ResourceBundle;

public class InsuranceManagerController extends MainController {
    @FXML
    private Button backBtn;
    @FXML
    private MenuItem approveclaimMenuItem;
    @FXML
    private MenuItem rejectclaimMenuItem;
    @FXML
    private MenuItem insmngretrieveclaimMenuItem;
    @FXML
    private MenuItem insmngretrievecustomerMenuItem;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleEvent();
    }

    public void handleEvent() {
        backBtn.setOnMouseClicked(event -> handleTransfer(backBtn, "/fxml/login.fxml"));
        approveclaimMenuItem.setOnAction(event -> handleTransfer(approveclaimMenuItem, "/fxml/approveclaim.fxml"));
        rejectclaimMenuItem.setOnAction(event -> handleTransfer(rejectclaimMenuItem, "/fxml/rejectclaim.fxml"));
        insmngretrieveclaimMenuItem.setOnAction(event -> handleTransfer(insmngretrieveclaimMenuItem, "/fxml/insmngretrieveclaim.fxml"));
        insmngretrievecustomerMenuItem.setOnAction(event -> handleTransfer(insmngretrievecustomerMenuItem, "/fxml/insmngretrievecustomer.fxml"));
    }
}
