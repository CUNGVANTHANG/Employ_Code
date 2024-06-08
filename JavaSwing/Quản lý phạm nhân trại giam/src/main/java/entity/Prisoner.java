package entity;

public class Prisoner {
    private String id;
    private String name;
    private int age;
    private String crime;
    private int sentenceYears;
    private String prison;

    public Prisoner(String id, String name, int age, String crime, int sentenceYears, String prison) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.crime = crime;
        this.sentenceYears = sentenceYears;
        this.prison = prison;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCrime() {
        return crime;
    }

    public void setCrime(String crime) {
        this.crime = crime;
    }

    public int getSentenceYears() {
        return sentenceYears;
    }

    public void setSentenceYears(int sentenceYears) {
        this.sentenceYears = sentenceYears;
    }

    public String getPrison() {
        return prison;
    }

    public void setPrison(String prison) {
        this.prison = prison;
    }
}
