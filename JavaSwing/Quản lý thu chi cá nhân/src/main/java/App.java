import controller.LoginController;
import view.LoginView;

import java.awt.*;

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
