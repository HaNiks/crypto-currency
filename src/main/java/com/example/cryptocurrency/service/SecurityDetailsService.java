package com.example.cryptocurrency.service;

import com.example.cryptocurrency.model.Security;
import com.example.cryptocurrency.repository.SecurityRepository;
import com.example.cryptocurrency.security.SecurityDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SecurityDetailsService implements UserDetailsService {

    private final SecurityRepository securityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Security> byName = securityRepository.findByName(username);
        if (byName.isEmpty()) {
            throw new UsernameNotFoundException("User Not found");
        }

        return new SecurityDetails(byName.get());
    }
}
