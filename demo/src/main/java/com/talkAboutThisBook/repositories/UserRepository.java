/*
 * This interface extends the JpaRepository and is implemented by UserService
 * to do operations on the database
 */

package com.talkAboutThisBook.repositories;

import com.talkAboutThisBook.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /*
     * Check if username exists in the database
     */
    Boolean existsByUsername(String username);

    /*
     * Get a user by its username
     */
    User findByUsername(String username);

}
