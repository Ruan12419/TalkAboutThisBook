/*
 * User Registration Dto (Data Transfer Object) class
 */

package com.talkAboutThisBook.dto;

public class UserRegistrationDto {

    /*
     * Private fields
     */
    private String firstName;

    private String lastName;

    private String username;

    private String password;

    /*
     * UserRegistrationDto constructors
     */
    public UserRegistrationDto() {

    }

    public UserRegistrationDto(String firstName, String lastName, String username, String password) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    /*
     * Getters and Setters
     */
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
