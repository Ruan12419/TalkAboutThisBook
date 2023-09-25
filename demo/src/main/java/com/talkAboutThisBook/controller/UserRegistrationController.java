/*
 * This class is responsible for the user registration
 */

package com.talkAboutThisBook.controller;

import com.talkAboutThisBook.dto.UserRegistrationDto;
import com.talkAboutThisBook.services.UserService;
import com.talkAboutThisBook.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

    // Declaration of the services
    @Autowired
    private final UserService userService;
    private final UserServiceImpl userServiceImpl;

    public UserRegistrationController(UserService userService, UserServiceImpl userServiceImpl) {
        super();
        this.userService = userService;
        this.userServiceImpl = userServiceImpl;
    }

    /*
     * Return a new Dto for registration
     */
    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    /*
     * Render the registration template
     */
    @GetMapping
    public String showRegistrationForm() {
        return "registration";
    }

    /*
     * Save the new user to database
     */
    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto) {
        if (userServiceImpl.existsByUsername(registrationDto.getUsername())) {
            return "redirect:/registration?userExists";
        }
        userService.save(registrationDto);
        return "redirect:/registration?success";
    }

}
