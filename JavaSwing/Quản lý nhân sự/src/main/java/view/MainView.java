package view;

import controller.DepartmentController;
import controller.EmployeeController;
import controller.PositionController;
import func.DepartmentManager;
import func.EmployeeManager;
import func.PositionManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView extends JFrame {
    private JTabbedPane tabbedPane;
    private DepartmentController departmentController;
    private EmployeeController employeeController;
    private PositionController positionController;

    public MainView() {
        setTitle("Main Menu");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tabbedPane = new JTabbedPane();
        EmployeeManager employeeManager = new EmployeeManager();
        EmployeeView employeeView = new EmployeeView();
        employeeController = new EmployeeController(employeeManager, employeeView);
        tabbedPane.addTab("Employee", employeeView);

        PositionManager positionManager = new PositionManager();
        PositionView positionView = new PositionView();
        positionController = new PositionController(positionManager, positionView);
        tabbedPane.addTab("Position", positionView);

        DepartmentManager departmentManager = new DepartmentManager();
        DepartmentView departmentView = new DepartmentView();
        departmentController = new DepartmentController(departmentManager, departmentView);
        tabbedPane.addTab("Department", departmentView);

        getContentPane().add(tabbedPane, BorderLayout.CENTER);

        tabbedPane.addChangeListener(e -> {
            int tabIndex = tabbedPane.getSelectedIndex();
            switch (tabIndex) {
                case 0:
                    employeeController.loadEmployees();
                    break;
                case 1:
                    positionController.loadPositions();
                    break;
                case 2:
                    departmentController.loadDepartments();
                    break;
                default:
                    break;
            }
        });

        // Thêm các button vào MainView
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        searchPanel.add(new JLabel("Search by name:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchPanel);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lấy index của tab được chọn
                int selectedIndex = tabbedPane.getSelectedIndex();
                // Hiển thị dialog thêm dữ liệu tùy thuộc vào tab được chọn
                switch (selectedIndex) {
                    case 0:
                        employeeView.showAdd(employeeController);
                        break;
                    case 1:
                        positionView.showAdd(positionController);
                        break;
                    case 2:
                        departmentView.showAdd(departmentController);
                        break;
                    default:
                        break;
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lấy index của tab được chọn
                int selectedIndex = tabbedPane.getSelectedIndex();
                // Hiển thị dialog thêm dữ liệu tùy thuộc vào tab được chọn
                switch (selectedIndex) {
                    case 0:
                        employeeView.showEdit(employeeController);
                        break;
                    case 1:
                        positionView.showEdit(positionController);
                        break;
                    case 2:
                        departmentView.showEdit(departmentController);
                        break;
                    default:
                        break;
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lấy index của tab được chọn
                int selectedIndex = tabbedPane.getSelectedIndex();
                // Hiển thị dialog thêm dữ liệu tùy thuộc vào tab được chọn
                switch (selectedIndex) {
                    case 0:
                        employeeView.showDelete(employeeController);
                        break;
                    case 1:
                        positionView.showDelete(positionController);
                        break;
                    case 2:
                        departmentView.showDelete(departmentController);
                        break;
                    default:
                        break;
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = searchField.getText().trim();
                int selectedIndex = tabbedPane.getSelectedIndex();
                switch (selectedIndex) {
                    case 0:
                        employeeView.searchByName(keyword);
                        searchField.setText("");
                        break;
                    case 1:
                        positionView.searchByName(keyword);
                        searchField.setText("");
                        break;
                    case 2:
                        departmentView.searchByName(keyword);
                        searchField.setText("");
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void display() {
        employeeController.loadEmployees();
        setVisible(true);
    }
}
