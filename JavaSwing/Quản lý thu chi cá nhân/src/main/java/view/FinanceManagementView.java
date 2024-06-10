package view;

import controller.FinanceController;
import entity.FinanceEntry;
import func.FinanceManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class FinanceManagementView extends JFrame {
    private FinanceController controller;
    private JTable financeTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> cmbStatistics;
    private JTextField txtDay;
    private JTextField txtMonth;
    private JTextField txtYear;

    public FinanceManagementView() {
        setTitle("Quản lý thu chi cá nhân");
        setSize(1050, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setupUI();
    }

    private void setupUI() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columnNames = {"Loại", "Số tiền", "Ngày", "Ghi chú"};
        tableModel = new DefaultTableModel(columnNames, 0);
        financeTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(financeTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        JButton btnAdd = new JButton("Thêm");
        JButton btnEdit = new JButton("Sửa");
        JButton btnDelete = new JButton("Xóa");
        JButton btnSave = new JButton("Lưu");
        JButton btnLoad = new JButton("Tải");

        JLabel lblDay = new JLabel("Ngày:");
        txtDay = new JTextField(5);

        JLabel lblMonth = new JLabel("Tháng:");
        txtMonth = new JTextField(5);

        JLabel lblYear = new JLabel("Năm:");
        txtYear = new JTextField(8);

        JButton btnSearch = new JButton("Tìm kiếm");

        JLabel lblStatistics = new JLabel("Thống kê theo:");
        String[] statisticsOptions = {"Ngày", "Tuần", "Tháng"};
        cmbStatistics = new JComboBox<>(statisticsOptions);

        JButton btnStatistics = new JButton("Xem thống kê");

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FinanceEntryDialog dialog = new FinanceEntryDialog(FinanceManagementView.this);
                dialog.setVisible(true);
            }
        });

        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = financeTable.getSelectedRow();
                if (selectedRow >= 0) {
                    FinanceEntry entry = controller.getEntries().get(selectedRow);
                    FinanceEntryDialog dialog = new FinanceEntryDialog(FinanceManagementView.this, entry);
                    dialog.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(FinanceManagementView.this, "Vui lòng chọn một hàng để sửa", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = financeTable.getSelectedRow();
                if (selectedRow >= 0) {
                    FinanceEntry entry = controller.getEntries().get(selectedRow);
                    int confirmation = JOptionPane.showConfirmDialog(FinanceManagementView.this, "Bạn có chắc chắn muốn xóa mục nhập này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                    if (confirmation == JOptionPane.YES_OPTION) {
                        controller.deleteEntry(entry);
                        updateTable();
                    }
                } else {
                    JOptionPane.showMessageDialog(FinanceManagementView.this, "Vui lòng chọn một hàng để xóa", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showSaveDialog(FinanceManagementView.this) == JFileChooser.APPROVE_OPTION) {
                    String filePath = fileChooser.getSelectedFile().getPath();
                    controller.saveEntries(filePath);
                }
            }
        });

        btnLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showOpenDialog(FinanceManagementView.this) == JFileChooser.APPROVE_OPTION) {
                    String filePath = fileChooser.getSelectedFile().getPath();
                    controller.loadEntries(filePath);
                }
            }
        });

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int searchDay, searchMonth, searchYear;
                if (!txtDay.getText().isEmpty() && !txtMonth.getText().isEmpty() && !txtYear.getText().isEmpty()) {
                    searchDay = Integer.parseInt(txtDay.getText());
                    searchMonth = Integer.parseInt(txtMonth.getText());
                    searchYear = Integer.parseInt(txtYear.getText());
                    showSearchResults(searchByDayMonthYear(searchDay, searchMonth, searchYear));
                } else if (!txtDay.getText().isEmpty() && !txtMonth.getText().isEmpty()) {
                    searchDay = Integer.parseInt(txtDay.getText());
                    searchMonth = Integer.parseInt(txtMonth.getText());
                    showSearchResults(searchByDayMonth(searchDay, searchMonth));
                } else if (!txtDay.getText().isEmpty() && !txtYear.getText().isEmpty()) {
                    searchDay = Integer.parseInt(txtDay.getText());
                    searchYear = Integer.parseInt(txtYear.getText());
                    showSearchResults(searchByDayYear(searchDay, searchYear));
                } else if (!txtMonth.getText().isEmpty() && !txtYear.getText().isEmpty()) {
                    searchMonth = Integer.parseInt(txtMonth.getText());
                    searchYear = Integer.parseInt(txtYear.getText());
                    showSearchResults(searchByMonthYear(searchMonth, searchYear));
                } else if (!txtDay.getText().isEmpty()) {
                    searchDay = Integer.parseInt(txtDay.getText());
                    showSearchResults(searchByDay(searchDay));
                } else if (!txtMonth.getText().isEmpty()) {
                    searchMonth = Integer.parseInt(txtMonth.getText());
                    showSearchResults(searchByMonth(searchMonth));
                } else if (!txtYear.getText().isEmpty()) {
                    searchYear = Integer.parseInt(txtYear.getText());
                    showSearchResults(searchByYear(searchYear));
                }
            }
        });

        btnStatistics.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String type = (String) cmbStatistics.getSelectedItem();
                Map<String, Map<String, Double>> statistics = controller.getStatistics(type);
                showStatistics(statistics);
            }
        });

        controlPanel.add(lblDay);
        controlPanel.add(txtDay);
        controlPanel.add(lblMonth);
        controlPanel.add(txtMonth);
        controlPanel.add(lblYear);
        controlPanel.add(txtYear);
        controlPanel.add(btnSearch);
        controlPanel.add(lblStatistics);
        controlPanel.add(cmbStatistics);
        controlPanel.add(btnStatistics);
        controlPanel.add(btnAdd);
        controlPanel.add(btnEdit);
        controlPanel.add(btnDelete);
        controlPanel.add(btnSave);
        controlPanel.add(btnLoad);

        panel.add(controlPanel, BorderLayout.SOUTH);

        add(panel);
    }

    public List<FinanceEntry> searchByMonth(int month) {
        List<FinanceEntry> results = new ArrayList<>();
        for (FinanceEntry financeEntry : controller.getEntries()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(financeEntry.getDate());
            if (calendar.get(Calendar.MONTH) + 1 == month) {
                results.add(financeEntry);
            }
        }
        return results;
    }

    public List<FinanceEntry> searchByDayMonthYear(int day, int month, int year) {
        List<FinanceEntry> results = new ArrayList<>();
        for (FinanceEntry financeEntry : controller.getEntries()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(financeEntry.getDate());
            if (calendar.get(Calendar.DAY_OF_MONTH) == day && calendar.get(Calendar.MONTH) + 1 == month && calendar.get(Calendar.YEAR) == year) {
                results.add(financeEntry);
            }
        }
        return results;
    }

    public List<FinanceEntry> searchByYear(int year) {
        List<FinanceEntry> results = new ArrayList<>();
        for (FinanceEntry financeEntry : controller.getEntries()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(financeEntry.getDate());
            if (calendar.get(Calendar.YEAR) == year) {
                results.add(financeEntry);
            }
        }
        return results;
    }

    public List<FinanceEntry> searchByDayYear(int day, int year) {
        List<FinanceEntry> results = new ArrayList<>();
        for (FinanceEntry financeEntry : controller.getEntries()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(financeEntry.getDate());
            if (calendar.get(Calendar.DAY_OF_MONTH) == day && calendar.get(Calendar.YEAR) == year) {
                results.add(financeEntry);
            }
        }
        return results;
    }

    public List<FinanceEntry> searchByMonthYear(int month, int year) {
        List<FinanceEntry> results = new ArrayList<>();
        for (FinanceEntry financeEntry : controller.getEntries()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(financeEntry.getDate());
            if (calendar.get(Calendar.MONTH) + 1 == month && calendar.get(Calendar.YEAR) == year) {
                results.add(financeEntry);
            }
        }
        return results;
    }

    public List<FinanceEntry> searchByDayMonth(int day, int month) {
        List<FinanceEntry> results = new ArrayList<>();
        for (FinanceEntry financeEntry : controller.getEntries()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(financeEntry.getDate());
            if (calendar.get(Calendar.DAY_OF_MONTH) == day && calendar.get(Calendar.MONTH) + 1 == month) {
                results.add(financeEntry);
            }
        }
        return results;
    }

    public List<FinanceEntry> searchByDay(int day) {
        List<FinanceEntry> results = new ArrayList<>();
        for (FinanceEntry financeEntry : controller.getEntries()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(financeEntry.getDate());
            if (calendar.get(Calendar.DAY_OF_MONTH) == day) {
                results.add(financeEntry);
            }
        }
        return results;
    }

    private void showSearchResults(List<FinanceEntry> results) {
        if (!results.isEmpty()) {
            FinanceResultDialog resultDialog = new FinanceResultDialog(this, "Kết quả tìm kiếm", results);
            resultDialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Không tìm thấy", "Kết quả tìm kiếm", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void setController(FinanceController controller) {
        this.controller = controller;
    }

    public FinanceController getController() {
        return controller;
    }

    public void updateTable() {
        List<FinanceEntry> entries = controller.getEntries();
        updateTable(entries);
    }

    private void updateTable(List<FinanceEntry> entries) {
        tableModel.setRowCount(0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        NumberFormat currencyFormat = NumberFormat.getNumberInstance(Locale.US);
        for (FinanceEntry entry : entries) {
            String type = entry.isIncome() ? "Thu nhập" : "Chi tiêu";
            String formattedDate = dateFormat.format(entry.getDate()); // Định dạng ngày
            String formattedAmount = currencyFormat.format(entry.getAmount()) + " đồng"; // Định dạng số tiền
            Object[] row = {type, formattedAmount, formattedDate, entry.getNotes()};
            tableModel.addRow(row);
        }
    }

    public void showWarning(String message) {
        JOptionPane.showMessageDialog(this, message, "Cảnh báo", JOptionPane.WARNING_MESSAGE);
    }

    private void showStatistics(Map<String, Map<String, Double>> statistics) {
        StringBuilder message = new StringBuilder("Thống kê:\n");

        for (Map.Entry<String, Map<String, Double>> entry : statistics.entrySet()) {
            String date = entry.getKey();
            Map<String, Double> subMap = entry.getValue();

            message.append(date).append(":\n");
            message.append("  Thu nhập: ").append(String.format(Locale.US, "%,.0f đồng", subMap.getOrDefault("Thu nhập", 0.0))).append("\n");
            message.append("  Chi tiêu: ").append(String.format(Locale.US, "%,.0f đồng", subMap.getOrDefault("Chi tiêu", 0.0))).append("\n");
        }

        JOptionPane.showMessageDialog(this, message.toString(), "Thống kê", JOptionPane.INFORMATION_MESSAGE);
    }
}
