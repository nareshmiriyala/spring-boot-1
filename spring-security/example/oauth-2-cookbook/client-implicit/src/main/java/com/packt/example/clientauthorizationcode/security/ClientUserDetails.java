package com.packt.example.clientauthorizationcode.security;

import com.packt.example.clientauthorizationcode.user.ClientUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

public class ClientUserDetails implements UserDetails {
    private static final long serialVersionUID = 1L;

    private ClientUser clientUser;

    public ClientUserDetails(ClientUser user) {
        this.clientUser = user;
    }

    public ClientUser getClientUser() {
        return clientUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new HashSet<>();
    }

    @Override
    public String getPassword() {
        return clientUser.getPassword();
    }

    @Override
    public String getUsername() {
        return clientUser.getUsername();
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

}
