package com.example.cryptocurrency.security;

import com.example.cryptocurrency.service.SecurityDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class AuthProvider implements AuthenticationProvider {

    private final SecurityDetailsService securityDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        UserDetails userDetails = securityDetailsService.loadUserByUsername(username);
        String password = authentication.getCredentials().toString();
        if (password.equals(userDetails.getPassword()))
            return new UsernamePasswordAuthenticationToken(userDetails, password, Collections.emptyList());
        else {
            throw new BadCredentialsException("Incorrect password");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
