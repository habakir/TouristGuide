package org.example.turistickivodic.models;

public class Activity {
    private String id;
    private String name;

    // Constructors
    public Activity() {}

    public Activity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and Setters
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

    // toString method (optional, for debugging purposes)
    @Override
    public String toString() {
        return "Activity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
