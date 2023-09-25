/*
 * Discussion Dto (Data Transfer Object) class
 */

package com.talkAboutThisBook.dto;

import com.talkAboutThisBook.model.Book;
import com.talkAboutThisBook.model.User;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class DiscussionDto {

    /*
     * Private fields
     */
    private User user;

    private Book book;

    private LocalDateTime dateTime;

    @Size(max = 2048)
    private String text;

    /*
     * DiscussionDto constructors
     */
    public DiscussionDto() {

    }

    public DiscussionDto(User user, Book book, LocalDateTime dateTime, String text) {
        this.user = user;
        this.book = book;
        this.dateTime = dateTime;
        this.text = text;
    }

    /*
     * Getters and Setters
     */
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
