/*
 * This interface extends the JpaRepository and is implemented by CommentService
 * to do operations on the database
 */

package com.talkAboutThisBook.repositories;

import com.talkAboutThisBook.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    /*
     * Get a list of comments by the discussion id ordered by date and time
     */
    List<Comment> findByDiscussionIdOrderByDateTimeAsc(Long discussionId);

    /*
     * 
     */
    List<Comment> findByParentId(Long parentId);

    /*
     * Delete all comments of a discussion by the discussion's id
     */
    void deleteByDiscussionId(Long discussionId);
}
