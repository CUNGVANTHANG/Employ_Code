package view;

import entity.Exam;
import func.ExamManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class ExamResultDialog extends JDialog {
    private ExamManager examManager;
    private JTable resultTable;
    private DefaultTableModel tableModel;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public ExamResultDialog(JFrame parent, String title, List<Exam> results) {
        super(parent, title, true);
        setSize(600, 400);
        setLocationRelativeTo(parent);
        this.examManager = examManager;

        JPanel panel = new JPanel(new BorderLayout());

        String[] columnNames = {"Ngày thi", "Địa điểm", "Số lượng xe", "Loại bằng", "Hình thức thi"};
        tableModel = new DefaultTableModel(columnNames, 0);
        resultTable = new JTable(tableModel);

        updateTable(results);

        JScrollPane scrollPane = new JScrollPane(resultTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);
    }

    private void updateTable(List<Exam> results) {
        tableModel.setRowCount(0);
        for (Exam exam : results) {
            String dateFormatted = dateFormat.format(exam.getDate());
            Object[] row = {dateFormatted, exam.getLocation(), exam.getVehicleCount(), exam.getLicenseType(), exam.getExamForm()};
            tableModel.addRow(row);
        }
    }
}
