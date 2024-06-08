package entity;

import java.util.Date;

public class Student {
    private String name;
    private String cccd;
    private String registeredLicenseType;
    private Date examDate;  // Change to Date
    private String result;

    public Student(String name, String cccd, String registeredLicenseType, Date examDate, String result) {
        this.name = name;
        this.cccd = cccd;
        this.registeredLicenseType = registeredLicenseType;
        this.examDate = examDate;
        this.result = result;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getRegisteredLicenseType() {
        return registeredLicenseType;
    }

    public void setRegisteredLicenseType(String registeredLicenseType) {
        this.registeredLicenseType = registeredLicenseType;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", cccd='" + cccd + '\'' +
                ", registeredLicenseType='" + registeredLicenseType + '\'' +
                ", examDate=" + examDate +  // Adjust for Date
                ", result='" + result + '\'' +
                '}';
    }
}
