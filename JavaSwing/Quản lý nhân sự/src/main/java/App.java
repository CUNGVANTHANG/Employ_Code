import controller.LoginController;
import view.LoginView;
import view.MainView;
import javax.swing.SwingUtilities;
import java.awt.*;

public class App {
    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            MainView mainView = new MainView();
//            mainView.display();
//        });

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
