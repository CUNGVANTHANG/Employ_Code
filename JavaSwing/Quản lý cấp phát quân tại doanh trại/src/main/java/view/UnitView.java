package view;

import controller.UnitController;
import entity.Unit;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnitView extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;

    public UnitView() {
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Soldier Count");

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void displayUnits(List<Unit> units) {
        tableModel.setRowCount(0); // Clear the table

        for (Unit unit : units) {
            Object[] row = new Object[]{
                    unit.getId(),
                    unit.getName(),
                    unit.getSoldierCount()
            };
            tableModel.addRow(row);
        }
    }

    public void displayError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showAddUnitDialog(UnitController unitController) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        JDialog dialog = new JDialog(frame, "Add Unit", true);
        dialog.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel idLabel = new JLabel("ID:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(idLabel, gbc);

        JTextField idField = new JTextField(20);
        gbc.gridx = 1;
        gbc.weightx = 1;
        inputPanel.add(idField, gbc);

        JLabel nameLabel = new JLabel("Name:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        inputPanel.add(nameLabel, gbc);

        JTextField nameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.weightx = 1;
        inputPanel.add(nameField, gbc);

        JLabel soldierCountLabel = new JLabel("Soldier Count:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        inputPanel.add(soldierCountLabel, gbc);

        JTextField soldierCountField = new JTextField(20);
        gbc.gridx = 1;
        gbc.weightx = 1;
        inputPanel.add(soldierCountField, gbc);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText().trim();
                String name = nameField.getText().trim();
                int soldierCount = Integer.parseInt(soldierCountField.getText().trim());

                if (unitController.isUnitIdExists(id)) {
                    JOptionPane.showMessageDialog(dialog, "ID already exists. Please use a different ID.", "Duplicate ID", JOptionPane.ERROR_MESSAGE);
                    return; // Dừng việc thêm đơn vị
                }

                Unit newUnit = new Unit(name, id, soldierCount);
                unitController.addUnitToXML(newUnit);
                dialog.dispose();
            }
        });


        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);

        dialog.add(inputPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    public void showEditUnitDialog(UnitController unitController) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        JDialog dialog = new JDialog(frame, "Edit Unit", true);
        dialog.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int selectedRow = table.getSelectedRow();

        if (selectedRow != -1) {
            String id = (String) table.getValueAt(selectedRow, 0);
            String name = (String) table.getValueAt(selectedRow, 1);
            int soldierCount = (Integer) table.getValueAt(selectedRow, 2);

            JLabel idLabel = new JLabel("ID:");
            gbc.gridx = 0;
            gbc.gridy = 0;
            inputPanel.add(idLabel, gbc);

            JTextField idField = new JTextField(id, 20);
            gbc.gridx = 1;
            gbc.weightx = 1;
            inputPanel.add(idField, gbc);

            JLabel nameLabel = new JLabel("Name:");
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.weightx = 0;
            inputPanel.add(nameLabel, gbc);

            JTextField nameField = new JTextField(name, 20);
            gbc.gridx = 1;
            gbc.weightx = 1;
            inputPanel.add(nameField, gbc);

            JLabel soldierCountLabel = new JLabel("Soldier Count:");
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.weightx = 0;
            inputPanel.add(soldierCountLabel, gbc);

            JTextField soldierCountField = new JTextField(String.valueOf(soldierCount), 20);
            gbc.gridx = 1;
            gbc.weightx = 1;
            inputPanel.add(soldierCountField, gbc);

            JButton editButton = new JButton("Edit");
            editButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String newId = idField.getText().trim();
                    String newName = nameField.getText().trim();
                    int newSoldierCount = Integer.parseInt(soldierCountField.getText().trim());

                    // Kiểm tra xem ID mới có trùng với các ID khác không
                    if (unitController.isUnitIdExists(newId)) {
                        // ID mới đã tồn tại, hiển thị thông báo và không thực hiện chỉnh sửa
                        JOptionPane.showMessageDialog(dialog, "ID already exists. Please choose a different ID.", "Duplicate ID", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // ID mới không trùng, tiến hành chỉnh sửa
                        Unit oldUnit = new Unit(name, id, soldierCount);
                        Unit newUnit = new Unit(newName, newId, newSoldierCount);
                        unitController.updateUnitInXML(oldUnit, newUnit);
                        dialog.dispose();
                    }
                }
            });

            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dialog.dispose();
                }
            });

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.add(editButton);
            buttonPanel.add(cancelButton);

            dialog.add(inputPanel, BorderLayout.CENTER);
            dialog.add(buttonPanel, BorderLayout.SOUTH);
            dialog.pack();
            dialog.setLocationRelativeTo(frame);
            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a unit to edit.", "No Unit Selected", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void showDeleteUnitDialog(UnitController unitController) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);

        int selectedRow = table.getSelectedRow();

        if (selectedRow != -1) {
            String id = (String) table.getValueAt(selectedRow, 1);
            String name = (String) table.getValueAt(selectedRow, 0);
            int soldierCount = (Integer) table.getValueAt(selectedRow, 2);

            Unit unitToDelete = new Unit(id, name, soldierCount);

            int choice = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this unit?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                unitController.deleteUnitFromXML(unitToDelete);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a unit to delete.", "No Unit Selected", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void showStatistics() {
        int totalUnits = tableModel.getRowCount();

        // Hiển thị thông tin thống kê
        StringBuilder statistics = new StringBuilder();
        statistics.append("Unit Statistics:\n");
        statistics.append("Total Units: ").append(totalUnits).append("\n");

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
            String unitName = (String) table.getValueAt(i, 1);
            Matcher matcher = pattern.matcher(unitName);
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

        // Hiển thị thông báo nếu không tìm thấy đơn vị nào
        if (foundIndices.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No unit found with the name \"" + name + "\"", "Search Results", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, scrollPane, "Search Results for \"" + name + "\"", JOptionPane.PLAIN_MESSAGE);

        }
    }

}