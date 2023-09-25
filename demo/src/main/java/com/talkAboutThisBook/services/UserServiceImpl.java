/*
 * This class is responsible for operations with User on the database
 */

package com.talkAboutThisBook.services;

import com.talkAboutThisBook.dto.UserRegistrationDto;
import com.talkAboutThisBook.model.Role;
import com.talkAboutThisBook.model.User;
import com.talkAboutThisBook.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    // Declaration of service
    private final UserRepository userRepository;

    /*
     * Declaration of BCryptPasswordEncoder
     */
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    /*
     * Save user to database
     */
    @Override
    public void save(UserRegistrationDto registrationDto) {
        User user = new User(registrationDto.getFirstName(),
                registrationDto.getLastName(),
                registrationDto.getUsername(),
                passwordEncoder.encode(registrationDto.getPassword()),
                List.of(new Role("ROLE_USER")));

        userRepository.save(user);
    }

    /*
     * Save user to database
     */
    public User save(User user) {
        return userRepository.save(user);
    }

    /*
     * Check if username is already taken
     */
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    /*
     * Get user by the username
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    /*
     * Get user by id
     */
    public User getUser(Long id) throws UsernameNotFoundException {
        Optional<User> opUser = userRepository.findById(id);
        User user = null;
        if (opUser.isPresent()) {
            user = opUser.get();
        }

        return user;
    }

    /*
     * Get roles of user
     */
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    /*
     * Get user id by username
     */
    public Long loadUserID(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return user.getId();
    }

    /*
     * Get user id
     */
    public Long getUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return loadUserID(username);
    }

}
