/*
 * This class is responsible for follower settings
 */

package com.talkAboutThisBook.controller;

import com.talkAboutThisBook.model.Follow;
import com.talkAboutThisBook.model.User;
import com.talkAboutThisBook.services.FollowService;
import com.talkAboutThisBook.services.UserServiceImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

    // Declaration of the services
    final UserServiceImpl userServiceImpl;

    final FollowService followService;

    public UserController(UserServiceImpl userServiceImpl, FollowService followService) {
        this.userServiceImpl = userServiceImpl;
        this.followService = followService;
    }

    /*
     * When a user clicks to 'follow' someone, that other user's id will be sent to
     * the following address to build the relationship between the two
     */
    @ResponseBody
    @PostMapping("/follow")
    public RedirectView followUser(@RequestParam(name = "id") Long id) throws UserPrincipalNotFoundException {

        // If the user is already following the other one, reload the page or go back to
        // discuss
        if (followService.existsByUserIdAndFollowingId(userServiceImpl.getUserId(), id)) {
            return new RedirectView("discuss");
        }

        // Initializes a new follow model and gets the user model
        Follow follower = new Follow();
        User user = userServiceImpl.getUser(id);

        // set user x following user y
        follower.setUser(userServiceImpl.getUser(userServiceImpl.getUserId()));
        follower.setFollowing(user);

        // save the new relationship to database
        followService.save(follower);

        // Redirect to the user discussions
        return new RedirectView("discuss/" + id);
    }

    /*
     * When a user clicks to 'unfollow' someone, that other user's id will be sent
     * to the following address to build the relationship between the two
     */
    @ResponseBody
    @PostMapping("/unfollow")
    public RedirectView unfollowUser(@RequestParam(name = "id") Long id)
            throws UserPrincipalNotFoundException {

        // If user is really following the other one the relationship will be deleted
        // from database
        if (followService.existsByUserIdAndFollowingId(userServiceImpl.getUserId(), id)) {
            Follow follow = followService.findByUserIdAndFollowingId(userServiceImpl.getUserId(), id);

            followService.delete(follow);
        }

        return new RedirectView("following");
    }

    /*
     * Get all users being followed by the current user and all their followers
     */
    @GetMapping("/following")
    public ModelAndView following() throws UserPrincipalNotFoundException {
        ModelAndView mav = new ModelAndView("following");
        List<Follow> following = followService.findByUserId(userServiceImpl.getUserId());

        mav.addObject("following", following);
        mav.addObject("followers", followService.findByFollowingId(userServiceImpl.getUserId()));
        return mav;
    }

}
