package org.example.turistickivodic.models;

import java.util.Date;

public class Comment {
    private int id;
    private int articleId;
    private String author;
    private String text;
    private Date createdAt;

    public Comment(int id, int articleId, String author, String text, Date createdAt) {
        this.id = id;
        this.articleId = articleId;
        this.author = author;
        this.text = text;
        this.createdAt = createdAt;
    }

    public Comment(int articleId, String author, String text) {
        this.articleId = articleId;
        this.author = author;
        this.text = text;
        this.createdAt = new Date();
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
