package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCustomerController extends MainController {
    @FXML
    private Button backBtn;
    @FXML
    private Button doneBtn;
    @FXML
    private TextField name;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField address;
    @FXML
    private TextField email;
    @FXML
    private RadioButton policyholderRadioBtn;
    @FXML
    private RadioButton dependentRadioBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleEvent();
    }

    public void handleEvent() {
        backBtn.setOnMouseClicked(event -> handleTransfer(backBtn, "/fxml/admin.fxml"));
        doneBtn.setOnMouseClicked(event -> handleDone());
    }

    public void handleDone() {
        showAlert("Done", "Add new customer successfully", Alert.AlertType.INFORMATION);
        name.setText("");
        phoneNumber.setText("");
        address.setText("");
        email.setText("");
        policyholderRadioBtn.setSelected(false);
        dependentRadioBtn.setSelected(false);
    }
}
