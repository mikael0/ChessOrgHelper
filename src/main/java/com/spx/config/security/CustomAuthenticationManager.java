package com.spx.config.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * Created by Bogdan1 on 11.09.2016.
 */
public class CustomAuthenticationManager implements AuthenticationManager {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        //String pw       = authentication.getCredentials().toString();
        return authentication;

        // Code to make rest call here and check for OK or Unauthorised.
        // What do I return?

    }
}
