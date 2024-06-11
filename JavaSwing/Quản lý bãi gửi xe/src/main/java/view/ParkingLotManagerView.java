package view;

import controller.ParkingLotManagerController;
import entity.ParkingLot;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ParkingLotManagerView extends JFrame {
    private ParkingLotManagerController controller;
    private ParkingLotTableModel tableModel;
    private JTable table;

    public ParkingLotManagerView(ParkingLotManagerController controller) {
        this.controller = controller;
        this.tableModel = new ParkingLotTableModel(controller.getAllParkingLots());
        initUI();
    }

    private void initUI() {
        setTitle("Quản lý các bãi đỗ xe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        JButton addButton = new JButton("Thêm");
        JButton editButton = new JButton("Chỉnh sửa");
        JButton removeButton = new JButton("Xóa");
        JButton manageButton = new JButton("Quản lý bãi");

        controlPanel.add(addButton);
        controlPanel.add(editButton);
        controlPanel.add(removeButton);
        controlPanel.add(manageButton);

        add(controlPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddDialog();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showEditDialog();
            }
        });



        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    String id = (String) tableModel.getValueAt(selectedRow, 0);
                    controller.removeParkingLot(id);
                    tableModel.updateData(controller.getAllParkingLots());
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a row to remove.");
                }
            }
        });

        manageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    String id = (String) tableModel.getValueAt(selectedRow, 0);
                    ParkingLot parkingLot = controller.getParkingLotById(id);
                    if (parkingLot != null) {
                        new ParkingLotView(parkingLot, id).display();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a row to manage.");
                }
            }
        });
    }

    private void showEditDialog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String id = (String) tableModel.getValueAt(selectedRow, 0);
            ParkingLot parkingLot = controller.getParkingLotById(id);

            JTextField idField = new JTextField(parkingLot.getId());
            JTextField nameField = new JTextField(parkingLot.getName());
            JTextField locationField = new JTextField(parkingLot.getLocation());
            JTextField vehicleCountField = new JTextField(String.valueOf(parkingLot.getVehicleCount()));

            Object[] message = {
                    "ID:", idField,
                    "Tên bãi:", nameField,
                    "Địa điểm:", locationField,
                    "Sức chứa:", vehicleCountField
            };

            int option = JOptionPane.showConfirmDialog(null, message, "Chỉnh sửa", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String name = nameField.getText();
                String location = locationField.getText();
                int vehicleCount = Integer.parseInt(vehicleCountField.getText());

                if (name != null && location != null) {
                    if (!id.equals(idField.getText())) {
                        // ID has changed
                        if (!controller.isParkingLotIdExists(idField.getText())) {
                            // New ID does not exist
                            parkingLot.setId(idField.getText());
                            parkingLot.setName(name);
                            parkingLot.setLocation(location);
                            parkingLot.setVehicleCount(vehicleCount);
                            controller.updateParkingLot(parkingLot);
                            tableModel.updateData(controller.getAllParkingLots());
                        } else {
                            JOptionPane.showMessageDialog(null, "New ID already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                            showEditDialog();
                        }
                    } else {
                        // ID has not changed
                        parkingLot.setName(name);
                        parkingLot.setLocation(location);
                        parkingLot.setVehicleCount(vehicleCount);
                        controller.updateParkingLot(parkingLot);
                        tableModel.updateData(controller.getAllParkingLots());
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a row to edit.");
        }
    }

    private void showAddDialog() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField locationField = new JTextField();
        JTextField vehicleCountField = new JTextField();

        Object[] message = {
                "ID:", idField,
                "Tên bãi:", nameField,
                "Địa điểm:", locationField,
                "Sức chứa:", vehicleCountField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Thêm", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String id = idField.getText();
            String name = nameField.getText();
            String location = locationField.getText();
            int vehicleCount = Integer.parseInt(vehicleCountField.getText());

            if (id != null && name != null && location != null) {
                if (!controller.isParkingLotIdExists(id)) {
                    ParkingLot parkingLot = new ParkingLot(id, name, location, vehicleCount);
                    controller.addParkingLot(parkingLot);
                    tableModel.updateData(controller.getAllParkingLots());
                } else {
                    JOptionPane.showMessageDialog(null, "ID already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                    showAddDialog();
                }
            }
        }
    }

    private static class ParkingLotTableModel extends AbstractTableModel {
        private final String[] columnNames = {"ID", "Tên bãi", "Địa điểm", "Sức chứa"};
        private List<ParkingLot> parkingLots;

        public ParkingLotTableModel(List<ParkingLot> parkingLots) {
            this.parkingLots = parkingLots;
        }

        @Override
        public int getRowCount() {
            return parkingLots.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            ParkingLot parkingLot = parkingLots.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return parkingLot.getId();
                case 1:
                    return parkingLot.getName();
                case 2:
                    return parkingLot.getLocation();
                case 3:
                    return parkingLot.getVehicleCount();
                default:
                    return null;
            }
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        public void updateData(List<ParkingLot> parkingLots) {
            this.parkingLots = parkingLots;
            fireTableDataChanged();
        }
    }
}
