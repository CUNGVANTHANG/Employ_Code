package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController extends MainController{
    @FXML
    private Button backBtn;
    @FXML
    private MenuItem addcustomerMenuItem;
    @FXML
    private MenuItem updatecustomerMenuItem;
    @FXML
    private MenuItem deletecustomerMenuItem;
    @FXML
    private MenuItem sumupMenuItem;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleEvent();
    }

    public void handleEvent() {
        backBtn.setOnMouseClicked(event -> handleTransfer(backBtn, "/fxml/login.fxml"));
        addcustomerMenuItem.setOnAction(event -> handleTransfer(addcustomerMenuItem, "/fxml/addcustomer.fxml"));
        updatecustomerMenuItem.setOnAction(event -> handleTransfer(updatecustomerMenuItem, "/fxml/updatecustomer.fxml"));
        deletecustomerMenuItem.setOnAction(event -> handleTransfer(deletecustomerMenuItem, "/fxml/deletecustomer.fxml"));
        sumupMenuItem.setOnAction(event -> handleTransfer(sumupMenuItem, "/fxml/sumup.fxml"));

    }
}
