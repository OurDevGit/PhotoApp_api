package com.picktur.server.entities.dto;

import com.picktur.server.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data @NoArgsConstructor
public class SimpleUserDto {

    private String username;
    private String fullName;
    private String email;
    private boolean active;
    private Instant created;
    private String instagram;
    private String facebook;

    public SimpleUserDto(User user) {
        this.username = user.getUsername();
        this.fullName = user.getName() + " " + user.getSurname();
        this.email = user.getEmail();
        this.active = user.isActive();
        this.created = user.getCreated();
        this.instagram = user.getInstagram();
        this.facebook = user.getFacebook();
    }
}
