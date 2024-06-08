package view;

import entity.Student;
import entity.Vehicle;
import func.VehicleManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class VehicleManagementView extends JFrame {
    private VehicleManager vehicleManager;
    private JTable vehicleTable;
    private DefaultTableModel tableModel;

    public VehicleManagementView(VehicleManager vehicleManager) {
        this.vehicleManager = vehicleManager;

        setTitle("Quản lý xe");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setupUI();
    }

    private void setupUI() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columnNames = {"Biển số xe", "Thông tin xe", "Hình thức thi"};
        tableModel = new DefaultTableModel(columnNames, 0);
        vehicleTable = new JTable(tableModel);

        updateTable();

        JScrollPane scrollPane = new JScrollPane(vehicleTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        JButton btnAdd = new JButton("Thêm");
        JButton btnEdit = new JButton("Sửa");
        JButton btnDelete = new JButton("Xóa");

        JLabel lblSearch = new JLabel("Tìm kiếm theo biển số:");

        JTextField txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(150, 25));

        JButton btnSearch = new JButton("Tìm kiếm");

        JButton btnStatistic = new JButton("Thống kê");

        // Xử lý sự kiện thống kê
        btnStatistic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statisticTotalVehicles();
            }
        });

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addVehicle();
            }
        });

        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editVehicle();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteVehicle();
            }
        });

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String number = txtSearch.getText();
                if (!number.isEmpty()) {
                    searchByNumber(number);
                } else {
                    updateTable(); // Nếu không có ngày được nhập, hiển thị lại toàn bộ dữ liệu
                }
            }
        });

        controlPanel.add(lblSearch);
        controlPanel.add(txtSearch);
        controlPanel.add(btnSearch);
        controlPanel.add(btnStatistic);
        controlPanel.add(btnAdd);
        controlPanel.add(btnEdit);
        controlPanel.add(btnDelete);

        panel.add(controlPanel, BorderLayout.SOUTH);

        add(panel);
    }

    private void searchByNumber(String number) {
        List<Vehicle> results = vehicleManager.searchByNumber(number);
        if (!results.isEmpty()) {
            VehicleResultDialog resultDialog = new VehicleResultDialog(this, "Kết quả tìm kiếm", results);
            resultDialog.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(null, "Không tìm thấy biển số " + number, "Kết quả tìm kiếm", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void statisticTotalVehicles() {
        int totalVehicles = vehicleManager.statisticTotalVehicles();
        JOptionPane.showMessageDialog(null, "Tổng số lượng xe: " + totalVehicles, "Thống kê", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        for (Vehicle vehicle : vehicleManager.getVehicles()) {
            Object[] row = {vehicle.getVehicleNumber(), vehicle.getVehicleInfo(), vehicle.getExamAssigned()};
            tableModel.addRow(row);
        }
    }

    private void addVehicle() {
        VehicleDialog dialog = new VehicleDialog(this, "Thêm xe mới", null);
        dialog.setVisible(true);
        Vehicle newVehicle = dialog.getVehicle();
        if (newVehicle != null) {
            if (vehicleManager.isDuplicateVehicleNumber(newVehicle.getVehicleNumber())) {
                JOptionPane.showMessageDialog(this, "Biển số xe đã tồn tại. Vui lòng nhập biển số xe khác.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else {
                vehicleManager.addVehicle(newVehicle);
                updateTable();
            }
        }
    }

    private void editVehicle() {
        int selectedRow = vehicleTable.getSelectedRow();
        if (selectedRow >= 0) {
            Vehicle existingVehicle = vehicleManager.getVehicles().get(selectedRow);
            VehicleDialog dialog = new VehicleDialog(this, "Sửa xe", existingVehicle);
            dialog.setVisible(true);
            Vehicle updatedVehicle = dialog.getVehicle();
            if (updatedVehicle != null) {
                if (!existingVehicle.getVehicleNumber().equals(updatedVehicle.getVehicleNumber()) && vehicleManager.isDuplicateVehicleNumber(updatedVehicle.getVehicleNumber())) {
                    JOptionPane.showMessageDialog(this, "Biển số xe đã tồn tại. Vui lòng nhập biển số xe khác.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                } else {
                    vehicleManager.updateVehicle(selectedRow, updatedVehicle);
                    updateTable();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn xe để sửa");
        }
    }


    private void deleteVehicle() {
        int selectedRow = vehicleTable.getSelectedRow();
        if (selectedRow >= 0) {
            vehicleManager.deleteVehicle(selectedRow);
            updateTable();
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn xe để xóa");
        }
    }
}
