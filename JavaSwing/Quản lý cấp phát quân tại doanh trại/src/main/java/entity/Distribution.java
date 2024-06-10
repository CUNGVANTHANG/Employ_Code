package entity;

import java.util.Date;

public class Distribution {
    private Date distributionDate;
    private String distributionUnit;
    private int quantityDistributed;
    private int quantityReturned;
    private boolean distributionStatus;

    // Constructor, getters, and setters
    public Distribution(Date distributionDate, String distributionUnit, int quantityDistributed, int quantityReturned, boolean distributionStatus) {
        this.distributionDate = distributionDate;
        this.distributionUnit = distributionUnit;
        this.quantityDistributed = quantityDistributed;
        this.quantityReturned = quantityReturned;
        this.distributionStatus = distributionStatus;
    }

    public Date getDistributionDate() {
        return distributionDate;
    }

    public void setDistributionDate(Date distributionDate) {
        this.distributionDate = distributionDate;
    }

    public String getDistributionUnit() {
        return distributionUnit;
    }

    public void setDistributionUnit(String distributionUnit) {
        this.distributionUnit = distributionUnit;
    }

    public int getQuantityDistributed() {
        return quantityDistributed;
    }

    public void setQuantityDistributed(int quantityDistributed) {
        this.quantityDistributed = quantityDistributed;
    }

    public int getQuantityReturned() {
        return quantityReturned;
    }

    public void setQuantityReturned(int quantityReturned) {
        this.quantityReturned = quantityReturned;
    }

    public boolean isDistributionStatus() {
        return distributionStatus;
    }

    public void setDistributionStatus(boolean distributionStatus) {
        this.distributionStatus = distributionStatus;
    }
}
