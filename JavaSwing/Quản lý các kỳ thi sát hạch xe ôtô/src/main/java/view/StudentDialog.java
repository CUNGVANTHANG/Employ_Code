package view;

import entity.Student;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StudentDialog extends JDialog {
    private JTextField txtName, txtCccd;
    private JComboBox<String> cmbRegisteredLicenseType, cmbResult;
    private JDateChooser dateChooser;
    private Student student;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public StudentDialog(Frame owner, String title, Student student) {
        super(owner, title, true);
        this.student = student;

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Họ và tên:"), gbc);
        gbc.gridx = 1;
        txtName = new JTextField(20);
        panel.add(txtName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("CCCD:"), gbc);
        gbc.gridx = 1;
        txtCccd = new JTextField(20);
        panel.add(txtCccd, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Loại bằng đăng ký thi:"), gbc);
        gbc.gridx = 1;
        cmbRegisteredLicenseType = new JComboBox<>(new String[]{"A1", "A2", "B1", "B2", "C1", "C2"});
        panel.add(cmbRegisteredLicenseType, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Ngày thi:"), gbc);
        gbc.gridx = 1;
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");
        panel.add(dateChooser, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Kết quả:"), gbc);
        gbc.gridx = 1;
        cmbResult = new JComboBox<>(new String[]{"Đỗ", "Trượt"});
        panel.add(cmbResult, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
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

        if (student != null) {
            txtName.setText(student.getName());
            txtCccd.setText(student.getCccd());
            cmbRegisteredLicenseType.setSelectedItem(student.getRegisteredLicenseType());
            dateChooser.setDate(student.getExamDate());
            cmbResult.setSelectedItem(student.getResult());
        }

        pack();
        setLocationRelativeTo(owner);
    }

    private void onOK() {
        if (txtName.getText() != null && txtCccd.getText() != null && dateChooser.getDate() != null) {
            String name = txtName.getText();
            String cccd = txtCccd.getText();
            String registeredLicenseType = (String) cmbRegisteredLicenseType.getSelectedItem();
            Date examDate = dateChooser.getDate();
            String result = (String) cmbResult.getSelectedItem();

            student = new Student(name, cccd, registeredLicenseType, examDate, result);
            dispose();
        } else {
            student = null;

        }
    }

    private void onCancel() {
        student = null;
        dispose();
    }

    public Student getStudent() {
        return student;
    }
}
