package controller;

import base.DictionaryManagement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    protected Button searchBtn;
    @FXML
    protected Button translateBtn;
    @FXML
    private Button gameBtn;
    @FXML
    private Button bookmarkBtn;
    @FXML
    private Button settingBtn;
    @FXML
    private AnchorPane mainPane;
    private AnchorPane searchPane;
    private AnchorPane translatePane;
    private AnchorPane gamePane;
    private AnchorPane bookmarkPane;
    private AnchorPane settingPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
        handleEvent();
    }

    public void handleEvent() {

    }

    public void loadData() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/search.fxml"));
            searchPane = loader.load();
            searchPane.getStylesheets().add(getClass().getResource("/css/search.css").toExternalForm());
        } catch (IOException e) {
            e.printStackTrace();
        }

        mainPane.getChildren().setAll(searchPane);
        DictionaryManagement.insertFromFile();
    }
}
