// entity/Unit.java
package entity;

public class Unit {
    private String name;
    private String id;
    private int soldierCount;

    // Constructor, getters, and setters
    public Unit(String name, String id, int soldierCount) {
        this.name = name;
        this.id = id;
        this.soldierCount = soldierCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSoldierCount() {
        return soldierCount;
    }

    public void setSoldierCount(int soldierCount) {
        this.soldierCount = soldierCount;
    }
}
