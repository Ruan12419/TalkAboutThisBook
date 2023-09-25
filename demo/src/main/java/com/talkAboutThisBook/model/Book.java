/*
 * Book Entity
 * 
 * This class is the model entity for the books table in the database.
 */

package com.talkAboutThisBook.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "books", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Book implements Serializable {

    /*
     * Private fields
     */
    @Id
    private String id;

    private Long userId;

    @Column(name = "title", nullable = false)
    private String title;

    private String publisher;

    private String publishedDate;

    @Transient
    private String[] authors;

    @Column(length = 4096)
    private String description;

    @Column(length = 2048)
    private String thumbnail;

    /*
     * Book constructors
     */
    public Book() {

    }

    public Book(String id, Long userId, String title,
            String publisher, String publishedDate,
            String[] authors, String description, String thumbnail) {
        super();
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.authors = authors;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    /*
     * Getters and Setters
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String[] getAuthors() {
        return authors;
    }

    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
