package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import func.FinanceManager;
import func.UserFunc;
import entity.User;
import view.FinanceManagementView;
import view.LoginView;
import javax.swing.*;

public class LoginController {
    private UserFunc userDao;
    private LoginView loginView;
    private FinanceManager manager;
    private FinanceManagementView managementView;
    private FinanceController controller;

    public LoginController(LoginView view) {
        manager = new FinanceManager();
        managementView = new FinanceManagementView();
        controller = new FinanceController(managementView, manager);
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
                controller.loadEntries("src/main/resources/finance_entries.xml");

                managementView.setVisible(true);
            } else {
                loginView.showMessage("username hoặc password không đúng.");
            }
        }
    }
}
