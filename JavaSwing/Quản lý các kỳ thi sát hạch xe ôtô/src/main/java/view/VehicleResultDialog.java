package view;

import entity.Vehicle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VehicleResultDialog extends JDialog {
    private JTable vehicleTable;

    public VehicleResultDialog(Frame owner, String title, List<Vehicle> vehicles) {
        super(owner, title, true);

        JPanel panel = new JPanel(new BorderLayout());

        String[] columnNames = {"Biển số xe", "Thông tin xe", "Hình thức thi"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        vehicleTable = new JTable(tableModel);

        updateTable(vehicles);

        JScrollPane scrollPane = new JScrollPane(vehicleTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton btnClose = new JButton("Đóng");
        btnClose.addActionListener(e -> dispose());
        panel.add(btnClose, BorderLayout.SOUTH);

        add(panel);

        pack();
        setLocationRelativeTo(owner);
    }

    private void updateTable(List<Vehicle> vehicles) {
        DefaultTableModel model = (DefaultTableModel) vehicleTable.getModel();
        model.setRowCount(0);
        for (Vehicle vehicle : vehicles) {
            Object[] row = {vehicle.getVehicleNumber(), vehicle.getVehicleInfo(), vehicle.getExamAssigned()};
            model.addRow(row);
        }
    }
}
