import controller.LoginController;
import controller.MainController;
import func.ExamManager;
import func.StudentManager;
import func.VehicleManager;
import utils.XMLUtils;
import view.LoginView;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Paths;

public class App {
    public static void main(String[] args) {

        // Chạy giao diện chính
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                LoginView view = new LoginView();
                LoginController controller = new LoginController(view);
                // hiển thị màn hình login
                controller.showLoginView();
            }
        });
    }
}
