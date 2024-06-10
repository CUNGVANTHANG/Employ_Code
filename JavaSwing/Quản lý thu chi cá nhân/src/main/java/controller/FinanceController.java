package controller;

import entity.FinanceEntry;
import func.FinanceManager;
import view.FinanceManagementView;

import java.util.List;
import java.util.Map;

public class FinanceController {
    private FinanceManagementView view;
    private FinanceManager manager;

    public FinanceController(FinanceManagementView view, FinanceManager manager) {
        this.view = view;
        this.manager = manager;
        view.setController(this);
    }

    public void addEntry(FinanceEntry entry) {
        manager.addEntry(entry);
        view.updateTable();
        checkExpensesExceedIncome();
    }

    public void updateEntry(FinanceEntry entry, FinanceEntry newEntry) {
        manager.updateEntry(entry, newEntry);
        view.updateTable();
    }

    public void deleteEntry(FinanceEntry entry) {
        manager.deleteEntry(entry);
        view.updateTable();
    }

    public List<FinanceEntry> searchEntries(String query) {
        return manager.search(query);
    }

    public Map<String, Map<String, Double>> getStatistics(String type) {
        return manager.getStatistics(type);
    }

    public void loadEntries(String filePath) {
        manager.loadEntries(filePath);
        view.updateTable();
    }

    public void saveEntries(String filePath) {
        manager.saveEntries(filePath);
    }

    private void checkExpensesExceedIncome() {
        double totalIncome = manager.getTotalIncome();
        double totalExpense = manager.getTotalExpense();
        if (totalExpense > totalIncome) {
            view.showWarning("Chi tiêu vượt quá thu nhập!");
        }
    }

    public List<FinanceEntry> getEntries() {
        return manager.getEntries();
    }
}
