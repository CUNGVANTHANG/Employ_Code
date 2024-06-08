package controller;

import func.ExamManager;
import func.StudentManager;
import func.VehicleManager;
import view.ExamManagementView;
import view.StudentManagementView;
import view.VehicleManagementView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainController {
    private ExamManager examManager;
    private StudentManager studentManager;
    private VehicleManager vehicleManager;

    public MainController(ExamManager examManager, StudentManager studentManager, VehicleManager vehicleManager) {
        this.examManager = examManager;
        this.studentManager = studentManager;
        this.vehicleManager = vehicleManager;
    }

    public void showMainMenu() {
        JFrame frame = new JFrame("Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JButton btnManageExams = new JButton("Quản lý kỳ thi");
        btnManageExams.setBounds(100, 50, 200, 25);
        panel.add(btnManageExams);
        btnManageExams.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExamManagementView examManagementView = new ExamManagementView(examManager, studentManager);
                examManagementView.setVisible(true);
            }
        });

        JButton btnManageStudents = new JButton("Quản lý học viên");
        btnManageStudents.setBounds(100, 100, 200, 25);
        panel.add(btnManageStudents);
        btnManageStudents.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StudentManagementView studentManagementView = new StudentManagementView(studentManager);
                studentManagementView.setVisible(true);
            }
        });

        JButton btnManageVehicles = new JButton("Quản lý xe");
        btnManageVehicles.setBounds(100, 150, 200, 25);
        panel.add(btnManageVehicles);
        btnManageVehicles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VehicleManagementView vehicleManagementView = new VehicleManagementView(vehicleManager);
                vehicleManagementView.setVisible(true);
            }
        });
    }
}
