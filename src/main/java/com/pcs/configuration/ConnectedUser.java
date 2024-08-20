package com.pcs.configuration;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

/**
 * Simple implementation.
 */
@Component
public class ConnectedUser implements IConnectedUser {

    /**
     * Retrieves the connected user username after verifying that the token passed as a parameter is authenticated.
     *
     * @param token the UsernamePasswordAuthenticationToken token.
     * @return the username.
     */
    @Override
    public String getUsernamePasswordLoginInfo(UsernamePasswordAuthenticationToken token) {
        if (token.isAuthenticated()) {
            User u = (User) token.getPrincipal();
            return u.getUsername();
        } else {
            return "NA";
        }
    }

    /**
     * Determines whether the connected user has a given role among all of his roles.
     *
     * @param token the UsernamePasswordAuthenticationToken token.
     * @param role  the role we want to know if it is held by the user.
     * @return a boolean true if the connected user has the role passed as parameter.
     */
    @Override
    public boolean hasRole(UsernamePasswordAuthenticationToken token, String role) {
        return token.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals(role));
    }

}
