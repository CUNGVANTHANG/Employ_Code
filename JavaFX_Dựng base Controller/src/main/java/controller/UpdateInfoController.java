package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateInfoController extends MainController {
    @FXML
    private Button backBtn;
    @FXML
    private Button saveBtn;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField address;
    @FXML
    private TextField email;
    @FXML
    private TextField password;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleEvent();
    }

    public void handleEvent() {
        backBtn.setOnMouseClicked(event -> handleTransfer(backBtn, "/fxml/policyholder.fxml"));
        saveBtn.setOnMouseClicked(event -> handleSave());
    }

    public void handleSave() {
        showAlert("Save", "Update information successfully", Alert.AlertType.INFORMATION);
        phoneNumber.setText("");
        email.setText("");
        password.setText("");
        address.setText("");
    }
}
