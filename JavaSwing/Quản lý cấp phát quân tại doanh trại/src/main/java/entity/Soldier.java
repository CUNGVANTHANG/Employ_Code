// entity/Soldier.java
package entity;

public class Soldier {
    private String fullName;
    private String id;
    private String unit;
    private String gender;
    private String rank;

    // Constructor, getters, and setters
    public Soldier(String fullName, String id, String unit, String gender, String rank) {
        this.fullName = fullName;
        this.id = id;
        this.unit = unit;
        this.gender = gender;
        this.rank = rank;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
