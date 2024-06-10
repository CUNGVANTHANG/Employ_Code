package view;

import com.toedter.calendar.JDateChooser;
import controller.DistributionController;
import entity.Distribution;
import entity.Unit;
import func.DistributionManager;
import func.UnitManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DistributionView extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private UnitManager unitManager;
    private DistributionManager distributionManager;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public DistributionView() {
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Date");
        tableModel.addColumn("Unit");
        tableModel.addColumn("Quantity Distributed");
        tableModel.addColumn("Quantity Returned");
        tableModel.addColumn("Distribution Status");

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void displayDistributions(List<Distribution> distributions) {
        tableModel.setRowCount(0); // Clear the table

        for (Distribution distribution : distributions) {
            Object[] row = new Object[]{
                    dateFormat.format(distribution.getDistributionDate()),
                    distribution.getDistributionUnit(),
                    distribution.getQuantityDistributed(),
                    distribution.getQuantityReturned(),
                    distribution.isDistributionStatus() ? "Yes" : "No"
            };
            tableModel.addRow(row);
        }
    }

    public void displayError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showAddDistributionDialog(DistributionController distributionController) {
        try {
            unitManager = new UnitManager();
            unitManager.loadUnitList("src/main/resources/units.xml");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        JDialog dialog = new JDialog(frame, "Add Distribution", true);
        dialog.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel dateLabel = new JLabel("Date:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(dateLabel, gbc);

        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");
        gbc.gridx = 1;
        gbc.weightx = 1;
        inputPanel.add(dateChooser, gbc);

        JLabel unitLabel = new JLabel("Unit:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        inputPanel.add(unitLabel, gbc);

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

        JLabel quantityDistributedLabel = new JLabel("Quantity Distributed:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        inputPanel.add(quantityDistributedLabel, gbc);

        JTextField quantityDistributedField = new JTextField(20);
        gbc.gridx = 1;
        gbc.weightx = 1;
        inputPanel.add(quantityDistributedField, gbc);

        JLabel quantityReturnedLabel = new JLabel("Quantity Returned:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        inputPanel.add(quantityReturnedLabel, gbc);

        JTextField quantityReturnedField = new JTextField(20);
        gbc.gridx = 1;
        gbc.weightx = 1;
        inputPanel.add(quantityReturnedField, gbc);

        JLabel distributionStatusLabel = new JLabel("Distribution Status:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        inputPanel.add(distributionStatusLabel, gbc);

        JCheckBox distributionStatusCheckBox = new JCheckBox();
        gbc.gridx = 1;
        gbc.weightx = 1;
        inputPanel.add(distributionStatusCheckBox, gbc);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            try {
                Date date = dateChooser.getDate();
                Unit unit = (Unit) unitComboBox.getSelectedItem();
                int quantityDistributed = Integer.parseInt(quantityDistributedField.getText().trim());
                int quantityReturned = Integer.parseInt(quantityReturnedField.getText().trim());
                boolean distributionStatus = distributionStatusCheckBox.isSelected();

                // Kiểm tra xem đơn vị và ngày đã chọn có trùng với bất kỳ đơn vị và ngày nào khác trong bảng không
                boolean duplicateExists = false;
                for (int i = 0; i < table.getRowCount(); i++) {
                    Date existingDate = dateFormat.parse((String) table.getValueAt(i, 0));
                    String existingUnit = (String) table.getValueAt(i, 1);
                    if (existingDate.equals(date) && existingUnit.equals(unit.getName())) {
                        duplicateExists = true;
                        break;
                    }
                }

                if (duplicateExists) {
                    JOptionPane.showMessageDialog(dialog, "The selected date and unit combination already exists. Please choose a different date or unit.", "Duplicate Entry", JOptionPane.ERROR_MESSAGE);
                } else {
                    distributionController.addDistribution(new Distribution(date, unit.getName(), quantityDistributed, quantityReturned, distributionStatus));
                    dialog.dispose();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid input. Please check your data and try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
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

    public void showEditDistributionDialog(DistributionController distributionController) {
        try {
            unitManager = new UnitManager();
            unitManager.loadUnitList("src/main/resources/units.xml");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        int selectedRow = table.getSelectedRow();

        if (selectedRow != -1) {
            JDialog dialog = new JDialog(frame, "Edit Distribution", true);
            dialog.setLayout(new BorderLayout());

            JPanel inputPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JLabel dateLabel = new JLabel("Date:");
            gbc.gridx = 0;
            gbc.gridy = 0;
            inputPanel.add(dateLabel, gbc);

            JDateChooser dateChooser = new JDateChooser();
            dateChooser.setDateFormatString("dd/MM/yyyy");
            try {
                dateChooser.setDate(dateFormat.parse((String) table.getValueAt(selectedRow, 0)));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            gbc.gridx = 1;
            gbc.weightx = 1;
            inputPanel.add(dateChooser, gbc);

            JLabel unitLabel = new JLabel("Unit:");
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.weightx = 0;
            inputPanel.add(unitLabel, gbc);

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

            String unitName = (String) table.getValueAt(selectedRow, 1);
            for (Unit unit : unitManager.getUnitList()) {
                if (unit.getName().equals(unitName)) {
                    unitComboBox.setSelectedItem(unit);
                    break;
                }
            }
            gbc.gridx = 1;
            gbc.weightx = 1;
            inputPanel.add(unitComboBox, gbc);

            JLabel quantityDistributedLabel = new JLabel("Quantity Distributed:");
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.weightx = 0;
            inputPanel.add(quantityDistributedLabel, gbc);

            JTextField quantityDistributedField = new JTextField(String.valueOf(table.getValueAt(selectedRow, 2)), 20);
            gbc.gridx = 1;
            gbc.weightx = 1;
            inputPanel.add(quantityDistributedField, gbc);

            JLabel quantityReturnedLabel = new JLabel("Quantity Returned:");
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.weightx = 0;
            inputPanel.add(quantityReturnedLabel, gbc);

            JTextField quantityReturnedField = new JTextField(String.valueOf(table.getValueAt(selectedRow, 3)), 20);
            gbc.gridx = 1;
            gbc.weightx = 1;
            inputPanel.add(quantityReturnedField, gbc);

            JLabel distributionStatusLabel = new JLabel("Distribution Status:");
            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.weightx = 0;
            inputPanel.add(distributionStatusLabel, gbc);

            JCheckBox distributionStatusCheckBox = new JCheckBox();
            distributionStatusCheckBox.setSelected(((String) table.getValueAt(selectedRow, 4)).equalsIgnoreCase("Yes"));
            gbc.gridx = 1;
            gbc.weightx = 1;
            inputPanel.add(distributionStatusCheckBox, gbc);

            JButton editButton = new JButton("Edit");
            editButton.addActionListener(e -> {
                try {
                    Date date = dateChooser.getDate();
                    Unit unit = (Unit) unitComboBox.getSelectedItem();
                    int quantityDistributed = Integer.parseInt(quantityDistributedField.getText().trim());
                    int quantityReturned = Integer.parseInt(quantityReturnedField.getText().trim());
                    boolean distributionStatus = distributionStatusCheckBox.isSelected();

                    // Kiểm tra xem đơn vị và ngày đã chọn có trùng với bất kỳ đơn vị và ngày nào khác trong bảng không
                    boolean duplicateExists = false;
                    for (int i = 0; i < table.getRowCount(); i++) {
                        if (i != selectedRow) { // Bỏ qua hàng đang chỉnh sửa
                            Date existingDate = dateFormat.parse((String) table.getValueAt(i, 0));
                            String existingUnit = (String) table.getValueAt(i, 1);
                            if (existingDate.equals(date) && existingUnit.equals(unit.getName())) {
                                duplicateExists = true;
                                break;
                            }
                        }
                    }

                    if (duplicateExists) {
                        JOptionPane.showMessageDialog(dialog, "The selected date and unit combination already exists. Please choose a different date or unit.", "Duplicate Entry", JOptionPane.ERROR_MESSAGE);
                    } else {
                        Distribution oldDistribution = new Distribution(
                                dateFormat.parse((String) table.getValueAt(selectedRow, 0)),
                                (String) table.getValueAt(selectedRow, 1),
                                (int) table.getValueAt(selectedRow, 2),
                                (int) table.getValueAt(selectedRow, 3),
                                ((String) table.getValueAt(selectedRow, 4)).equalsIgnoreCase("Yes"));

                        Distribution newDistribution = new Distribution(date, unit.getName(), quantityDistributed, quantityReturned, distributionStatus);

                        distributionController.updateDistribution(oldDistribution, newDistribution);
                        dialog.dispose();
                    }
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(dialog, "Invalid date format. Please enter the date in the format dd/MM/yyyy.", "Error", JOptionPane.ERROR_MESSAGE);
                }
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
            JOptionPane.showMessageDialog(frame, "Please select a distribution to edit.", "No Distribution Selected", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void showDeleteDistributionDialog(DistributionController distributionController) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        int selectedRow = table.getSelectedRow();

        if (selectedRow != -1) {
            String dateString = (String) table.getValueAt(selectedRow, 0);
            Date date = null;
            try {
                date = dateFormat.parse(dateString);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            String unit = (String) table.getValueAt(selectedRow, 1);
            int quantityDistributed = (int) table.getValueAt(selectedRow, 2);
            int quantityReturned = (int) table.getValueAt(selectedRow, 3);
            boolean distributionStatus = ((String) table.getValueAt(selectedRow, 4)).equalsIgnoreCase("Yes");

            Distribution distributionToDelete = new Distribution(date, unit, quantityDistributed, quantityReturned, distributionStatus);

            int choice = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this distribution?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                distributionController.deleteDistribution(distributionToDelete);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a distribution to delete.", "No Distribution Selected", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void showStatistics() {
        // Tính toán các thông tin thống kê
        int totalDistributed = 0;
        int totalReturned = 0;
        int totalRecords = tableModel.getRowCount();

        for (int i = 0; i < totalRecords; i++) {
            int quantityDistributed = (int) table.getValueAt(i, 2);
            int quantityReturned = (int) table.getValueAt(i, 3);
            totalDistributed += quantityDistributed;
            totalReturned += quantityReturned;
        }

        // Hiển thị thông tin thống kê
        StringBuilder statistics = new StringBuilder();
        statistics.append("Distribution Statistics:\n");
        statistics.append("Total Records: ").append(totalRecords).append("\n");
        statistics.append("Total Quantity Distributed: ").append(totalDistributed).append("\n");
        statistics.append("Total Quantity Returned: ").append(totalReturned).append("\n");

        JOptionPane.showMessageDialog(null, statistics.toString(), "Statistics", JOptionPane.INFORMATION_MESSAGE);
    }

    public void searchByDate(String date) {
        distributionManager = new DistributionManager();
        try {
            distributionManager.loadDistributionList("src/main/resources/distributions.xml");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (date.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a search term.", "Empty Search Term", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<Distribution> searchResults = new ArrayList<>();
        Pattern pattern = Pattern.compile("^" + date);

        for (Distribution distribution : distributionManager.getDistributionList()) {
            String distributionDate = new SimpleDateFormat("dd/MM/yyyy").format(distribution.getDistributionDate());
            Matcher matcher = pattern.matcher(distributionDate);
            if (matcher.find()) {
                searchResults.add(distribution);
            }
        }

        if (searchResults.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No distribution found with the specified date.", "Search Results", JOptionPane.INFORMATION_MESSAGE);
        } else {
            showSearchResults(searchResults);
        }
    }

    public void showSearchResults(List<Distribution> searchResults) {
        // Tạo một bảng mới để hiển thị kết quả tìm kiếm
        DefaultTableModel searchTableModel = new DefaultTableModel();
        searchTableModel.addColumn("Date");
        searchTableModel.addColumn("Unit");
        searchTableModel.addColumn("Quantity Distributed");
        searchTableModel.addColumn("Quantity Returned");
        searchTableModel.addColumn("Distribution Status");

        // Thêm các dòng dữ liệu từ kết quả tìm kiếm vào bảng mới
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        for (Distribution distribution : searchResults) {
            Object[] rowData = new Object[]{
                    dateFormat.format(distribution.getDistributionDate()),
                    distribution.getDistributionUnit(),
                    distribution.getQuantityDistributed(),
                    distribution.getQuantityReturned(),
                    distribution.isDistributionStatus() ? "Yes" : "No"
            };
            searchTableModel.addRow(rowData);
        }

        // Hiển thị cửa sổ kết quả tìm kiếm
        JTable searchTable = new JTable(searchTableModel);
        JScrollPane scrollPane = new JScrollPane(searchTable);
        JOptionPane.showMessageDialog(null, scrollPane, "Search Results", JOptionPane.PLAIN_MESSAGE);
    }


}
