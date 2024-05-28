package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SumUpController extends MainController{
    @FXML
    private Button backBtn;
    @FXML
    private Button doneBtn;
    @FXML
    private TextField id;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleEvent();
    }

    public void handleEvent() {
        backBtn.setOnMouseClicked(event -> handleTransfer(backBtn, "/fxml/login.fxml"));
        doneBtn.setOnMouseClicked(event -> handleDone());
    }

    public void handleDone() {
        id.setText("");
        startDate.setValue(null);
        endDate.setValue(null);
    }
}
