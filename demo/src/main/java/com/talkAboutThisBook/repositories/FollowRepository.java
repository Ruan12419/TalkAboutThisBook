/*
 * This interface extends the JpaRepository and is implemented by FollowService
 * to do operations on the database
 */

package com.talkAboutThisBook.repositories;

import com.talkAboutThisBook.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    /*
     * Get a list of everyone the user is following
     */
    List<Follow> findByUserId(Long id);

    /*
     * Get a list of everyone who's following the user
     */
    List<Follow> findByFollowingId(Long id);

    /*
     * Get a specific user the user is following
     */
    Follow findByUserIdAndFollowingId(Long userId, Long followingId);

    /*
     * Check if the relationship between the two user exists
     */
    Boolean existsByUserIdAndFollowingId(Long userId, Long followingId);

}
