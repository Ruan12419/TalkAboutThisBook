/*
 * Follow Entity
 * 
 * This class is the model entity for the follow table in the database.
 */

package com.talkAboutThisBook.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "follow")
public class Follow implements Serializable {

    /*
     * Private fields
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST,
            CascadeType.REFRESH }, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "following_id")
    private User following;

    /*
     * Follow constructors
     */
    public Follow() {
    }

    public Follow(User user, User following) {
        this.user = user;
        this.following = following;
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

    public User getFollowing() {
        return following;
    }

    public void setFollowing(User following) {
        this.following = following;
    }

}
