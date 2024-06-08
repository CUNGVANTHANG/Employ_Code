package entity;

public class Vehicle {
    private String vehicleNumber;
    private String vehicleInfo;
    private String examAssigned;

    public Vehicle(String vehicleNumber, String vehicleInfo, String examAssigned) {
        this.vehicleNumber = vehicleNumber;
        this.vehicleInfo = vehicleInfo;
        this.examAssigned = examAssigned;
    }

    // Getters and setters
    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getVehicleInfo() {
        return vehicleInfo;
    }

    public void setVehicleInfo(String vehicleInfo) {
        this.vehicleInfo = vehicleInfo;
    }

    public String getExamAssigned() {
        return examAssigned;
    }

    public void setExamAssigned(String examAssigned) {
        this.examAssigned = examAssigned;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleNumber='" + vehicleNumber + '\'' +
                ", vehicleInfo='" + vehicleInfo + '\'' +
                ", examAssigned='" + examAssigned + '\'' +
                '}';
    }
}
