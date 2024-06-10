package entity;

public class Position {
    private String positionID;
    private String positionName;
    private double commission;
    private double allowance;

    public Position(String positionID, String positionName, double commission, double allowance) {
        this.positionID = positionID;
        this.positionName = positionName;
        this.commission = commission;
        this.allowance = allowance;
    }

    public String getPositionID() {
        return positionID;
    }

    public void setPositionID(String positionID) {
        this.positionID = positionID;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public double getAllowance() {
        return allowance;
    }

    public void setAllowance(double allowance) {
        this.allowance = allowance;
    }
}

