/*
 * Role Entity
 * 
 * This class is the model entity for the role table in the database.
 */

package com.talkAboutThisBook.model;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role {

    /*
     * Private fields
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    /*
     * Role constructors
     */
    public Role() {

    }

    public Role(String name) {
        super();
        this.name = name;
    }

    /*
     * Getters and Setters
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
