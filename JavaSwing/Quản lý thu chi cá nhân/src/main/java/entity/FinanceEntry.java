package entity;

import java.util.Date;

public class FinanceEntry {
    private boolean isIncome;
    private double amount;
    private Date date;
    private String notes;

    public FinanceEntry() {}

    public FinanceEntry(boolean isIncome, double amount, Date date, String notes) {
        this.isIncome = isIncome;
        this.amount = amount;
        this.date = date;
        this.notes = notes;
    }

    public boolean isIncome() {
        return isIncome;
    }

    public void setIncome(boolean isIncome) {
        this.isIncome = isIncome;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
