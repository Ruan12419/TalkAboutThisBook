/*
 * Comment Dto (Data Transfer Object) class
 */

package com.talkAboutThisBook.dto;

import com.talkAboutThisBook.model.Discussion;
import com.talkAboutThisBook.model.User;

import java.time.LocalDateTime;

public class CommentDto {

    /*
     * Private fields
     */
    private User user;

    private Discussion discussion;

    private String text;

    private LocalDateTime dateTime;

    private Long parentId;

    /*
     * CommentDto constructors
     */
    public CommentDto() {
    }

    public CommentDto(User user, Discussion discussion, String text, LocalDateTime dateTime, Long parentId) {
        this.user = user;
        this.discussion = discussion;
        this.text = text;
        this.dateTime = dateTime;
        this.parentId = parentId;
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

    public Discussion getDiscussion() {
        return discussion;
    }

    public void setDiscussion(Discussion discussion) {
        this.discussion = discussion;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
