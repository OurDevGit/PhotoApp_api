package com.picktur.server.entities.dto;

import com.picktur.server.entities.AuthorizationKind;
import com.picktur.server.entities.Photo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Collection;

@Data
@NoArgsConstructor
public class AuthorizationDto {

    private String id;

    private String caption;

    private Instant uploadInstant;

    private Collection<Photo> Authorized;

    private String documentUrl;

    // Can be SUBJECT, PROPERTY, BRAND, COPYRIGH
    private AuthorizationKind authorizationKind;

    public AuthorizationDto( AuthorizationKind authorizationKind, Instant uploadInstant) {
        this.uploadInstant = uploadInstant;
        this.authorizationKind = authorizationKind;
    }
}
