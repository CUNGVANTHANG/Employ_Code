package func;

import entity.FinanceEntry;
import utils.XMLHandler;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class FinanceManager {
    private List<FinanceEntry> entries;

    public FinanceManager() {
        entries = new ArrayList<>();
    }

    public void loadEntries(String filePath) {
        entries = XMLHandler.loadFromFile(filePath);
    }

    public void saveEntries(String filePath) {
        XMLHandler.saveToFile(filePath, entries);
    }

    public void addEntry(FinanceEntry entry) {
        entries.add(entry);
        saveEntries("src/main/resources/finance_entries.xml");
    }

    public void updateEntry(FinanceEntry entry, FinanceEntry updatedEntry) {
        int index = entries.indexOf(entry);
        if (index >= 0) {
            entries.set(index, updatedEntry);
            saveEntries("src/main/resources/finance_entries.xml");
        }
    }

    public void deleteEntry(FinanceEntry entry) {
        entries.remove(entry);
        saveEntries("src/main/resources/finance_entries.xml");
    }

    public List<FinanceEntry> getEntries() {
        return entries;
    }

    public List<FinanceEntry> search(String query) {
        return entries.stream()
                .filter(entry -> entry.getNotes().contains(query))
                .collect(Collectors.toList());
    }

    public Map<String, Map<String, Double>> getStatistics(String type) {
        Map<String, Map<String, Double>> statistics = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat weekFormat = new SimpleDateFormat("'W'ww/yyyy");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM/yyyy");

        for (FinanceEntry entry : entries) {
            calendar.setTime(entry.getDate());
            String key;

            if ("Tháng".equalsIgnoreCase(type)) {
                key = monthFormat.format(calendar.getTime());
            } else if ("Tuần".equalsIgnoreCase(type)) {
                key = weekFormat.format(calendar.getTime());
            } else {
                key = dayFormat.format(calendar.getTime());
            }

            // Initialize sub-maps if they don't exist
            statistics.putIfAbsent(key, new HashMap<>());
            Map<String, Double> subMap = statistics.get(key);

            // Determine the type and update the corresponding value
            if (entry.isIncome()) {
                subMap.put("Thu nhập", subMap.getOrDefault("Thu nhập", 0.0) + entry.getAmount());
            } else {
                subMap.put("Chi tiêu", subMap.getOrDefault("Chi tiêu", 0.0) + entry.getAmount());
            }
        }
        return statistics;
    }

    public double getTotalIncome() {
        return entries.stream().filter(FinanceEntry::isIncome).mapToDouble(FinanceEntry::getAmount).sum();
    }

    public double getTotalExpense() {
        return entries.stream().filter(entry -> !entry.isIncome()).mapToDouble(FinanceEntry::getAmount).sum();
    }
}
