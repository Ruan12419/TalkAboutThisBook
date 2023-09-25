/*
 * This class is responsible for the discussions
 */

package com.talkAboutThisBook.controller;

import com.talkAboutThisBook.dto.DiscussionDto;
import com.talkAboutThisBook.model.Comment;
import com.talkAboutThisBook.model.Discussion;
import com.talkAboutThisBook.services.BookService;
import com.talkAboutThisBook.services.CommentService;
import com.talkAboutThisBook.services.DiscussionService;
import com.talkAboutThisBook.services.UserServiceImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "*")
public class DiscussionController {

    // Declaration of the services
    final BookService bookService;
    final UserServiceImpl userServiceImpl;
    final DiscussionService discussionService;
    final CommentService commentService;

    public DiscussionController(BookService bookService, UserServiceImpl userServiceImpl,
            DiscussionService discussionService,
            CommentService commentService) {
        this.bookService = bookService;
        this.userServiceImpl = userServiceImpl;
        this.discussionService = discussionService;
        this.commentService = commentService;
    }

    /*
     * Renders the 'discuss' template
     */
    @GetMapping("/discuss")
    public ModelAndView discuss() throws UserPrincipalNotFoundException {
        // The ModelAndView is initialized by a function tha will add the main objects
        // of the template, like user saved books, discussion comments and user
        // information that will be used by thymeleaf
        ModelAndView mav = startDiscussionsViews("discuss");

        // Adding discussions in mav
        mav.addObject(getDiscussions());

        return mav;
    }

    /*
     * Shows only the user discussions
     */
    @GetMapping("/my-discuss")
    public ModelAndView myDiscuss() throws UserPrincipalNotFoundException {
        // ModelAndView is initialized like the above comment, but now for the
        // 'mydiscuss' template
        ModelAndView mav = startDiscussionsViews("mydiscuss");

        // Get only user discussions via discussionService and add it to mav
        mav.addObject(discussionService.getUserDiscussions(userServiceImpl.getUserId()));

        return mav;
    }

    /*
     * Show discussions from another user
     */
    @GetMapping("/discuss/{id}")
    public ModelAndView userDiscuss(@PathVariable Long id) throws UserPrincipalNotFoundException {
        // Initializes ModelAndView
        ModelAndView mav = startDiscussionsViews("discuss");

        // Get discussions from some user by passing their id as a parameter to a method
        // of discussionService
        mav.addObject(discussionService.getUserDiscussions(id));

        return mav;
    }

    /*
     * Show every discussion of a book by book's id
     */
    @GetMapping("/discuss/book/{id}")
    public ModelAndView bookDiscuss(@PathVariable String id) throws UserPrincipalNotFoundException {
        // Initializes ModelAndView
        ModelAndView mav = startDiscussionsViews("discuss");

        // Get discussions of some book by passing the book id as a parameter to a
        // method of discussionService
        mav.addObject(discussionService.findByBookId(id));

        return mav;
    }

    /*
     * Responsible for saving discussions in the database
     * When a user posts a discussion, it will be sent to the address below with two
     * parameters
     * 'id' has the book id for which the discussion will be associated
     * 'text' is the discussion itself, having the text written by the user
     */
    @PostMapping("/discuss")
    public ModelAndView savePost(@RequestParam(name = "id") String id, @RequestParam(name = "text") String text)
            throws Exception {

        // Creates a new discussionDto with the user model, the book model, the current
        // date and time and the text
        DiscussionDto discussionDto = new DiscussionDto(userServiceImpl.getUser(userServiceImpl.getUserId()),
                bookService.findById(id),
                LocalDateTime.now(), text);

        // Saves the discussion calling the save method from discussionService, giving
        // the Dto as parameter
        discussionService.save(discussionDto);

        return discuss();
    }

    /*
     * Delete a discussion by its id and reload the page or go back to the discuss
     * page
     */
    @PostMapping("delete-discussion")
    public RedirectView deleteDiscussion(@RequestParam(name = "id") Long id) throws Exception {

        // The discussion will only be deleted if its author submits the form
        if (Objects.equals(discussionService.findById(id).getUser().getId(), userServiceImpl.getUserId())) {

            // If the discussion has comments they will be deleted first
            commentService.deleteByDiscussionId(id);
            discussionService.delete(id);
        }
        return new RedirectView("discuss");
    }

    /*
     * Add the main objects that will be used in the 'discuss' and 'mydiscuss'
     * templates
     */
    private ModelAndView startDiscussionsViews(String viewName) throws UserPrincipalNotFoundException {
        ModelAndView mav;

        // Add the user's saved books
        mav = (bookService.viewUserBooks(viewName));

        // Get all comments
        mav.addObject(getComments());

        // Get user id to be used by thymeleaf
        mav.addObject("userId", userServiceImpl.getUserId());

        return mav;
    }

    /*
     * Get all discussions
     */
    private Object getDiscussions() {
        ModelAndView mav = new ModelAndView("discuss");

        List<Discussion> discussion = discussionService.getAllDiscussions();
        mav.addObject("discussions", discussion);
        return mav.getModel().get("discussions");
    }

    /*
     * Get all comments
     */
    private Object getComments() {
        ModelAndView mav = new ModelAndView("discuss");

        List<Comment> comments = commentService.getAllComments();
        mav.addObject("comments", comments);
        return mav.getModel().get("comments");
    }
}
