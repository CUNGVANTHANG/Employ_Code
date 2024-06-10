package view;

import controller.SoldierController;
import entity.Soldier;
import entity.Unit;
import func.UnitManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SoldierView extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private UnitManager unitManager;

    public SoldierView() {
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Full Name");
        tableModel.addColumn("Unit");
        tableModel.addColumn("Gender");
        tableModel.addColumn("Rank");

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void displaySoldiers(List<Soldier> soldiers) {
        tableModel.setRowCount(0); // Clear the table

        for (Soldier soldier : soldiers) {
            Object[] row = new Object[]{
                    soldier.getId(),
                    soldier.getFullName(),
                    soldier.getUnit(),
                    soldier.getGender(),
                    soldier.getRank()
            };
            tableModel.addRow(row);
        }
    }

    public void displayError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showAddSoldierDialog(SoldierController soldierController) {
        try {
            unitManager = new UnitManager();
            unitManager.loadUnitList("src/main/resources/units.xml");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        JDialog dialog = new JDialog(frame, "Add Soldier", true);
        dialog.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel fullNameLabel = new JLabel("Full Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(fullNameLabel, gbc);

        JTextField fullNameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.weightx = 1;
        inputPanel.add(fullNameField, gbc);

        JLabel idLabel = new JLabel("ID:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        inputPanel.add(idLabel, gbc);

        JTextField idField = new JTextField(20);
        gbc.gridx = 1;
        gbc.weightx = 1;
        inputPanel.add(idField, gbc);

        JLabel unitLabel = new JLabel("Unit:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        inputPanel.add(unitLabel, gbc);

        // Thêm JComboBox<Unit> để chọn đơn vị
        JComboBox<Unit> unitComboBox = new JComboBox<>(unitManager.getUnitList().toArray(new Unit[0]));
        unitComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Unit) {
                    setText(((Unit) value).getName());
                }
                return c;
            }
        });
        gbc.gridx = 1;
        gbc.weightx = 1;
        inputPanel.add(unitComboBox, gbc);

        JLabel genderLabel = new JLabel("Gender:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        inputPanel.add(genderLabel, gbc);

        String[] genders = {"Male", "Female"};
        JComboBox<String> genderComboBox = new JComboBox<>(genders);
        // Chọn giới tính tương ứng
        genderComboBox.setSelectedItem(genders[0]);
        gbc.gridx = 1;
        gbc.weightx = 1;
        inputPanel.add(genderComboBox, gbc);

        JLabel rankLabel = new JLabel("Rank:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        inputPanel.add(rankLabel, gbc);

        JTextField rankField = new JTextField(20);
        gbc.gridx = 1;
        gbc.weightx = 1;
        inputPanel.add(rankField, gbc);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fullName = fullNameField.getText().trim();
                String id = idField.getText().trim();
                Unit unit = (Unit) unitComboBox.getSelectedItem();
                String gender = genderComboBox.getSelectedItem().toString();
                String rank = rankField.getText().trim();

                // Kiểm tra xem ID đã tồn tại hay chưa
                boolean idExists = soldierController.isSoldierIdExists(id);

                if (idExists) {
                    JOptionPane.showMessageDialog(dialog, "ID already exists. Please use a different ID.", "Duplicate ID", JOptionPane.WARNING_MESSAGE);
                } else {
                    Soldier newSoldier = new Soldier(fullName, id, unit.getName(), gender, rank);
                    soldierController.addSoldier(newSoldier);
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
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);

        dialog.add(inputPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    public void showEditSoldierDialog(SoldierController soldierController) {
        try {
            unitManager = new UnitManager();
            unitManager.loadUnitList("src/main/resources/units.xml");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        JDialog dialog = new JDialog(frame, "Edit Soldier", true);
        dialog.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int selectedRow = table.getSelectedRow();

        if (selectedRow != -1) {
            String fullName = (String) table.getValueAt(selectedRow, 1);
            String id = (String) table.getValueAt(selectedRow, 0);
            String unit = (String) table.getValueAt(selectedRow, 2);
            String gender = (String) table.getValueAt(selectedRow, 3);
            String rank = (String) table.getValueAt(selectedRow, 4);

            JLabel fullNameLabel = new JLabel("Full Name:");
            gbc.gridx = 0;
            gbc.gridy = 0;
            inputPanel.add(fullNameLabel, gbc);

            JTextField fullNameField = new JTextField(fullName, 20);
            gbc.gridx = 1;
            gbc.weightx = 1;
            inputPanel.add(fullNameField, gbc);

            JLabel idLabel = new JLabel("ID:");
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.weightx = 0;
            inputPanel.add(idLabel, gbc);

            JTextField idField = new JTextField(id, 20);
            gbc.gridx = 1;
            gbc.weightx = 1;
            inputPanel.add(idField, gbc);

            JLabel unitLabel = new JLabel("Unit:");
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.weightx = 0;
            inputPanel.add(unitLabel, gbc);

            // Thêm JComboBox<Unit> để chọn đơn vị
            JComboBox<Unit> unitComboBox = new JComboBox<>(unitManager.getUnitList().toArray(new Unit[0]));
            unitComboBox.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value instanceof Unit) {
                        setText(((Unit) value).getName());
                    }
                    return c;
                }
            });
            gbc.gridx = 1;
            gbc.weightx = 1;
            inputPanel.add(unitComboBox, gbc);

            JLabel genderLabel = new JLabel("Gender:");
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.weightx = 0;
            inputPanel.add(genderLabel, gbc);

            String[] genders = {"Male", "Female"};
            JComboBox<String> genderComboBox = new JComboBox<>(genders);
            // Chọn giới tính tương ứng
            genderComboBox.setSelectedItem(gender);
            gbc.gridx = 1;
            gbc.weightx = 1;
            inputPanel.add(genderComboBox, gbc);

            JLabel rankLabel = new JLabel("Rank:");
            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.weightx = 0;
            inputPanel.add(rankLabel, gbc);

            JTextField rankField = new JTextField(rank, 20);
            gbc.gridx = 1;
            gbc.weightx = 1;
            inputPanel.add(rankField, gbc);

            JButton editButton = new JButton("Edit");
            editButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String newFullName = fullNameField.getText().trim();
                    String newId = idField.getText().trim();
                    Unit newUnit = (Unit) unitComboBox.getSelectedItem();
                    String newGender = genderComboBox.getSelectedItem().toString();
                    String newRank = rankField.getText().trim();

                    if (!newId.equals(id)) { // Nếu ID mới khác với ID cũ
                        if (soldierController.isSoldierIdExists(newId)) {
                            JOptionPane.showMessageDialog(dialog, "ID already exists. Please choose a different one.", "Error", JOptionPane.ERROR_MESSAGE);
                            return; // Dừng việc cập nhật nếu ID mới đã tồn tại
                        }
                    }

                    Soldier oldSoldier = new Soldier(fullName, id, unit, gender, rank);
                    Soldier newSoldier = new Soldier(newFullName, newId, newUnit.getName(), newGender, newRank);
                    soldierController.updateSoldier(oldSoldier, newSoldier);
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
            buttonPanel.add(editButton);
            buttonPanel.add(cancelButton);

            dialog.add(inputPanel, BorderLayout.CENTER);
            dialog.add(buttonPanel, BorderLayout.SOUTH);
            dialog.pack();
            dialog.setLocationRelativeTo(frame);
            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a soldier to edit.", "No Soldier Selected", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void showDeleteSoldierDialog(SoldierController soldierController) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);

        int selectedRow = table.getSelectedRow();

        if (selectedRow != -1) {
            String fullName = (String) table.getValueAt(selectedRow, 1);
            String id = (String) table.getValueAt(selectedRow, 0);
            String unit = (String) table.getValueAt(selectedRow, 2);
            String gender = (String) table.getValueAt(selectedRow, 3);
            String rank = (String) table.getValueAt(selectedRow, 4);

            Soldier soldierToDelete = new Soldier(fullName, id, unit, gender, rank);

            int choice = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this soldier?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                soldierController.deleteSoldier(soldierToDelete);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a soldier to delete.", "No Soldier Selected", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void showStatistics() {
        int totalSoldiers = tableModel.getRowCount();

        // Hiển thị thông tin thống kê
        StringBuilder statistics = new StringBuilder();
        statistics.append("Soldier Statistics:\n");
        statistics.append("Total Soldiers: ").append(totalSoldiers).append("\n");

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
            String soldierName = (String) table.getValueAt(i, 1);
            Matcher matcher = pattern.matcher(soldierName);
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

        // Hiển thị thông báo nếu không tìm thấy lính nào
        if (foundIndices.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No soldier found with the name \"" + name + "\"", "Search Results", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, scrollPane, "Search Results for \"" + name + "\"", JOptionPane.PLAIN_MESSAGE);
        }
    }


}