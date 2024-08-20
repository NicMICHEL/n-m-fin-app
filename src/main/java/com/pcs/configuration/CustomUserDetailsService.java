package com.pcs.configuration;

import com.pcs.model.User;
import com.pcs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * The CustomUserDetailsService class implements the UserDetailsService interface.
 * Thus, we are forced to implement the loadUserByUsername method.
 * This method is essential because it will be automatically called
 * by Spring Security during user authentication.
 * The CustomUserDetailsService class is annotated @Service in order to be detected by Spring.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    /**
     * Retrieves the user with a given username from the database and builds the corresponding UserDetails objet.
     *
     * @param username the user's username.
     * @return the UserDetails objet which is the representation of the user.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws IllegalArgumentException {
        User user = userService.getUserByUsername(username);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword()
                , getGrantedAuthorities(user.getRole()));
    }

    /**
     * Builds user rights.
     *
     * @param role the role of the user whose rights are being constructed.
     * @return a GrantedAuthority List that represents the user's rights.
     */
    public List<GrantedAuthority> getGrantedAuthorities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return authorities;
    }

}
