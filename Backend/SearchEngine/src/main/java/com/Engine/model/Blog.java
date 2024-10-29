package com.Engine.model;

public class Blog {
    private String title;
    private String link;
    private String author;
    private String publishedDate;

    public Blog(String title, String link, String author, String publishedDate) {
        this.title = title;
        this.link = link;
        this.author = author;
        this.publishedDate = publishedDate;
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }
}
