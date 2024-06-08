package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class RejectClaimController extends MainController {
    @FXML
    private TextField claimId;
    @FXML
    private Button backBtn;
    @FXML
    private Button rejectBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleEvent();
    }

    public void handleEvent() {
        backBtn.setOnMouseClicked(event -> handleTransfer(backBtn, "/fxml/insurancemanager.fxml"));
        rejectBtn.setOnMouseClicked(event -> handleReject());
    }

    public void handleReject() {
        showAlert("Reject", "Claim ID does not exist", Alert.AlertType.CONFIRMATION);
        claimId.setText("");
    }
}
