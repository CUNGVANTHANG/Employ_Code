package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateClaimController extends MainController {
    @FXML
    private Button backBtn;
    @FXML
    private TextField claimId;
    @FXML
    private TextField personId;
    @FXML
    private TextField cardNumber;
    @FXML
    private TextField claimAmount;
    @FXML
    private TextField name;
    @FXML
    private TextField bankingInfo;
    @FXML
    private Button saveBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleEvent();
    }

    public void handleEvent() {
        backBtn.setOnMouseClicked(event -> handleTransfer(backBtn, "/fxml/policyholder.fxml"));
        saveBtn.setOnMouseClicked(event -> handleSave());
    }

    public void handleSave() {
        showAlert("Save", "Update claim successfully", Alert.AlertType.INFORMATION);
        claimId.setText("");
        personId.setText("");
        cardNumber.setText("");
        name.setText("");
        claimAmount.setText("");
        bankingInfo.setText("");
    }
}
