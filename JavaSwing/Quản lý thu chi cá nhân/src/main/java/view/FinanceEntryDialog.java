package view;

import com.toedter.calendar.JDateChooser;
import entity.FinanceEntry;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FinanceEntryDialog extends JDialog {
    private JTextField txtAmount;
    private JDateChooser dateChooser;
    private JTextArea txtNotes;
    private JRadioButton rbtnIncome;
    private JRadioButton rbtnExpense;
    private FinanceManagementView parentView;
    private FinanceEntry entry;

    public FinanceEntryDialog(FinanceManagementView parent) {
        super(parent, "Thêm mục nhập", true);
        this.parentView = parent;
        initialize();
        setSize(400, 400);
        setLocationRelativeTo(parent);
    }

    public FinanceEntryDialog(FinanceManagementView parent, FinanceEntry entry) {
        super(parent, "Chỉnh sửa mục nhập", true);
        this.parentView = parent;
        this.entry = entry;
        initialize();
        populateFields();
        setSize(400, 400);
        setLocationRelativeTo(parent);
    }


    private void initialize() {
        setLayout(new BorderLayout(10, 10));

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Panel Loại mục nhập
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Loại mục nhập:"), gbc);

        rbtnIncome = new JRadioButton("Thu nhập");
        rbtnExpense = new JRadioButton("Chi tiêu");
        ButtonGroup group = new ButtonGroup();
        group.add(rbtnIncome);
        group.add(rbtnExpense);
        rbtnIncome.setSelected(true);

        JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        typePanel.add(rbtnIncome);
        typePanel.add(rbtnExpense);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(typePanel, gbc);

        // Panel Số tiền
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Số tiền:"), gbc);

        txtAmount = new JTextField();
        txtAmount.setPreferredSize(new Dimension(250, 25));
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(txtAmount, gbc);

        // Panel Ngày
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Ngày:"), gbc);

        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");
        dateChooser.setDate(new Date());
        dateChooser.setPreferredSize(new Dimension(250, 25));
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(dateChooser, gbc);

        // Panel Ghi chú
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Ghi chú:"), gbc);

        txtNotes = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(txtNotes);
        scrollPane.setPreferredSize(new Dimension(250, 100));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridheight = 2;
        panel.add(scrollPane, gbc);

        add(panel, BorderLayout.CENTER);

        // Panel nút Lưu và Hủy
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSave = new JButton("Lưu");
        JButton btnCancel = new JButton("Hủy");

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        add(buttonPanel, BorderLayout.SOUTH);

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveEntry();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void populateFields() {
        if (entry != null) {
            rbtnIncome.setSelected(entry.isIncome());
            rbtnExpense.setSelected(!entry.isIncome());
            txtAmount.setText(String.valueOf(entry.getAmount()));
            dateChooser.setDate(entry.getDate());
            txtNotes.setText(entry.getNotes());
        }
    }

    private void saveEntry() {
        try {
            double amount = Double.parseDouble(txtAmount.getText());
            Date date = dateChooser.getDate();
            String notes = txtNotes.getText();
            boolean isIncome = rbtnIncome.isSelected();

            if (entry == null) {
                // Thêm mục nhập mới
                FinanceEntry newEntry = new FinanceEntry(isIncome, amount, date, notes);
                parentView.getController().addEntry(newEntry);
            } else {
                // Chỉnh sửa mục nhập hiện tại
                entry.setIncome(isIncome);
                entry.setAmount(amount);
                entry.setDate(date);
                entry.setNotes(notes);
                parentView.getController().updateEntry(entry, entry); // cập nhật mục nhập
            }

            parentView.updateTable(); // Cập nhật bảng sau khi lưu mục nhập
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số tiền hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

}
