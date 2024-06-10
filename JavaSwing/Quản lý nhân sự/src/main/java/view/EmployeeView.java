package view;

import com.toedter.calendar.JDateChooser;
import controller.EmployeeController;
import entity.Department;
import entity.Employee;
import entity.Position;
import func.DepartmentManager;
import func.EmployeeManager;
import func.PositionManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeeView extends JPanel {
    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private DepartmentManager departmentManager;
    private PositionManager positionManager;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public EmployeeView() {
        setLayout(new BorderLayout());
        tableModel = new DefaultTableModel();
        employeeTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void displayEmployees(List<Employee> employees) {
        String[] columnNames = {"ID", "Name", "Date of Birth", "Gender", "Hometown", "Salary", "Department", "Position",};
        Object[][] data = new Object[employees.size()][8]; // Update size to match the number of columns
        for (int i = 0; i < employees.size(); i++) {
            Employee employee = employees.get(i);
            data[i][0] = employee.getId();
            data[i][1] = employee.getName();
            data[i][2] = dateFormat.format(employee.getDateOfBirth()); // Add date of birth
            data[i][3] = employee.getGender(); // Add gender
            data[i][4] = employee.getHometown(); // Add hometown
            data[i][5] = employee.getSalary(); // Add salary
            data[i][6] = employee.getDepartment();
            data[i][7] = employee.getPosition();
        }
        tableModel = new DefaultTableModel(data, columnNames);
        employeeTable.setModel(tableModel);
    }

    public void showAdd(EmployeeController employeeController) {
        departmentManager = new DepartmentManager();
        positionManager = new PositionManager();

        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        JDialog addDialog = new JDialog(frame, "Add Employee", true);
        addDialog.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(8, 2, 10, 10)); // Increased gap between components
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add empty border to create space around the components

        inputPanel.add(new JLabel("ID:"));
        JTextField idField = new JTextField();
        inputPanel.add(idField);

        inputPanel.add(new JLabel("Name:"));
        JTextField nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Date of Birth:"));
        JDateChooser dobChooser = new JDateChooser();
        dobChooser.setDateFormatString("dd/MM/yyyy");
        inputPanel.add(dobChooser);

        inputPanel.add(new JLabel("Gender:"));
        JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"Female", "Male"});
        inputPanel.add(genderComboBox);

        inputPanel.add(new JLabel("Hometown:"));
        JTextField hometownField = new JTextField();
        inputPanel.add(hometownField);

        inputPanel.add(new JLabel("Salary:"));
        JTextField salaryField = new JTextField();
        inputPanel.add(salaryField);

        // Department ComboBox
        JComboBox<String> departmentComboBox = new JComboBox<>();
        List<Department> departments = departmentManager.getAllDepartments();
        for (Department department : departments) {
            departmentComboBox.addItem(department.getDepartmentName());
        }
        inputPanel.add(new JLabel("Department:"));
        inputPanel.add(departmentComboBox);

        JComboBox<String> positionComboBox = new JComboBox<>();
        List<Position> positions = positionManager.getAllPositions();
        for (Position position : positions) {
            positionComboBox.addItem(position.getPositionName());
        }
        inputPanel.add(new JLabel("Position:"));
        inputPanel.add(positionComboBox);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String id = idField.getText().trim();
                    String name = nameField.getText().trim();
                    Date dob = dobChooser.getDate();
                    String gender = (String) genderComboBox.getSelectedItem();
                    String hometown = hometownField.getText().trim();
                    double salary = Double.parseDouble(salaryField.getText().trim());
                    String department = (String) departmentComboBox.getSelectedItem();
                    String position = (String) positionComboBox.getSelectedItem();

                    if (!employeeController.isEmployeeIdUnique(id)) {
                        JOptionPane.showMessageDialog(addDialog, "Employee ID already exists. Please use a unique ID.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Create Employee object
                    Employee employee = new Employee(id, name, dob, gender, hometown, salary, department, position);

                    // Add employee to database or data structure
                    // Call the controller method to add employee
                    employeeController.addEmployee(employee);

                    // Close dialog
                    addDialog.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(addDialog, "Invalid input. Please check your data and try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addDialog.dispose();
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);

        addDialog.add(inputPanel, BorderLayout.CENTER);
        addDialog.add(buttonPanel, BorderLayout.SOUTH);

        // Set size of the dialog
        addDialog.setSize(400, 400);

        addDialog.setLocationRelativeTo(frame);
        addDialog.setVisible(true);
    }

    public void showEdit(EmployeeController employeeController) {
        departmentManager = new DepartmentManager();
        positionManager = new PositionManager();

        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select an employee to edit.", "No Employee Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String id = (String) tableModel.getValueAt(selectedRow, 0);
        String name = (String) tableModel.getValueAt(selectedRow, 1);
        Date dob;
        try {
            dob = dateFormat.parse((String) tableModel.getValueAt(selectedRow, 2));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String gender = (String) tableModel.getValueAt(selectedRow, 3);
        String hometown = (String) tableModel.getValueAt(selectedRow, 4);
        double salary = (Double) tableModel.getValueAt(selectedRow, 5);
        String department = (String) tableModel.getValueAt(selectedRow, 6);
        String position = (String) tableModel.getValueAt(selectedRow, 7);

        Employee employee = new Employee(id, name, dob, gender, hometown, salary, department, position);

        JTextField idField = new JTextField(id);
        JTextField nameField = new JTextField(name);
        JDateChooser dobChooser = new JDateChooser();
        dobChooser.setDate(dob);
        JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"Female", "Male"});
        genderComboBox.setSelectedItem(gender);
        JTextField hometownField = new JTextField(hometown);
        JTextField salaryField = new JTextField(String.valueOf(salary));

        // Department ComboBox
        JComboBox<String> departmentComboBox = new JComboBox<>();
        List<Department> departments = departmentManager.getAllDepartments();
        for (Department dep : departments) {
            departmentComboBox.addItem(dep.getDepartmentName());
        }
        departmentComboBox.setSelectedItem(department);

        // Position ComboBox
        JComboBox<String> positionComboBox = new JComboBox<>();
        List<Position> positions = positionManager.getAllPositions();
        for (Position pos : positions) {
            positionComboBox.addItem(pos.getPositionName());
        }
        positionComboBox.setSelectedItem(position);

        JPanel inputPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Date of Birth:"));
        inputPanel.add(dobChooser);
        inputPanel.add(new JLabel("Gender:"));
        inputPanel.add(genderComboBox);
        inputPanel.add(new JLabel("Hometown:"));
        inputPanel.add(hometownField);
        inputPanel.add(new JLabel("Salary:"));
        inputPanel.add(salaryField);
        inputPanel.add(new JLabel("Department:"));
        inputPanel.add(departmentComboBox);
        inputPanel.add(new JLabel("Position:"));
        inputPanel.add(positionComboBox);

        int option = JOptionPane.showConfirmDialog(frame, inputPanel, "Edit Employee", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String newId = idField.getText().trim();
                String newName = nameField.getText().trim();
                Date newDob = dobChooser.getDate();
                String newGender = (String) genderComboBox.getSelectedItem();
                String newHometown = hometownField.getText().trim();
                double newSalary = Double.parseDouble(salaryField.getText().trim());
                String newDepartment = (String) departmentComboBox.getSelectedItem();
                String newPosition = (String) positionComboBox.getSelectedItem();

                if (!employeeController.isEmployeeIdUnique(newId)) {
                    JOptionPane.showMessageDialog(frame, "Employee ID already exists. Please use a unique ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Create updated Employee object
                Employee updatedEmployee = new Employee(newId, newName, newDob, newGender, newHometown, newSalary, newDepartment, newPosition);

                // Update employee in database or data structure
                // Call the controller method to update employee
                employeeController.updateEmployee(employee, updatedEmployee);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input for salary.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void showDelete(EmployeeController employeeController) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select an employee to delete.", "No Employee Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this employee?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            String id = (String) tableModel.getValueAt(selectedRow, 0);
            String name = (String) tableModel.getValueAt(selectedRow, 1);
            Date dob;
            try {
                dob = dateFormat.parse((String) tableModel.getValueAt(selectedRow, 2));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            String gender = (String) tableModel.getValueAt(selectedRow, 3);
            String hometown = (String) tableModel.getValueAt(selectedRow, 4);
            double salary = (Double) tableModel.getValueAt(selectedRow, 5);
            String department = (String) tableModel.getValueAt(selectedRow, 6);
            String position = (String) tableModel.getValueAt(selectedRow, 7);

            Employee employee = new Employee(id, name, dob, gender, hometown, salary, department, position);

            employeeController.deleteEmployee(employee);

        }
    }


    public void searchByName(String name) {
        // Kiểm tra xem chuỗi tìm kiếm có trống không
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a search term.", "Empty Search Term", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<Integer> foundIndices = new ArrayList<>();

        // Tìm kiếm theo tên sử dụng regex
        Pattern pattern = Pattern.compile("^" + name);
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String equipmentName = (String) employeeTable.getValueAt(i, 1);
            Matcher matcher = pattern.matcher(equipmentName);
            if (matcher.find()) {
                foundIndices.add(i);
            }
        }

        // Tạo bảng kết quả với đầy đủ các cột
        DefaultTableModel resultModel = new DefaultTableModel();
        for (int columnIndex = 0; columnIndex < tableModel.getColumnCount(); columnIndex++) {
            resultModel.addColumn(tableModel.getColumnName(columnIndex));
        }
        for (int index : foundIndices) {
            Object[] rowData = new Object[tableModel.getColumnCount()];
            for (int columnIndex = 0; columnIndex < tableModel.getColumnCount(); columnIndex++) {
                rowData[columnIndex] = employeeTable.getValueAt(index, columnIndex);
            }
            resultModel.addRow(rowData);
        }
        JTable resultTable = new JTable(resultModel);

        // Tạo cửa sổ kết quả dưới dạng dialog
        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setPreferredSize(new Dimension(600, 300));

        // Hiển thị thông báo nếu không tìm thấy trang bị nào
        if (foundIndices.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No employee found with the name \"" + name + "\"", "Search Results", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, scrollPane, "Search Results for \"" + name + "\"", JOptionPane.PLAIN_MESSAGE);

        }
    }
}
