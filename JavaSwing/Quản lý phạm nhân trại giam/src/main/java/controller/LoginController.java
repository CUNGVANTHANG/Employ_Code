package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import func.UserFunc;
import entity.User;
import view.LoginView;
import view.MainFrame;

import javax.swing.*;

public class LoginController {
    private UserFunc userDao;
    private LoginView loginView;
    private MainFrame parkingLotManagerUI;

    public LoginController(LoginView view) {
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
                SwingUtilities.invokeLater(() -> {
                    MainFrame ui = new MainFrame();
                    ui.setVisible(true);
                });
            } else {
                loginView.showMessage("username hoặc password không đúng.");
            }
        }
    }
}
