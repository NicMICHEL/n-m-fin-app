package com.pcs.configuration;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * Anything that will retrieve the authentication
 * from the UsernamePasswordAuthenticationToken class.
 */
public interface IConnectedUser {

    /**
     * Retrieves the connected user username.
     *
     * @param token the UsernamePasswordAuthenticationToken token.
     * @return the String username.
     */
    String getUsernamePasswordLoginInfo(UsernamePasswordAuthenticationToken token);

    /**
     * Determines whether the connected user has a given role.
     *
     * @param token the UsernamePasswordAuthenticationToken token.
     * @param role  the role we want to know if it is held by the user.
     * @return a boolean true if the connected user has the role passed as parameter.
     */
    boolean hasRole(UsernamePasswordAuthenticationToken token, String role);

}
