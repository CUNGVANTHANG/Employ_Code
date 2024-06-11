package entity;

public class Vehicle {
    private String licensePlate;
    private String entryTime;
    private String exitTime;
    private double parkingFee;

    public Vehicle(String licensePlate, String entryTime) {
        this.licensePlate = licensePlate;
        this.entryTime = entryTime;
        this.exitTime = "";
        this.parkingFee = 0.0;
    }

    public Vehicle(String licensePlate, String entryTime, String exitTime, double parkingFee) {
        this.licensePlate = licensePlate;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.parkingFee = parkingFee;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getExitTime() {
        return exitTime;
    }

    public void setExitTime(String exitTime) {
        this.exitTime = exitTime;
    }

    public double getParkingFee() {
        return parkingFee;
    }

    public void setParkingFee(double parkingFee) {
        this.parkingFee = parkingFee;
    }

    // Getters and setters...
}

