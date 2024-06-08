package view;

import entity.Exam;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExamDialog extends JDialog {
    private JDateChooser dateChooser;
    private JTextField txtLocation, txtVehicleCount;
    private JComboBox<String> cmbLicenseType, cmbExamForm;
    private Exam exam;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static final String[] LICENSE_TYPES = {"B1", "B2", "A1", "A2", "C1", "C2"};
    private static final String[] EXAM_FORMS = {"Thực hành", "Lý thuyết"};

    public ExamDialog(Frame owner, String title, Exam exam) {
        super(owner, title, true);
        this.exam = exam;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Ngày thi
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Ngày thi:"), gbc);
        gbc.gridx = 1;
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");
        add(dateChooser, gbc);

        // Địa điểm
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Địa điểm:"), gbc);
        gbc.gridx = 1;
        txtLocation = new JTextField(20);
        add(txtLocation, gbc);

        // Số lượng xe
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Số lượng xe:"), gbc);
        gbc.gridx = 1;
        txtVehicleCount = new JTextField(20);
        add(txtVehicleCount, gbc);

        // Loại bằng
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Loại bằng:"), gbc);
        gbc.gridx = 1;
        cmbLicenseType = new JComboBox<>(LICENSE_TYPES);
        add(cmbLicenseType, gbc);

        // Hình thức thi
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Hình thức thi:"), gbc);
        gbc.gridx = 1;
        cmbExamForm = new JComboBox<>(EXAM_FORMS);
        add(cmbExamForm, gbc);

        // Buttons
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
        add(buttonPanel, gbc);

        if (exam != null) {
            dateChooser.setDate(exam.getDate());
            txtLocation.setText(exam.getLocation());
            txtVehicleCount.setText(String.valueOf(exam.getVehicleCount()));
            cmbLicenseType.setSelectedItem(exam.getLicenseType());
            cmbExamForm.setSelectedItem(exam.getExamForm());
        }

        setPreferredSize(new Dimension(400, 400));
        pack();
        setLocationRelativeTo(owner);
    }

    private void onOK() {
        if (dateChooser.getDate() != null && txtLocation.getText() != null && txtVehicleCount.getText() != null) {
            Date date = dateChooser.getDate();
            String location = txtLocation.getText();
            int vehicleCount = Integer.parseInt(txtVehicleCount.getText());
            String licenseType = (String) cmbLicenseType.getSelectedItem();
            String examForm = (String) cmbExamForm.getSelectedItem();
            exam = new Exam(date, location, vehicleCount, licenseType, examForm);
            dispose();
        } else {
            exam = null;
        }
    }

    private void onCancel() {
        exam = null;
        dispose();
    }

    public Exam getExam() {
        return exam;
    }
}