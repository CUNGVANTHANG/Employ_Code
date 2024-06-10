package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import func.UserFunc;
import entity.User;
import view.LoginView;
import view.MainView;

import javax.swing.*;

public class LoginController {
    private UserFunc userDao;
    private LoginView loginView;
    private MainView mainView;

    public LoginController(LoginView view) {
        mainView = new MainView();

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
                mainView.display();
            } else {
                loginView.showMessage("username hoặc password không đúng.");
            }
        }
    }
}
