package entity;

public class Visitor {
    private String id;
    private String name;
    private String relationship;
    private String prisonerId;

    public Visitor(String id, String name, String relationship, String prisonerId) {
        this.id = id;
        this.name = name;
        this.relationship = relationship;
        this.prisonerId = prisonerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getPrisonerId() {
        return prisonerId;
    }

    public void setPrisonerId(String prisonerId) {
        this.prisonerId = prisonerId;
    }
}
