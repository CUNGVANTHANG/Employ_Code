package entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Exam {
    private Date date;
    private String location;
    private int vehicleCount;
    private String licenseType;
    private String examForm;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public Exam(Date date, String location, int vehicleCount, String licenseType, String examForm) {
        this.date = date;
        this.location = location;
        this.vehicleCount = vehicleCount;
        this.licenseType = licenseType;
        this.examForm = examForm;
    }

    // Getters and setters
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getVehicleCount() {
        return vehicleCount;
    }

    public void setVehicleCount(int vehicleCount) {
        this.vehicleCount = vehicleCount;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public String getExamForm() {
        return examForm;
    }

    public void setExamForm(String examForm) {
        this.examForm = examForm;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "date=" + dateFormat.format(date) +
                ", location='" + location + '\'' +
                ", vehicleCount=" + vehicleCount +
                ", licenseType='" + licenseType + '\'' +
                ", examForm='" + examForm + '\'' +
                '}';
    }
}
