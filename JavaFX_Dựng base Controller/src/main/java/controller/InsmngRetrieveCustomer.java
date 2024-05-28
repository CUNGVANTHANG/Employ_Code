package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class InsmngRetrieveCustomer extends MainController {
    @FXML
    private Button backBtn;
    @FXML
    private Button retrieveBtn;
    @FXML
    private RadioButton policyholderRadioBtn;
    @FXML
    private RadioButton allRadioBtn;
    @FXML
    private RadioButton dependentRadioBtn;
    @FXML
    private TextField customerName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleEvent();
    }

    public void handleEvent() {
        backBtn.setOnMouseClicked(event -> handleTransfer(backBtn, "/fxml/insurancemanager.fxml"));
    }
}
