package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ApproveClaimController extends MainController {
    @FXML
    private TextField claimId;
    @FXML
    private Button backBtn;
    @FXML
    private Button approveBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleEvent();
    }

    public void handleEvent() {
        backBtn.setOnMouseClicked(event -> handleTransfer(backBtn, "/fxml/insurancemanager.fxml"));
        approveBtn.setOnMouseClicked(event -> handleApprove());
    }

    public void handleApprove() {
        showAlert("Approve", "Claim ID does not exist", Alert.AlertType.CONFIRMATION);
        claimId.setText("");
    }
}
