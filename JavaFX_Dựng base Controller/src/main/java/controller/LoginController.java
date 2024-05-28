package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends MainController {
    @FXML
    private TextField loginUser;
    @FXML
    private PasswordField loginPass;
    @FXML
    private Button loginBtn;

    private void handleLogin() {
        handleTransfer(loginBtn, "/fxml/policyholder.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleEvent();
    }

    public void handleEvent() {
        loginBtn.setOnMouseClicked(event -> handleLogin());
    }

}
