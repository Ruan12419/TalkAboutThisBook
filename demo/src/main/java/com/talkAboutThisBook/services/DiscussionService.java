/*
 * This class is responsible for operations with Discussions on the database
 */

package com.talkAboutThisBook.services;

import com.talkAboutThisBook.dto.DiscussionDto;
import com.talkAboutThisBook.model.Discussion;
import com.talkAboutThisBook.repositories.DiscussionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiscussionService {

    // Declaration of the service
    final DiscussionRepository discussionRepository;

    public DiscussionService(DiscussionRepository discussionRepository) {
        this.discussionRepository = discussionRepository;
    }

    /*
     * Save a new Discussion to database
     */
    public void save(DiscussionDto discussionDto) {
        Discussion discussion = new Discussion(discussionDto.getUser(), discussionDto.getBook(),
                discussionDto.getDateTime(), discussionDto.getText());
        discussionRepository.save(discussion);
    }

    /*
     * Get a discussion by its id
     */
    public Discussion findById(Long id) throws Exception {
        if (discussionRepository.findById(id).isPresent()) {
            return discussionRepository.findById(id).get();
        } else {
            throw new Exception();
        }
    }

    /*
     * Delete a discussion by its id
     */
    public void delete(Long id) {
        try {
            Optional<Discussion> opDisc = discussionRepository.findById(id);
            Discussion discussion;
            if (opDisc.isPresent()) {
                discussion = opDisc.get();

                try {
                    discussionRepository.delete(discussion);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Get all discussions of a user
     */
    public List<Discussion> getUserDiscussions(Long userId) {
        return discussionRepository.findByUserId(userId);
    }

    /*
     * Get all discussions of a book by the book's id
     */
    public List<Discussion> findByBookId(String id) {
        return discussionRepository.findByBookId(id);
    }

    /*
     * Get all discussions
     */
    public List<Discussion> getAllDiscussions() {
        return discussionRepository.findByOrderByDateTimeDesc();
    }

}