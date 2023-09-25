/*
 * This class is responsible for operations with Follows on the database
 */

package com.talkAboutThisBook.services;

import com.talkAboutThisBook.model.Follow;
import com.talkAboutThisBook.repositories.FollowRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FollowService {

    // Declaration of the services
    final FollowRepository followRepository;
    final UserServiceImpl userServiceImpl;

    public FollowService(FollowRepository followRepository, UserServiceImpl userServiceImpl) {
        this.followRepository = followRepository;
        this.userServiceImpl = userServiceImpl;
    }

    /*
     * Save a new follow to database
     */
    @Transactional
    public void save(Follow follow) {
        followRepository.save(follow);
    }

    /*
     * Get a list of Follow by the user's id
     */
    public List<Follow> findByUserId(Long id) {
        // Return everyone the user if following
        return followRepository.findByUserId(id);
    }

    /*
     * Get a list of Follow by the user's id
     */
    public List<Follow> findByFollowingId(Long id) {
        // Return everyone following the user
        return followRepository.findByFollowingId(id);
    }

    /*
     * Return a follow of the user
     */
    public Follow findByUserIdAndFollowingId(Long userId, Long followingId) {
        return followRepository.findByUserIdAndFollowingId(userId, followingId);
    }

    /*
     * Check if exist a relationship of follow between users
     */
    public Boolean existsByUserIdAndFollowingId(Long userId, Long followingId) {
        return followRepository.existsByUserIdAndFollowingId(userId, followingId);
    }

    /*
     * Delete a follow by the user id and the if of the user being followed
     */
    @Transactional
    public void delete(Follow follow) {
        // Stop following a user by deleting the relationship between them
        followRepository.delete(follow); 
    }
    
}
