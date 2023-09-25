/*
 * This interface extends the JpaRepository and is implemented by DiscussionService
 * to do operations on the database
 */

package com.talkAboutThisBook.repositories;

import com.talkAboutThisBook.model.Discussion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscussionRepository extends JpaRepository<Discussion, Long> {

    /*
     * Get a list of discussions of a user by the user's id
     */
    List<Discussion> findByUserId(Long userId);

    /*
     * Get a list of discussions ordered by date and time in descending order
     */
    List<Discussion> findByOrderByDateTimeDesc();

    /*
     * Get a list of discussions of a specific book by the book's id
     */
    List<Discussion> findByBookId(String id);
}
