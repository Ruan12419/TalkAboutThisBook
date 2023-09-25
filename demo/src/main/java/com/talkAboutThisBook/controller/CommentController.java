/*
 *  This class is responsible for handling comments in discussions
 */

package com.talkAboutThisBook.controller;

import com.talkAboutThisBook.dto.CommentDto;
import com.talkAboutThisBook.model.Comment;
import com.talkAboutThisBook.services.BookService;
import com.talkAboutThisBook.services.CommentService;
import com.talkAboutThisBook.services.DiscussionService;
import com.talkAboutThisBook.services.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class CommentController {

    // Declaration of the services
    final BookService bookService;
    final UserServiceImpl userServiceImpl;
    final DiscussionService discussionService;
    final CommentService commentService;

    public CommentController(BookService bookService, UserServiceImpl userServiceImpl,
            DiscussionService discussionService,
            CommentService commentService) {
        this.bookService = bookService;
        this.userServiceImpl = userServiceImpl;
        this.discussionService = discussionService;
        this.commentService = commentService;
    }

    /*
     * Responsible for saving comments in the database
     * When a user posts a comment, it will be sent to the address below with two
     * parameters 'id' has the discussion id for which the comment will be
     * associated 'text' is the comment itself, having the text written by the user
     */
    @Transactional
    @PostMapping("comment-discussion")
    public ResponseEntity.BodyBuilder postComment(@RequestParam(name = "id") Long id,
            @RequestParam(name = "parentId") Object parentId, @RequestParam(name = "text") String text)
            throws Exception {
        long parentId1;
        if (!String.valueOf(parentId).equals("")) {
            parentId1 = Long.parseLong(String.valueOf(parentId));
        } else {
            parentId1 = 0L;
        }

        // Creates a new commentDto with the user id, the discussion model, the text,
        // and current date and time
        CommentDto commentDto = new CommentDto(userServiceImpl.getUser(userServiceImpl.getUserId()),
                discussionService.findById(id),
                text, LocalDateTime.now(), parentId1);

        // Saves the comment calling the save method from commentService, giving the Dto
        // as parameter
        commentService.save(commentDto);

        // Returns 'accepted'
        return ResponseEntity.accepted();
    }

    /*
     * Returns the comments from some discussion by the discussion id
     */
    @GetMapping("comment")
    public ResponseEntity<List<Comment>> returnComment(@RequestParam(name = "discussionId") Long discussionId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentService.findByDiscussionIdOrderByDateTimeAsc(discussionId));
    }

    /*
     * Delete a comment by its id and reload the page or go back to the discuss page
     */
    @PostMapping("delete-comment")
    public RedirectView deleteComment(@RequestParam(name = "id") Long id) {
        commentService.delete(id);
        return new RedirectView("discuss");
    }

}
