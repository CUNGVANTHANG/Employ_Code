package view;

import controller.PositionController;
import entity.Position;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PositionView extends JPanel {
    private JTable positionTable;
    private DefaultTableModel tableModel;

    public PositionView() {
        setLayout(new BorderLayout());

        positionTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(positionTable);
        add(scrollPane, BorderLayout.CENTER);

        // Assuming buttons for add, update, delete positions are added here
    }

    public void displayPositions(List<Position> positions) {
        String[] columnNames = {"ID", "Name", "Commission", "Allowance"};
        Object[][] data = new Object[positions.size()][4];
        for (int i = 0; i < positions.size(); i++) {
            Position position = positions.get(i);
            data[i][0] = position.getPositionID();
            data[i][1] = position.getPositionName();
            data[i][2] = position.getCommission();
            data[i][3] = position.getAllowance();
        }
        tableModel = new DefaultTableModel(data, columnNames);
        positionTable.setModel(tableModel);
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
            String equipmentName = (String) positionTable.getValueAt(i, 1);
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
                rowData[columnIndex] = positionTable.getValueAt(index, columnIndex);
            }
            resultModel.addRow(rowData);
        }
        JTable resultTable = new JTable(resultModel);

        // Tạo cửa sổ kết quả dưới dạng dialog
        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setPreferredSize(new Dimension(600, 300));

        // Hiển thị thông báo nếu không tìm thấy trang bị nào
        if (foundIndices.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No position found with the name \"" + name + "\"", "Search Results", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, scrollPane, "Search Results for \"" + name + "\"", JOptionPane.PLAIN_MESSAGE);

        }
    }

    public void showAdd(PositionController positionController) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);

        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField commissionField = new JTextField();
        JTextField allowanceField = new JTextField();

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Commission:"));
        inputPanel.add(commissionField);
        inputPanel.add(new JLabel("Allowance:"));
        inputPanel.add(allowanceField);

        int option = JOptionPane.showConfirmDialog(frame, inputPanel, "Add Position", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            double commission = Double.parseDouble(commissionField.getText().trim());
            double allowance = Double.parseDouble(allowanceField.getText().trim());

            if (positionController.isPositionIdUnique(id)) {
                // Create Position object
                Position newPosition = new Position(id, name, commission, allowance);

                // Add position to database or data structure
                // Call the controller method to add position
                positionController.addPosition(newPosition);

                // Refresh position table
            } else {
                JOptionPane.showMessageDialog(frame, "Position ID already exists. Please use a unique ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void showEdit(PositionController positionController) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        int selectedRow = positionTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a position to edit.", "No Position Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String id = (String) tableModel.getValueAt(selectedRow, 0);
        String name = (String) tableModel.getValueAt(selectedRow, 1);
        double commission = (double) tableModel.getValueAt(selectedRow, 2);
        double allowance = (double) tableModel.getValueAt(selectedRow, 3);

        JTextField idField = new JTextField(id);
        JTextField nameField = new JTextField(name);
        JTextField commissionField = new JTextField(String.valueOf(commission));
        JTextField allowanceField = new JTextField(String.valueOf(allowance));

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Commission:"));
        inputPanel.add(commissionField);
        inputPanel.add(new JLabel("Allowance:"));
        inputPanel.add(allowanceField);

        int option = JOptionPane.showConfirmDialog(frame, inputPanel, "Edit Position", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String newId = idField.getText().trim();
            String newName = nameField.getText().trim();
            double newCommission = Double.parseDouble(commissionField.getText().trim());
            double newAllowance = Double.parseDouble(allowanceField.getText().trim());

            if (!newId.equals(id)) {
                if (!positionController.isPositionIdUnique(newId)) {
                    JOptionPane.showMessageDialog(frame, "Position ID already exists. Please use a unique ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            Position oldPosition = new Position(id, name, commission, allowance);
            Position newPosition = new Position(newId, newName, newCommission, newAllowance);

            positionController.updatePosition(oldPosition, newPosition);

        }
    }

    public void showDelete(PositionController positionController) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        int selectedRow = positionTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a position to delete.", "No Position Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this position?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            String id = (String) tableModel.getValueAt(selectedRow, 0);
            String name = (String) tableModel.getValueAt(selectedRow, 1);
            double commission = (double) tableModel.getValueAt(selectedRow, 2);
            double allowance = (double) tableModel.getValueAt(selectedRow, 3);

            // Create a Position object for deletion
            Position positionToDelete = new Position(id, name, commission, allowance);

            positionController.removePosition(positionToDelete);
        }
    }
}
