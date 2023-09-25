/*
 * This class is responsible for saving the user
 */

package com.talkAboutThisBook.services;

import com.talkAboutThisBook.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void save(UserRegistrationDto registrationDto);
}
