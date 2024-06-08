package view;

import entity.Vehicle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VehicleDialog extends JDialog {
    private JTextField txtVehicleNumber, txtVehicleInfo, txtExamAssigned;
    private JComboBox<String> cmbExamType;
    private Vehicle vehicle;

    public VehicleDialog(Frame owner, String title, Vehicle vehicle) {
        super(owner, title, true);
        this.vehicle = vehicle;

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Biển số xe:"), gbc);
        gbc.gridx = 1;
        txtVehicleNumber = new JTextField(20);
        panel.add(txtVehicleNumber, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Thông tin xe:"), gbc);
        gbc.gridx = 1;
        txtVehicleInfo = new JTextField(20);
        panel.add(txtVehicleInfo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Hình thức thi:"), gbc);
        gbc.gridx = 1;
        cmbExamType = new JComboBox<>(new String[]{"Lý thuyết", "Thực hành"});
        panel.add(cmbExamType, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel();
        JButton btnOk = new JButton("OK");
        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
        buttonPanel.add(btnOk);

        JButton btnCancel = new JButton("Hủy");
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
        buttonPanel.add(btnCancel);
        panel.add(buttonPanel, gbc);

        add(panel);

        if (vehicle != null) {
            txtVehicleNumber.setText(vehicle.getVehicleNumber());
            txtVehicleInfo.setText(vehicle.getVehicleInfo());
            cmbExamType.setSelectedItem(vehicle.getExamAssigned());
        }

        pack();
        setLocationRelativeTo(owner);
    }

    private void onOK() {
        String vehicleNumber = txtVehicleNumber.getText();
        String vehicleInfo = txtVehicleInfo.getText();
        String examAssigned = (String) cmbExamType.getSelectedItem();

        vehicle = new Vehicle(vehicleNumber, vehicleInfo, examAssigned);
        dispose();
    }

    private void onCancel() {
        vehicle = null;
        dispose();
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}
