package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import func.ExamManager;
import func.StudentManager;
import func.UserFunc;
import entity.User;
import func.VehicleManager;
import view.LoginView;
import javax.swing.*;

public class LoginController {
    private UserFunc userDao;
    private LoginView loginView;
    private MainController mainController;

    public LoginController(LoginView view) {
        // Tạo các đối tượng quản lý và tải dữ liệu từ XML
        ExamManager examManager = new ExamManager();
        VehicleManager vehicleManager = new VehicleManager();
        StudentManager studentManager = new StudentManager();

        // Tạo đối tượng MainController
        mainController = new MainController(examManager, studentManager, vehicleManager);

        this.loginView = view;
        this.userDao = new UserFunc();
        view.addLoginListener(new LoginListener());
    }

    public void showLoginView() {
        loginView.setVisible(true);
    }

    /**
     * Lớp LoginListener chứa cài đặt cho sự kiện click button "Login"
     *
     * @author viettuts.vn
     */
    class LoginListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            User user = loginView.getUser();
            if (userDao.checkUser(user)) {
                loginView.dispose();
                // nếu đăng nhập thành công, mở màn hình quản lý bãi đậu xe
                mainController.showMainMenu();
            } else {
                loginView.showMessage("username hoặc password không đúng.");
            }
        }
    }
}
