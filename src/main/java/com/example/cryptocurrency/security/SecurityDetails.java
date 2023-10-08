package com.example.cryptocurrency.security;

import com.example.cryptocurrency.model.Security;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class SecurityDetails implements UserDetails{

    private final Security security;

    public SecurityDetails(Security security) {
        this.security = security;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.security.getPassword();
    }

    @Override
    public String getUsername() {
        return this.security.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Security getSecurity() {
        return this.security;
    }
}
