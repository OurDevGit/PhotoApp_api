package com.picktur.server.configurations.security;

import lombok.Data;

import java.time.Instant;

@Data
public class UserProfile {
    private String id;
    private String username;
    private String name;
    private Instant joinedAt;

    public UserProfile(String id, String username, String name) {
        this.id = id;
        this.username = username;
        this.name = name;
    }
}
