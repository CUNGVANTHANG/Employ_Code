import controller.LoginController;
import controller.ParkingLotManagerController;
import entity.ParkingLotManager;
import view.LoginView;
import view.ParkingLotManagerView;
import view.ParkingLotView;

import javax.swing.*;
import java.awt.*;

public class App {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                LoginView view = new LoginView();
                LoginController controller = new LoginController(view);
                // hiển thị màn hình login
                controller.showLoginView();
            }
        });

//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                ParkingLotManagerController controller = new ParkingLotManagerController();
//                new ParkingLotManagerView(controller).setVisible(true);
//            }
//        });
    }
}
