/*
 * This class is responsible for rendering the index template, login template and 'my books' template
 * The 'my books' page is where the user's saved books will be shown
*/

package com.talkAboutThisBook.controller;

import com.talkAboutThisBook.services.BookService;
import com.talkAboutThisBook.services.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.nio.file.attribute.UserPrincipalNotFoundException;

@Controller
@CrossOrigin(origins = "*")
public class AppController {

    // Declaration of the services
    final BookService bookService;
    final UserServiceImpl userServiceImpl;

    public AppController(BookService bookService, UserServiceImpl userServiceImpl) {
        this.bookService = bookService;
        this.userServiceImpl = userServiceImpl;
    }

    // Render the login template
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Render the index template
    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    // Renders the 'mybooks' template
    @GetMapping("/mybooks")
    public ModelAndView myBooks() throws UserPrincipalNotFoundException {
        return bookService.viewUserBooks("mybooks");
    }

}
