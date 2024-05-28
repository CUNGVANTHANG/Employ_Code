package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class RetrieveClaimController extends MainController {
    @FXML
    private Button backBtn;
    @FXML
    private TextField claimId;
    @FXML
    private Button retrieveBtn;

    /**
     * Xác nhận claimId
     */
    public boolean validate(String claimId) {
        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleEvent();
    }

    public void handleEvent() {
        backBtn.setOnMouseClicked(event -> handleTransfer(backBtn, "/fxml/policyholder.fxml"));
        retrieveBtn.setOnMouseClicked(event -> handleSave());
    }

    public void handleSave() {
        showAlert("Retrieve", "Claim ID does not exist", Alert.AlertType.CONFIRMATION);
    }
}
