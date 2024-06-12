package org.example.turistickivodic.models;

import java.util.Date;
import java.util.List;

public class Article {
    private int id;
    private String title;
    private String text;
    private int destination_id;
    private Destination destination; // Add this field
    private int author_id;
    private User author;
    private Date createdAt;
    private List<String> activities;
    private List<Comment> comments; // Add this field

    // Constructor

    public Article(int id, String title, String text, int destination_id, int author_id, User author, Date createdAt, List<String> activities, Destination destination) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.destination_id = destination_id;
        this.author_id = author_id;
        this.author = author;
        this.createdAt = createdAt;
        this.activities = activities;
        this.destination = destination; // Initialize the field
    }

    public Article(int id, String title, String text, int destinationId, int authorId, List<String> activities) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.destination_id = destinationId;
        this.author_id = authorId;
        this.activities = activities;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getDestination_id() {
        return destination_id;
    }

    public void setDestination_id(int destination_id) {
        this.destination_id = destination_id;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<String> getActivities() {
        return activities;
    }

    public void setActivities(List<String> activities) {
        this.activities = activities;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
