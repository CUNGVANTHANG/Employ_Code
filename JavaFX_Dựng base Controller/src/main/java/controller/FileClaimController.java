package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class FileClaimController extends MainController {
    @FXML
    private Button backBtn;
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
    private Button doneBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleEvent();
    }

    public void handleEvent() {
        backBtn.setOnMouseClicked(event -> handleTransfer(backBtn, "/fxml/policyholder.fxml"));
        doneBtn.setOnMouseClicked(event -> handleDone());
    }

    public void handleDone() {
        showAlert("Done", "File new claim successfully", Alert.AlertType.INFORMATION);
        personId.setText("");
        cardNumber.setText("");
        name.setText("");
        claimAmount.setText("");
        bankingInfo.setText("");
    }

}
