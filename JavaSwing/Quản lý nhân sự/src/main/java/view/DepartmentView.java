package view;

import controller.DepartmentController;
import entity.Department;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DepartmentView extends JPanel {
    private JTable departmentTable;
    private DefaultTableModel tableModel;

    public DepartmentView() {
        setLayout(new BorderLayout());

        departmentTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(departmentTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void displayDepartments(List<Department> departments) {
        String[] columnNames = {"ID", "Name"};
        Object[][] data = new Object[departments.size()][2];
        for (int i = 0; i < departments.size(); i++) {
            Department department = departments.get(i);
            data[i][0] = department.getDepartmentID();
            data[i][1] = department.getDepartmentName();
        }
        tableModel = new DefaultTableModel(data, columnNames);
        departmentTable.setModel(tableModel);
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
            String equipmentName = (String) departmentTable.getValueAt(i, 1);
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
                rowData[columnIndex] = departmentTable.getValueAt(index, columnIndex);
            }
            resultModel.addRow(rowData);
        }
        JTable resultTable = new JTable(resultModel);

        // Tạo cửa sổ kết quả dưới dạng dialog
        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setPreferredSize(new Dimension(600, 300));

        // Hiển thị thông báo nếu không tìm thấy trang bị nào
        if (foundIndices.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No department found with the name \"" + name + "\"", "Search Results", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, scrollPane, "Search Results for \"" + name + "\"", JOptionPane.PLAIN_MESSAGE);

        }
    }

    public void showAdd(DepartmentController departmentController) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        JDialog addDialog = new JDialog(frame, "Add Department", true);
        addDialog.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("ID:"));
        JTextField idField = new JTextField();
        inputPanel.add(idField);

        inputPanel.add(new JLabel("Name:"));
        JTextField nameField = new JTextField();
        inputPanel.add(nameField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String id = idField.getText().trim();
                    String name = nameField.getText().trim();

                    if (!departmentController.isDepartmentIdUnique(id)) {
                        Department department = new Department(id, name);

                        departmentController.addDepartment(department);

                        addDialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(addDialog, "Department ID already exists. Please use a unique ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
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

        addDialog.setSize(300, 150);
        addDialog.setLocationRelativeTo(frame);
        addDialog.setVisible(true);
    }

    public void showEdit(DepartmentController departmentController) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        int selectedRow = departmentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a department to edit.", "No Department Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String id = (String) tableModel.getValueAt(selectedRow, 0);
        String name = (String) tableModel.getValueAt(selectedRow, 1);

        JTextField idField = new JTextField(id);
        JTextField nameField = new JTextField(name);

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);

        int option = JOptionPane.showConfirmDialog(frame, inputPanel, "Edit Department", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String newId = idField.getText().trim();
            String newName = nameField.getText().trim();

            // Create Department objects for old and new data
            Department oldDepartment = new Department(id, name);
            Department newDepartment = new Department(newId, newName);

            // Update department in database or data structure
            // Call the controller method to update department
            departmentController.editDepartment(oldDepartment, newDepartment);

            // Refresh department table
        }
    }

    public void showDelete(DepartmentController departmentController) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        int selectedRow = departmentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a department to delete.", "No Department Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this department?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            String id = (String) tableModel.getValueAt(selectedRow, 0);
            String name = (String) tableModel.getValueAt(selectedRow, 1);

            // Create a Department object for deletion
            Department departmentToDelete = new Department(id, name);

            // Delete department from database or data structure
            // Call the controller method to delete department
            departmentController.deleteDepartment(departmentToDelete);

            // Refresh department table
        }
    }

}
