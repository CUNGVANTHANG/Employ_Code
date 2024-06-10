package view;

import entity.FinanceEntry;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class FinanceResultDialog extends JDialog {
    private DefaultTableModel tableModel;

    public FinanceResultDialog(Frame parent, String title, List<FinanceEntry> searchResults) {
        super(parent, title, true);
        setSize(600, 400);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new BorderLayout());

        String[] columnNames = {"Loại", "Số tiền", "Ngày", "Ghi chú"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable resultTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(resultTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);

        updateTable(searchResults);
    }

    private void updateTable(List<FinanceEntry> entries) {
        tableModel.setRowCount(0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        NumberFormat currencyFormat = NumberFormat.getNumberInstance(Locale.US);
        for (FinanceEntry entry : entries) {
            String type = entry.isIncome() ? "Thu nhập" : "Chi tiêu";
            String formattedDate = dateFormat.format(entry.getDate());
            String formattedAmount = currencyFormat.format(entry.getAmount()) + " đồng";
            Object[] row = {type, formattedAmount, formattedDate, entry.getNotes()};
            tableModel.addRow(row);
        }
    }
}
