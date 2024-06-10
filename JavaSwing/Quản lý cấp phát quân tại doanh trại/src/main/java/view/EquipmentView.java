package view;

import controller.EquipmentController;
import entity.Equipment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EquipmentView extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;

    public EquipmentView() {
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Name");
        tableModel.addColumn("Category");

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void showAddEquipmentDialog(EquipmentController equipmentController) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        JDialog dialog = new JDialog(frame, "Add Equipment", true);
        dialog.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(nameLabel, gbc);

        JTextField nameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.weightx = 1;
        inputPanel.add(nameField, gbc);

        JLabel categoryLabel = new JLabel("Category:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        inputPanel.add(categoryLabel, gbc);

        JTextField categoryField = new JTextField(20);
        gbc.gridx = 1;
        gbc.weightx = 1;
        inputPanel.add(categoryField, gbc);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String category = categoryField.getText().trim();
            equipmentController.addEquipment(new Equipment(name, category));
            dialog.dispose();
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dialog.dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);

        dialog.add(inputPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    public void showEditEquipmentDialog(EquipmentController equipmentController) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        JDialog dialog = new JDialog(frame, "Edit Equipment", true);
        dialog.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int selectedRow = table.getSelectedRow();

        if (selectedRow != -1) {
            String name = (String) table.getValueAt(selectedRow, 0);
            String category = (String) table.getValueAt(selectedRow, 1);

            JLabel nameLabel = new JLabel("Name:");
            gbc.gridx = 0;
            gbc.gridy = 0;
            inputPanel.add(nameLabel, gbc);

            JTextField nameField = new JTextField(name, 20);
            gbc.gridx = 1;
            gbc.weightx = 1;
            inputPanel.add(nameField, gbc);

            JLabel categoryLabel = new JLabel("Category:");
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.weightx = 0;
            inputPanel.add(categoryLabel, gbc);

            JTextField categoryField = new JTextField(category, 20);
            gbc.gridx = 1;
            gbc.weightx = 1;
            inputPanel.add(categoryField, gbc);

            JButton editButton = new JButton("Edit");
            editButton.addActionListener(e -> {
                String newName = nameField.getText().trim();
                String newCategory = categoryField.getText().trim();
                equipmentController.updateEquipment(new Equipment(name, category), new Equipment(newName, newCategory));
                dialog.dispose();
            });

            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(e -> dialog.dispose());

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.add(editButton);
            buttonPanel.add(cancelButton);

            dialog.add(inputPanel, BorderLayout.CENTER);
            dialog.add(buttonPanel, BorderLayout.SOUTH);
            dialog.pack();
            dialog.setLocationRelativeTo(frame);
            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(frame, "Please select an equipment to edit.", "No Equipment Selected", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void showDeleteEquipmentDialog(EquipmentController equipmentController) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);

        int selectedRow = table.getSelectedRow();

        if (selectedRow != -1) {
            String name = (String) table.getValueAt(selectedRow, 0);

            int choice = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this equipment?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                equipmentController.deleteEquipment(new Equipment(name, ""));
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please select an equipment to delete.", "No Equipment Selected", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void displayEquipments(List<Equipment> equipments) {
        tableModel.setRowCount(0); // Clear the table

        for (Equipment equipment : equipments) {
            Object[] row = new Object[]{
                    equipment.getName(),
                    equipment.getCategory()
            };
            tableModel.addRow(row);
        }
    }

    public void displayError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showStatistics() {
        int totalEquipment = tableModel.getRowCount();

        // Hiển thị thông tin thống kê
        StringBuilder statistics = new StringBuilder();
        statistics.append("Equipment Statistics:\n");
        statistics.append("Total Equipment Categories: ").append(totalEquipment).append("\n");

        JOptionPane.showMessageDialog(null, statistics.toString(), "Statistics", JOptionPane.INFORMATION_MESSAGE);
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
            String equipmentName = (String) table.getValueAt(i, 0);
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
                rowData[columnIndex] = table.getValueAt(index, columnIndex);
            }
            resultModel.addRow(rowData);
        }
        JTable resultTable = new JTable(resultModel);

        // Tạo cửa sổ kết quả dưới dạng dialog
        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setPreferredSize(new Dimension(600, 300));

        // Hiển thị thông báo nếu không tìm thấy trang bị nào
        if (foundIndices.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No equipment found with the name \"" + name + "\"", "Search Results", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, scrollPane, "Search Results for \"" + name + "\"", JOptionPane.PLAIN_MESSAGE);

        }
    }


}
