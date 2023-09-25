/*
 * This class is responsible for operations with Comments on the database
 */

package com.talkAboutThisBook.services;

import com.talkAboutThisBook.dto.CommentDto;
import com.talkAboutThisBook.model.Comment;
import com.talkAboutThisBook.repositories.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CommentService {

    // Declaration of the service
    final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    /*
     * Save a new comment to database
     */
    @Transactional
    public void save(CommentDto commentDto) {
        Comment comment;
        if (commentRepository.findById(commentDto.getParentId()).isPresent()) {
            comment = new Comment(commentDto.getUser(), commentDto.getDiscussion(), commentDto.getText(),
                    commentDto.getDateTime(), commentDto.getParentId());
            commentRepository.save(comment);

        } else {
            comment = new Comment(commentDto.getUser(), commentDto.getDiscussion(), commentDto.getText(),
                    commentDto.getDateTime(), null);
            commentRepository.save(comment);
        }

    }

    /*
     * Delete a comment from database
     */
    @Transactional
    public void delete(Long id) {
        try {
            Optional<Comment> opComm = commentRepository.findById(id);
            Comment comment = null;
            if(opComm.isPresent()) {
                comment = opComm.get();
            }
            try {
                commentRepository.delete(Objects.requireNonNull(comment));
            } catch(Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Delete all comments of a discussion by the discussion's id
     */
    @Transactional
    public void deleteByDiscussionId(Long discussionId) {
        commentRepository.deleteByDiscussionId(discussionId);
    }

    /*
     * Get a list of comments of a discussion ordered by date and time
     */
    public List<Comment> findByDiscussionIdOrderByDateTimeAsc(Long discussionId) {
        return commentRepository.findByDiscussionIdOrderByDateTimeAsc(discussionId);
    }

    /*
     * Get a list of all comments
     */
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public List<Comment> getReplies(Long id) {
        return commentRepository.findByParentId(id);
    }
}
