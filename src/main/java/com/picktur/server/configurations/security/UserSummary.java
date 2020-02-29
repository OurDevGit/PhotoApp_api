package com.picktur.server.configurations.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
public class UserSummary {
    private String id;
    private String username;
    private String name;
    private Collection<GrantedAuthority> authorities;


    public UserSummary(String id, String username, String name, Collection <GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.authorities = authorities;
    }
}
