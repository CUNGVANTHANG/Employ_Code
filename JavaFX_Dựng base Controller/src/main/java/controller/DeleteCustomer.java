package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DeleteCustomer extends MainController {
    @FXML
    private Button backBtn;
    @FXML
    private Button removeBtn;
    @FXML
    private TextField id;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleEvent();
    }

    public void handleEvent() {
        backBtn.setOnMouseClicked(event -> handleTransfer(backBtn, "/fxml/admin.fxml"));
        removeBtn.setOnMouseClicked(event -> handleRemove());
    }

    public void handleRemove() {
        id.setText("");
    }
}
