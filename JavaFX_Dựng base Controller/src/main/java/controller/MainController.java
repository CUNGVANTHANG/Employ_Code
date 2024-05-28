package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    // Phương thức: Chuyển hướng
    public void handleTransfer(Node node, String path) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) node.getScene().getWindow();

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleTransfer(MenuItem menuItem, String path) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) menuItem.getParentPopup().getOwnerWindow();

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Phương thức: Thông báo
    public void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Phương thức: Xử lý sự kiện trên giao diện
    public void handleEvent(){}
}
